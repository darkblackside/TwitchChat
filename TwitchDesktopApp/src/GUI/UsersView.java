package GUI;

import java.awt.Dimension;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;

import models.User;

public class UsersView extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6334336967090420119L;
	JList<String> userslist;
	JScrollPane scrollpane;
	
	public UsersView(List<User> users)
	{
		super("Users visited this session");
		
		userslist = new JList<String>(new DefaultListModel());
		userslist.setPreferredSize(new Dimension(200, 600));
		userslist.setMinimumSize(new Dimension(200, 600));
		scrollpane = new JScrollPane(userslist);
		scrollpane.setPreferredSize(new Dimension(200, 600));
		scrollpane.setMinimumSize(new Dimension(200, 600));
		scrollpane.setVisible(true);
		
		updateUsersList(users);
		
		this.add(scrollpane);
		this.pack();
		this.setVisible(true);
	}
	
	@SuppressWarnings("unchecked")
	public void updateUsersList(List<User> users)
	{
		@SuppressWarnings("rawtypes")
		DefaultListModel model = (DefaultListModel)userslist.getModel();
		model.removeAllElements();
		
		for(User u:users)
		{
			if(u.isOnline())
				model.addElement(u.getUsername());
		}
	}
}
