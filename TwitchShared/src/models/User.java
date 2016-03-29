package models;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class User implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7451344106156367916L;
	private String nickname;
	private Date created;
	private transient Date visitedThisTime;
	private transient boolean visitedThisSession = false;
	private int timesVisited = 0;
	private int timesWritten = 0;
	private long onlinetime = 0;
	private Map<String, Object> gameStats;
	private UserState userState;
	
	public User(String username)
	{
		nickname = username;
		created = new Date();
		gameStats = new HashMap<String, Object>();
	}
	
	public void addOrUpdateGameStat(String key, Object value)
	{
		gameStats.put(key, value);
	}
	
	public boolean isGameStatExistent(String key)
	{
		return gameStats.containsKey(key);
	}
	
	public Object getGameStat(String key)
	{
		return gameStats.get(key);
	}
	
	public String getUsername()
	{
		return nickname;
	}
	
	public void setUsername(String username)
	{
		nickname = username;
	}
	
	public Date getCreated()
	{
		return created;
	}
	
	public boolean getVisitedThisSession()
	{
		return visitedThisSession;
	}
	
	public void visitedThisSession()
	{
		if(visitedThisSession == false)
		{
			addOneTimeVisited();
		}
		visitedThisSession = true;
	}
	
	private void addOneTimeVisited()
	{
		timesVisited++;
	}
	
	public void setOnline()
	{
		if(visitedThisTime != null)
		{
			onlinetime = onlinetime +  (new Date().getTime() - visitedThisTime.getTime());
		}
		
		visitedThisSession();
		
		visitedThisTime = new Date();
	}
	
	public void setOffline()
	{
		if(visitedThisTime != null)
		{
			onlinetime = onlinetime +  (new Date().getTime() - visitedThisTime.getTime());
		}
		
		visitedThisTime = null;
	}
	
	public int getTimesVisited()
	{
		return timesVisited;
	}
	
	public int getTimesWritten()
	{
		return timesWritten;
	}
	
	public void addOneTimeWritten()
	{
		visitedThisSession();
		setOnline();
		timesWritten++;
	}
	
	public boolean isOnline()
	{
		return visitedThisTime != null;
	}

	public UserState getUserState() {
		return userState;
	}

	public void setUserState(UserState userState) {
		this.userState = userState;
	}
	
	public long getOnlineTime()
	{
		return onlinetime;
	}
}
