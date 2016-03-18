package ChatComponents;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jibble.pircbot.PircBot;

import dao.DefaultUserDAO;
import exceptions.ConnectionException;
import exceptions.NoSettingsException;
import exceptions.SettingsNotInitializedException;
import exceptions.UserNotFoundException;
import models.ChatMessage;
import models.IBroadcastListener;
import models.IChatListener;
import models.Settings;
import models.SettingsBuilder;
import models.User;

public class Chat extends PircBot implements IBroadcastListener
{
	private static final String path = "users.ser";
	
	private List<IChatListener> listeners = new ArrayList<IChatListener>();
	
	private String channel;
	private String server;
	private List<models.User> users;
	public Object userlistlock;
	private UserManager um;
	
	private List<ChatMessage> messages;
	
	public Chat() throws ClassNotFoundException, IOException, NoSettingsException, SettingsNotInitializedException, ConnectionException
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
		
		Settings settings = SettingsBuilder.getSettings();
		
		if(settings.getSetting("botname") != null && settings.getSetting("server") != null && settings.getSetting("port") != null && settings.getSetting("oauthkey") != null && settings.getSetting("channelname") != null)
		{
			connectToIRC(settings);
		}
		else
		{
			throw new NoSettingsException();
		}
		
		messages = new ArrayList<ChatMessage>();
		
		um = new UserManager(this);
		(new Thread(um)).start();
	}

	private void connectToIRC(Settings settings) throws ConnectionException
	{
		this.setName(settings.getSetting("botname").toString());
		//this.setVerbose(true);
		server = settings.getSetting("server").toString();
		int port = Integer.parseInt(settings.getSetting("port").toString());
		String oauthkey = settings.getSetting("oauthkey").toString();
		try {
			this.connect(server, port, oauthkey);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ConnectionException("Could not connect to server " + settings.getSetting("server").toString() + ":"+settings.getSetting("port").toString()+", Username "+settings.getSetting("botname").toString());
		}
		channel = "#"+settings.getSetting("channelname").toString();
		this.joinChannel(channel);
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
	
	private User findUserByUsername(String username) throws UserNotFoundException
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
	
	private void addUserIfNotExists(String sender) throws FileNotFoundException, IOException
	{
		if(!isUserExistent(sender))
		{
			synchronized (userlistlock)
			{
				User u = new User(sender);
				this.addUser(u);
				this.userJoined(u);
				saveUsers();
			}
		}
	}

	public void send(String message)
	{
		this.sendMessage(channel, message);
		this.onMessage(channel, this.getName(), "", server, message);
	}
	
	public String getChannel()
	{
		return channel;
	}
	
	public void addUser(User u) throws FileNotFoundException, IOException
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
	private boolean isUserExistent(String username)
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
		
		synchronized (userlistlock)
		{
			new DefaultUserDAO().saveUsers(users, path);
		}
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
}
