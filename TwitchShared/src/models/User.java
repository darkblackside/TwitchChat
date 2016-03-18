package models;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7451245106154367916L;
	private String nickname;
	private Date created;
	private transient Date visitedThisSession = null;
	private int timesVisited = 0;
	private int timesWritten = 0;
	private UserState userstate;
	private long onlinetime = 0;
	
	public User(String username, UserState status)
	{
		visitedThisSession = new Date(0);
		nickname = username;
		userstate = status;
		created = new Date();
	}
	
	public String getUsername()
	{
		if(isOnline())
		{
			onlinetime = onlinetime + new Date().getTime() - visitedThisSession.getTime();
			visitedThisSession = new Date();
		}
		return nickname;
	}
	
	public Date getCreated()
	{
		return created;
	}
	
	public Date getVisitedThisSession()
	{
		return visitedThisSession;
	}
	
	private void addOneTimeVisited()
	{
		timesVisited++;
	}
	
	public void setOnline()
	{
		System.out.println("User " + getUsername() + " is now online");
		if(visitedThisSession == null)
		{
			addOneTimeVisited();
			visitedThisSession = new Date();
		}
	}
	
	public void setOffline()
	{
		System.out.println("User " + getUsername() + " is now offline");
		if(!(visitedThisSession == null))
		{
			onlinetime = onlinetime + new Date().getTime() - visitedThisSession.getTime();
			visitedThisSession = null;
		}
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
		setOnline();
		timesWritten++;
	}
	
	public boolean isOnline()
	{
		return !(visitedThisSession == null);
	}

	public UserState getUserstate()
	{
		return userstate;
	}

	public void setUserstate(UserState userstate)
	{
		this.userstate = userstate;
	}
}
