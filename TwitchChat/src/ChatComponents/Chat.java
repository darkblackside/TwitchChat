package ChatComponents;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.AuthenticationException;
import javax.xml.crypto.URIReferenceException;

import org.jibble.pircbot.PircBot;

import dao.DefaultSettingsDAO;
import dao.DefaultUserDAO;
import exceptions.ConnectionException;
import exceptions.HttpRequestFailed;
import exceptions.JSONMalformedException;
import exceptions.NoSettingsException;
import exceptions.ReadNotOpenedException;
import exceptions.SettingsNotInitializedException;
import exceptions.UserNotFoundException;
import models.ChatMessage;
import models.IBroadcastListener;
import models.IChatListener;
import models.ISettingsChangedListener;
import models.Settings;
import models.User;

public class Chat extends PircBot implements IBroadcastListener, ISettingsChangedListener
{
	private static final String path = "users.ser";
	
	private List<IChatListener> listeners = new ArrayList<IChatListener>();
	
	private String botname;
	private String channel;
	private String server;
	private int port;
	private String authkey;
	private String channelname;
	private List<models.User> users;
	public Object userlistlock;
	private UserManager um;
	
	private List<ChatMessage> messages;
	
	public Chat() throws ClassNotFoundException, IOException, NoSettingsException, SettingsNotInitializedException, ConnectionException, AuthenticationException, URIReferenceException, ReadNotOpenedException, JSONMalformedException, UserNotFoundException, HttpRequestFailed
	{
		users = new ArrayList<models.User>();
		
		if(new File(path).exists())
		{
			users = new DefaultUserDAO().loadUsers(path);
		}
		else
		{
			new DefaultUserDAO().saveUsers(users, path);
		}
		
		userlistlock = new Object();
		
		DefaultSettingsDAO.getInstance().addSettingsChangedListener(this);
		connectToIRC();
		
		messages = new ArrayList<ChatMessage>();
		
		um = new UserManager(this);
		(new Thread(um)).start();
	}

	private void connectToIRC() throws ConnectionException, ClassNotFoundException, IOException
	{
		Settings settings = DefaultSettingsDAO.getInstance().getSettings();
		
		if(settings.getSetting("botname") != null && settings.getSetting("server") != null && settings.getSetting("port") != null && settings.getSetting("oauthkey") != null && settings.getSetting("channelname") != null &&
				(!settings.getSetting("botname").toString().equals(botname) || !settings.getSetting("server").equals(server) || !settings.getSetting("port").toString().equals(port) || !settings.getSetting("oauthkey").toString().equals(authkey) || !settings.getSetting("channelname").toString().equals(channelname)))
		{
			if(this.isConnected())
			{
				this.disconnect();
			}
			
			botname = settings.getSetting("botname").toString();
			this.setName(botname);
			//this.setVerbose(true);
			server = settings.getSetting("server").toString();
			port = Integer.parseInt(settings.getSetting("port").toString());
			authkey= settings.getSetting("oauthkey").toString();
			try {
				this.connect(server, port, authkey);
			} catch (Exception e) {
				e.printStackTrace();
				throw new ConnectionException("Could not connect to server " + settings.getSetting("server").toString() + ":"+settings.getSetting("port").toString()+", Username "+settings.getSetting("botname").toString() + ". Please restart application");
			}
			channelname = settings.getSetting("channelname").toString();
			channel = "#"+channelname;
			this.joinChannel(channel);
		}
	}

	public void addListener(IChatListener toAdd) {
	    listeners.add(toAdd);
	}
	
	public void userJoined(User user) {
	    for (IChatListener cl : listeners)
	    {
	        cl.UserJoined(user);
	    }
	}
	
	public void userDisconnected(User user)
	{
		for(IChatListener cl : listeners)
		{
			cl.UserDisconnected(user);
		}
	}
	
	public void userMessage(String username, String message) throws UserNotFoundException, FileNotFoundException, IOException
	{
		User u;
		
		u = findUserByUsername(username);
		u.visitedThisSession();
		u.addOneTimeWritten();
		saveUsers();
		
		ChatMessage cm = new ChatMessage(u, message);
		
		messages.add(cm);
	    for (IChatListener cl : listeners)
	    {
	        cl.MessageReceived(cm);
	    }
	}
	
	public void usersUpdated(List<User> userList)
	{
	    for (IChatListener cl : listeners)
	    {
	        cl.UsersUpdated(userList);
	    }
	}
	
	User findUserByUsername(String username) throws UserNotFoundException
	{
		synchronized (userlistlock)
		{
			for(User u:users)
			{
				if(u.getUsername().equals(username))
				{
					return u;
				}
			}
		}
		
		throw new UserNotFoundException();
	}

	public void onMessage(String channel, String sender, String login, String hostname, String message)
	{
		try
		{
			addUserIfNotExists(sender);
			userMessage(sender, message);
		}
		catch (UserNotFoundException | IOException e)
		{
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public void addUserIfNotExists(String sender) throws FileNotFoundException, IOException
	{
		User u = new User(sender);
		this.addUserIfNotExists(u);
	}
	public void addUserIfNotExists(User user) throws FileNotFoundException, IOException
	{
		if(!isUserExistent(user.getUsername()))
		{
			this.addUser(user);
			this.userJoined(user);
		}
	}

	public void send(String message)
	{
		this.sendMessage(channel, message);
		this.onMessage(channel, this.getName(), "", server, message);
	}
	
	public String getChannel()
	{
		return channelname;
	}
	
	private void addUser(User u) throws FileNotFoundException, IOException
	{
		synchronized (userlistlock) {
			users.add(u);
			saveUsers();
		}
	}
	public void removeUser(User u) throws FileNotFoundException, IOException
	{
		synchronized (userlistlock) {
			users.remove(u);
			saveUsers();
		}
	}
	public User getUser(int i)
	{
		return users.get(i);
	}
	
	boolean isUserExistent(String username)
	{
		synchronized (userlistlock)
		{
			for(User u:users)
			{
				if(u.getUsername().equals(username))
				{
					return true;
				}
			}
		}
		
		return false;
	}
	
	private void saveUsers() throws FileNotFoundException, IOException
	{
		usersUpdated(users);
		
		new DefaultUserDAO().saveUsers(users, path);
	}

	public List<User> getUsersList()
	{
		return users;
	}

	@Override
	public void receiveMessage(String message)
	{
		send(message);
	}

	@Override
	public void notifySettingsChanged()
	{
		try {
			connectToIRC();
		} catch (ClassNotFoundException | ConnectionException | IOException e) {
			System.out.println("Could not reconnect with new settings!");
			System.exit(1);
		}
	}
}
