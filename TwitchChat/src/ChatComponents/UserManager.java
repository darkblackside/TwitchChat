package ChatComponents;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import models.UserState;
import twitchRestApi.UserFunctions;

public class UserManager implements Runnable
{
	Chat c;
	
	public UserManager(Chat c)
	{		
		this.c = c;
	}

	@Override
	public void run()
	{
		while(true)
		{
			int retry = 60;
			
			Map<String, UserState> map = new HashMap<String, UserState>();
			try {
				map = new UserFunctions().getChatUsers(c.getChannel());
				
				try {
					c.updateUsers(map);
				} catch (IOException e1) {
					System.out.println("Could not synchronize online users list with offline users list.");
				}
			}
			catch (IOException e)
			{
				System.out.println("API error, retry in 30 seconds");
				
				retry = 30;
			}
			
			try
			{
				Thread.sleep(retry*1000);
			}
			catch (InterruptedException e)
			{
			}
		}
	}
}
