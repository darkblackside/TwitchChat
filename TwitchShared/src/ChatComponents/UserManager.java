package ChatComponents;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.AuthenticationException;
import javax.xml.crypto.URIReferenceException;

import dao.DefaultSettingsDAO;
import exceptions.HttpRequestFailed;
import exceptions.JSONMalformedException;
import exceptions.ReadNotOpenedException;
import exceptions.UserNotFoundException;
import models.Settings;
import models.User;
import twitchModels.ChatUser;
import twitchRestApi.ChatFunctions;

public class UserManager implements Runnable
{
	private Chat chat;
	
	public UserManager(Chat c) throws AuthenticationException, MalformedURLException, ClassNotFoundException, URIReferenceException, IOException, ReadNotOpenedException, JSONMalformedException, UserNotFoundException, HttpRequestFailed
	{		
		chat = c;
		
		try
		{
			findNewUsers(getActualUserList());
		}
		catch(JSONMalformedException | HttpRequestFailed e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void run()
	{
		while(true)
		{
			try
			{
				List<ChatUser> chatUsers = getActualUserList();
				
				findNewUsers(chatUsers);
				
				findDisconnectedUsers(chatUsers);
			}
			catch (AuthenticationException | ClassNotFoundException | URIReferenceException | IOException
					| ReadNotOpenedException | JSONMalformedException | UserNotFoundException | HttpRequestFailed e1)
			{
				e1.printStackTrace();
			}
			
			try
			{
				Thread.sleep(30000);
			}
			catch (InterruptedException e)
			{
			}
		}
	}

	private void findDisconnectedUsers(List<ChatUser> actualChatUsers)
	{
		for(User user:chat.getUsersList())
		{
			if(user.isOnline())
			{
				boolean found = false;
				for(ChatUser actualChatUser:actualChatUsers)
				{
					if(user.getUsername().equals(actualChatUser.username))
					{
						found = true;
					}
				}
				
				if(!found)
				{
					user.setOffline();
				}
			}
		}
	}

	private void findNewUsers(List<ChatUser> actualChatUsers) throws FileNotFoundException, IOException, UserNotFoundException 
	{
		for(ChatUser actualChatUser:actualChatUsers)
		{
			if(!chat.isUserExistent(actualChatUser.username))
			{
				User user = new User(actualChatUser.username);
				user.setUserState(actualChatUser.state);
				chat.addUserIfNotExists(user);
			}
			
			User found = chat.findUserByUsername(actualChatUser.username);
			
			if(found != null)
			{
				if(!found.isOnline())
				{
					found.setOnline();
					chat.userJoined(found);
				}
			}
			else
			{
				throw new UserNotFoundException();
			}
		}
	}
	
	private List<ChatUser> getActualUserList() throws AuthenticationException, MalformedURLException, ClassNotFoundException, URIReferenceException, IOException, ReadNotOpenedException, JSONMalformedException, HttpRequestFailed
	{
		List<ChatUser> users = ChatFunctions.getOnlineUsers(DefaultSettingsDAO.getInstance().getSettings().getSetting("channelname").toString());
		
		return users;
	}
}
