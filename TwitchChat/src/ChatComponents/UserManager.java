package ChatComponents;
import java.util.ArrayList;
import java.util.List;
import models.User;

public class UserManager implements Runnable
{
	public UserManager(Chat c)
	{		
		findNewUsers(true);
	}

	@Override
	public void run()
	{
		while(true)
		{
			findNewUsers(false);
			
			findDisconnectedUsers();
			
			try
			{
				Thread.sleep(10000);
			}
			catch (InterruptedException e)
			{
			}
		}
	}

	private void findDisconnectedUsers()
	{
	}

	private void findNewUsers(boolean firstConnect) 
	{
	}
	
	@SuppressWarnings("unused")
	private List<User> getUserList()
	{
		List<User> users = new ArrayList<User>();
		/*try {
			HttpResponse<JsonNode> jsonResponse = Unirest.get("http://tmi.twitch.tv/group/user/" + SettingsBuilder.getSettings().getSetting("channelname").toString() + "/chatters")
					  .asJson();
		} catch (UnirestException | SettingsNotInitializedException e)
		{
			e.printStackTrace();
		}*/
		
		return users;
	}
}
