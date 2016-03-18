package models;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7451244106156367916L;
	private String nickname;
	private Date created;
	private transient boolean visitedThisSession = false;
	private int timesVisited = 0;
	private int timesWritten = 0;
	private transient boolean online = false;
	private long onlinetime = 0;
	private Date onlineSince;
	
	public User(String username)
	{
		nickname = username;
		created = new Date();
		onlineSince = null;
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
		if(!visitedThisSession)
		{
			addOneTimeVisited();
		}
		visitedThisSession = true;
	}
	
	private void addOneTimeVisited()
	{
		timesVisited++;
	}
	
	private void setOnline()
	{
		online = true;
	}
	
	private void setOffline()
	{
		online = false;
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
		return online;
	}
}
