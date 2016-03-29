package games;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ChatComponents.Chat;
import models.ChatMessage;
import models.IChatListener;
import models.User;

public class LongestViewer implements IChatListener
{
	private static final String startCompetitionValue = "longestViewerStartCompetition";
	private static final String endCompetitionValue = "longestViewerLastResult";
	private static final String startDate = "longestViewerStartDate";
	
	private Chat chat;
	private Date gameStarted;
	
	public LongestViewer(Chat c)
	{
		chat = c;
		c.addListener(this);
	}
	
	public void startCompetition()
	{
		for(User u:chat.getUsersList())
		{
			u.addOrUpdateGameStat(startCompetitionValue, u.getOnlineTime());
			u.addOrUpdateGameStat(startDate, new Date().getTime());
		}
		gameStarted = new Date();
	}
	
	public List<User> endCompetition()
	{
		List<User> users = new ArrayList<User>();
		
		for(User u:chat.getUsersList())
		{
			u.addOrUpdateGameStat(endCompetitionValue, Integer.parseInt(u.getGameStat(startCompetitionValue).toString()) - u.getOnlineTime());
			users.add(u);
		}
		
		users.sort(new OnlineTimeComparer(endCompetitionValue));
		
		gameStarted = null;
		
		return users;
	}
	
	public List<User> getLongestViewerOfAllTimes()
	{
		List<User> users = new ArrayList<User>();
		
		for(User u:chat.getUsersList())
		{
			users.add(u);
		}
		
		users.sort(new OnlineTimeComparer());
		
		return users;
	}

	@Override
	public void UserJoined(User user)
	{
		
	}

	@Override
	public void UserDisconnected(User user)
	{
		
	}

	@Override
	public void MessageReceived(ChatMessage cm)
	{
		
	}

	@Override
	public void UsersUpdated(List<User> userList)
	{
		if(gameStarted != null)
		{
			for(User u:chat.getUsersList())
			{
				if(!u.isGameStatExistent(startCompetitionValue) || !u.isGameStatExistent(startDate) || Long.parseLong(u.getGameStat(startDate).toString()) != gameStarted.getTime())
				{
					u.addOrUpdateGameStat(startCompetitionValue, u.getOnlineTime());
					u.addOrUpdateGameStat(startDate, gameStarted.getTime());
				}
			}
		}
	}
	
	public boolean isGameStarted()
	{
		return (gameStarted != null);
	}
}
