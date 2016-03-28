package twitchRestApi;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.naming.AuthenticationException;
import javax.xml.crypto.URIReferenceException;

import org.json.JSONException;
import org.json.JSONObject;

import exceptions.HttpRequestFailed;
import exceptions.JSONMalformedException;
import exceptions.ReadNotOpenedException;
import models.UserState;
import twitchModels.ChatUser;

public class ChatFunctions
{
	public static final String ChatAPIAddress = "http://tmi.twitch.tv/group/user/<<<username>>>/chatters";
	
	public static List<ChatUser> getOnlineUsers(String username) throws AuthenticationException, MalformedURLException, URIReferenceException, IOException, ReadNotOpenedException, JSONMalformedException, HttpRequestFailed
	{
		String connectionAddress = ChatAPIAddress.replace("<<<username>>>", username);
		
		return getUsersFromJson(APIConnection.getConnection(connectionAddress + "?refresh=" + Math.random(), new HashMap<String, String>()));
	}
	
	public static List<ChatUser> getUsersFromJson(JSONObject object) throws JSONMalformedException
	{
		//TODO This function can be optimized, see down here
		
		List<ChatUser> result = new ArrayList<ChatUser>();
		
		if(object.getJSONObject("chatters") != null)
		{
			JSONObject chatters = object.getJSONObject("chatters");
			
			if(chatters.getJSONArray("moderators") != null && chatters.getJSONArray("staff") != null && chatters.getJSONArray("admins") != null && chatters.getJSONArray("global_mods") != null && chatters.getJSONArray("viewers") != null)
			{
				//TODO This has to be optimized
				java.util.Iterator<Object> iterator = chatters.getJSONArray("moderators").iterator();
				while(iterator.hasNext())
				{
					String username = iterator.next().toString();
					if(username != null && !username.equals(""))
					{
						ChatUser user = new ChatUser();
						user.state = UserState.Moderator;
						user.username = username;
						result.add(user);
					}
				}
				iterator = chatters.getJSONArray("viewers").iterator();
				while(iterator.hasNext())
				{
					String username = iterator.next().toString();
					if(username != null && !username.equals(""))
					{
						ChatUser user = new ChatUser();
						user.state = UserState.Viewer;
						user.username = username;
						result.add(user);
					}
				}
				
				iterator = chatters.getJSONArray("staff").iterator();
				while(iterator.hasNext())
				{
					String username = iterator.next().toString();
					if(username != null && !username.equals(""))
					{
						ChatUser user = new ChatUser();
						user.state = UserState.Staff;
						user.username = username;
						result.add(user);
					}
				}
				iterator = chatters.getJSONArray("admins").iterator();
				while(iterator.hasNext())
				{
					String username = iterator.next().toString();
					if(username != null && !username.equals(""))
					{
						ChatUser user = new ChatUser();
						user.state = UserState.Admin;
						user.username = username;
						result.add(user);
					}
				}
				iterator = chatters.getJSONArray("global_mods").iterator();
				while(iterator.hasNext())
				{
					String username = iterator.next().toString();
					if(username != null && !username.equals(""))
					{
						ChatUser user = new ChatUser();
						user.state = UserState.GlobalModerator;
						user.username = username;
						result.add(user);
					}
				}
			}
			else
			{
				throw new JSONMalformedException("No moderators/staff/admins/global_mods/viewers object found");
			}
		}
		else
		{
			throw new JSONMalformedException("No chatters object found");
		}
		
		return result;
	}
}
