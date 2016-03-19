package GUI.OptionPanes;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import GUI.Controllers.SettingsController;
import models.Settings;

public class SettingsView extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7234503262521938815L;
	private JLabel usernameLabel;
	private JTextField username;
	private JLabel oauthLabel;
	private JPasswordField oauthkey;
	private JLabel serverLabel;
	private JTextField server;
	private JTextField port;
	private JLabel twitchAdressLabel;
	private JTextField channel;
	private JButton save;
	private JButton abort;
	
	public SettingsView()
	{
		JPanel top = new JPanel();
		top.setLayout(new GridLayout(4, 2));
		
		usernameLabel = new JLabel("Username");
		oauthLabel = new JLabel("O-Auth Key (with oauth:)");
		serverLabel = new JLabel("Server and Port");
		twitchAdressLabel = new JLabel("http://twitch.tv/");
		
		username = new JTextField();
		username.setMinimumSize(new Dimension(150, 20));
		username.setPreferredSize(new Dimension(150, 20));
		oauthkey = new JPasswordField();
		oauthkey.setMinimumSize(new Dimension(150, 20));
		oauthkey.setPreferredSize(new Dimension(150, 20));
		server = new JTextField();
		server.setMinimumSize(new Dimension(100, 20));
		server.setPreferredSize(new Dimension(100, 20));
		port = new JTextField();
		port.setMinimumSize(new Dimension(50, 20));
		port.setPreferredSize(new Dimension(50, 20));
		channel = new JTextField();
		channel.setMinimumSize(new Dimension(150, 20));
		channel.setPreferredSize(new Dimension(150, 20));
		
		save = new JButton("save");
		save.setActionCommand("save");
		abort = new JButton("abort");
		abort.setActionCommand("abort");
		
		top.add(usernameLabel, 0);
		top.add(username, 1);
		top.add(oauthLabel, 2);
		top.add(oauthkey, 3);
		top.add(serverLabel, 4);
		
		JPanel forServer = new JPanel(new FlowLayout());
		forServer.add(server);
		forServer.add(port);
		
		top.add(forServer, 5);
		top.add(twitchAdressLabel, 6);
		top.add(channel, 7);
		
		top.setVisible(true);
		
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		buttonPane.add(save);
		buttonPane.add(abort);
		buttonPane.setVisible(true);
		
		this.setLayout(new BorderLayout());
		this.add(top, BorderLayout.CENTER);
		this.add(buttonPane, BorderLayout.SOUTH);
		this.setVisible(true);
	}
	
	public String getBotname()
	{
		return username.getText();
	}
	public String getServer()
	{
		return server.getText();
	}
	public String getOAuthKey()
	{
		String result = "";
		for(char c: oauthkey.getPassword())
		{
			result = result+c;
		}
		return result;
	}
	public String getPort()
	{
		return port.getText();
	}
	public String getChannelname()
	{
		return channel.getText();
	}

	public void fillWithSettings(Settings settings)
	{
		username.setText(settings.getSetting("botname").toString());
		oauthkey.setText(settings.getSetting("oauthkey").toString());
		server.setText(settings.getSetting("server").toString());
		port.setText(settings.getSetting("port").toString());
		channel.setText(settings.getSetting("channelname").toString());
	}

	public void addActionListenerForButtons(ActionListener listener)
	{
		save.addActionListener(listener);
		abort.addActionListener(listener);
	}
}
