package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.DefaultCaret;

import models.ChatMessage;
import models.User;

public class ChatView extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2443772155821659039L;
	private JScrollPane chatScrollPane;
	private JTextArea chatArea;
	private JTextField inputfield;
	private JPanel bottom;
	private JButton sendbutton;
	private JPanel top;
	private JLabel usersOnline;
	private JButton openUserlist;
	
	public ChatView()
	{
		sendbutton = new JButton("send");
		sendbutton.setActionCommand("sendText");
		sendbutton.setMinimumSize(new Dimension(40, 20));
		
		inputfield = new JTextField();
		inputfield.setMinimumSize(new Dimension(150, 20));
		
		bottom = new JPanel();
		bottom.setLayout(new BorderLayout());
		bottom.add(inputfield, BorderLayout.CENTER);
		bottom.add(sendbutton, BorderLayout.EAST);
		bottom.setVisible(true);
		
		chatArea = new JTextArea();
		chatArea.setEditable(false);
		chatArea.setFont(new Font("Serif", Font.ITALIC, 16));
		chatArea.setLineWrap(true);
		chatArea.setWrapStyleWord(true);
		DefaultCaret caret = (DefaultCaret)chatArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		
		chatScrollPane = new JScrollPane(chatArea);
		chatScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		chatScrollPane.setVisible(true);
		
		usersOnline = new JLabel("Users online: 0");
		usersOnline.setMinimumSize(new Dimension(100, 20));
		usersOnline.setPreferredSize(new Dimension(100, 20));
		openUserlist = new JButton("Users");
		openUserlist.setMinimumSize(new Dimension(75, 20));
		openUserlist.setPreferredSize(new Dimension(75, 20));
		openUserlist.setActionCommand("userlist");
		top = new JPanel();
		top.setLayout(new FlowLayout());
		top.add(usersOnline);
		top.add(openUserlist);
		top.setVisible(true);

		this.setMinimumSize(new Dimension(200, 400));
		this.setPreferredSize(new Dimension(300, 600));
		this.setLayout(new BorderLayout());
		
		this.add(chatScrollPane, BorderLayout.CENTER);
		this.add(bottom, BorderLayout.SOUTH);
		this.add(top, BorderLayout.NORTH);
		
		this.setVisible(true);
	}
	
	public String getText()
	{
		return inputfield.getText();
	}
	public void addChatLine(ChatMessage message)
	{
		chatArea.setText(chatArea.getText() + "[" + message.getTimestamp() + "] <" + message.getUsername() + ">:\r\n" + message.getMessage() + "\r\n");
	}

	public void updateUsers(List<User> userList)
	{
		int online = 0;
		for(User u:userList)
		{
			if(u.isOnline())
			{
				online++;
			}
		}
		
		usersOnline.setText("Users online: " + online);
	}
	
	public void addListenerForButtons(ActionListener al)
	{
		sendbutton.addActionListener(al);
		openUserlist.addActionListener(al);
	}

	public void clearText(String string)
	{
		inputfield.setText("");
	}
}
