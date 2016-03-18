package GUI.Controllers;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import GUI.ChatView;
import GUI.UsersView;
import models.ChatMessage;
import models.IBroadcastListener;
import models.IChatListener;
import models.User;

public class ChatController implements IChatListener, ActionListener
{
	public ChatView view;
	public List<User> usertemp;
	public UsersView usersview;
	public List<IBroadcastListener> broadcastreceiver;
	
	public ChatController(ChatView v, List<User> list)
	{
		broadcastreceiver = new ArrayList<IBroadcastListener>();
		usertemp = list;
		view = v;
		view.updateUsers(list);
	}
	
	public void addBroadcastListener(IBroadcastListener receiver)
	{
		broadcastreceiver.add(receiver);
	}

	@Override
	public void UserJoined(User user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void UserDisconnected(User user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void MessageReceived(ChatMessage cm)
	{
		Toolkit.getDefaultToolkit().beep();
		view.addChatLine(cm);
	}

	@Override
	public void UsersUpdated(List<User> userList)
	{
		view.updateUsers(userList);
		usertemp = userList;
	}
	
	private void broadcast(String text)
	{
		for(IBroadcastListener bcl:broadcastreceiver)
		{
			bcl.receiveMessage(text);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		switch(e.getActionCommand())
		{
		case "sendText": broadcast(view.getText()); view.clearText(""); break;
		case "userlist": new UsersView(usertemp); break;
		}
	}
}
