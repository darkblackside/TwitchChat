package GUI.OptionPanes.Games;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import models.User;

public class OnlineTimeGameView extends JPanel
{
	private JButton startGameButton;
	private JButton endGameButton;
	private JList<User> userList;
	private JScrollPane userListPane;
	
	public OnlineTimeGameView()
	{
		startGameButton = new JButton("start");
		startGameButton.setActionCommand("start");
		endGameButton = new JButton("end");
		endGameButton.setActionCommand("end");
		endGameButton.setEnabled(false);
		
		JPanel jpane = new JPanel();
		jpane.setLayout(new FlowLayout());
		jpane.add(startGameButton);
		jpane.add(endGameButton);
		
		userList = new JList<User>(new DefaultListModel());
		userListPane = new JScrollPane(userList);
		
		this.setLayout(new BorderLayout());
		this.add(jpane, BorderLayout.NORTH);
		this.add(userListPane, BorderLayout.CENTER);
		this.setVisible(true);
	}
	
	public void startGame(List<User> orderedUsers)
	{
		endGameButton.setEnabled(true);
		startGameButton.setEnabled(false);
		updateList(orderedUsers);
	}
	
	public void endGame(List<User> orderedUsers)
	{
		updateList(orderedUsers);
		endGameButton.setEnabled(false);
		startGameButton.setEnabled(true);
	}
	
	public void updateList(List<User> orderedUsers)
	{
		DefaultListModel model = (DefaultListModel) userList.getModel();
		model.clear();
		
		for(User u:orderedUsers)
		{
			model.addElement(u.getUsername());
		}
	}
	
	public void addActionListener(ActionListener al)
	{
		startGameButton.addActionListener(al);
		endGameButton.addActionListener(al);
	}
}
