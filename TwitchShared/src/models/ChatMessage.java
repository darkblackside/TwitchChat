package models;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ChatMessage
{
	private User sender;
	private String message;
	private Timestamp time;
	
	public ChatMessage(User sender, String message)
	{
		this.sender = sender;
		this.message = message;
		Calendar calendar = Calendar.getInstance();
		time = new java.sql.Timestamp(calendar.getTime().getTime());
	}
	
	public User getUser()
	{
		return sender;
	}
	
	public String getUsername()
	{
		return sender.getUsername();
	}
	
	public String getMessage()
	{
		return message;
	}
	
	public String getTimestamp()
	{
		return new SimpleDateFormat("dd.MM. HH:mm:ss").format(time);
	}
}
