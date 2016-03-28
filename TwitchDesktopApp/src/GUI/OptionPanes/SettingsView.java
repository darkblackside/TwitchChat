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
	private JLabel mightykeylabel;
	private JPasswordField mightykey;
	private JLabel ircLabel;
	private JLabel channelSettings;
	
	public SettingsView()
	{
		JPanel top = new JPanel();
		top.setLayout(new GridLayout(8, 2));
		
		usernameLabel = new JLabel("Username");
		oauthLabel = new JLabel("O-Auth Key (with oauth:)");
		serverLabel = new JLabel("Server and Port");
		twitchAdressLabel = new JLabel("Channelname: http://twitch.tv/");
		
		mightykeylabel = new JLabel("Mighty OAuth Key");
		ircLabel = new JLabel("IRC Settings");
		channelSettings = new JLabel("General Settings");
		
		username = new JTextField();
		username.setMinimumSize(new Dimension(150, 20));
		username.setPreferredSize(new Dimension(150, 20));
		oauthkey = new JPasswordField();
		oauthkey.setMinimumSize(new Dimension(150, 20));
		oauthkey.setPreferredSize(new Dimension(150, 20));
		mightykey = new JPasswordField();
		mightykey.setMinimumSize(new Dimension(150, 20));
		mightykey.setPreferredSize(new Dimension(150, 20));
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
		
		top.add(ircLabel, 0);
		top.add(new JLabel(), 1);
		top.add(usernameLabel, 2);
		top.add(username, 3);
		top.add(oauthLabel, 4);
		top.add(oauthkey, 5);
		top.add(serverLabel, 6);
		
		JPanel forServer = new JPanel(new FlowLayout());
		forServer.add(server);
		forServer.add(port);
		
		top.add(forServer, 7);
		
		top.add(channelSettings, 8);
		top.add(new JPanel(), 9);
		
		top.add(twitchAdressLabel, 10);
		top.add(channel, 11);
		top.add(mightykeylabel, 12);
		top.add(mightykey, 13);
		
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
	public String getMightyKey()
	{
		String result = "";
		for(char c: mightykey.getPassword())
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
		mightykey.setText(settings.getSetting("mightyoauthkey").toString());
	}

	public void addActionListenerForButtons(ActionListener listener)
	{
		save.addActionListener(listener);
		abort.addActionListener(listener);
	}
}
