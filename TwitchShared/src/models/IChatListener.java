package models;

import java.util.List;

public interface IChatListener
{
	public void UserJoined(User user);
	public void UserDisconnected(User user);
	public void MessageReceived(ChatMessage cm);
	public void UsersUpdated(List<User> userList);
}
