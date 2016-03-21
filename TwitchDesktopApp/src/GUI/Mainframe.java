package GUI;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.io.IOException;
import java.net.URL;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import ChatComponents.Chat;
import GUI.Controllers.ChannelController;
import GUI.Controllers.ChatController;
import GUI.Controllers.SettingsController;
import GUI.OptionPanes.ChannelView;
import GUI.OptionPanes.SettingsView;
import dao.DefaultSettingsDAO;
import exceptions.AuthTokenNotFoundException;
import exceptions.ChannelAPINotCallableException;
import exceptions.ConnectionException;
import exceptions.NoSettingsException;
import exceptions.SettingNotInitializedException;
import exceptions.SettingsNotInitializedException;
import models.Settings;
import twitchRestApi.APIKeyConnection;

public class Mainframe extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8159124900675570990L;
	private OptionPane tabbedpane;
	private Chat c;
	
	public Mainframe() throws SettingsNotInitializedException, ClassNotFoundException, IOException, HeadlessException, AuthTokenNotFoundException
	{
		//Initializing settings, views and controllers
		initializeSettings();
		try {
			c = new Chat();
		} catch (ClassNotFoundException | IOException | NoSettingsException | SettingsNotInitializedException
				| ConnectionException e) {
			e.printStackTrace();
			showError("Error while connecting to Server");
			if(JOptionPane.showConfirmDialog(this, "Do you want to reconfigure the system?") == JOptionPane.YES_OPTION)
			{
				DefaultSettingsDAO.getInstance().deleteSettings(true);
				showError("Please restart application in order to reconfigure");
			}
		}
		
		try
		{
			initializeGUI();
		}
		catch (ChannelAPINotCallableException | SettingNotInitializedException e)
		{
			e.printStackTrace();

			showError("There was an error while initializing the GUI, please ask darkblackside or twitch forum for help");
		}
	}
	
	private void initializeGUI() throws SettingsNotInitializedException, ClassNotFoundException, IOException, ChannelAPINotCallableException, SettingNotInitializedException
	{
		tabbedpane = new OptionPane();
		
		SettingsView settings = new SettingsView();
		SettingsController settingscontroller = new SettingsController(settings);
		tabbedpane.addTabbedComponent("Settings", settings);
		settings.addActionListenerForButtons(settingscontroller);
		
		ChannelView channel = new ChannelView();
		ChannelController channelcontroller = new ChannelController(channel);
		tabbedpane.addTabbedComponent("Channel", channel);
		channel.addActionListenerForButtons(channelcontroller);
		
		this.setLayout(new FlowLayout());
		ChatView chView = new ChatView();
		this.add(chView);
		ChatController chController = new ChatController(chView, c.getUsersList());
		c.addListener(chController);
		chView.addListenerForButtons(chController);
		chController.addBroadcastListener(c);
		this.add(tabbedpane);
		this.pack();
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	private void showError(String string)
	{
		JOptionPane.showMessageDialog(this, string, "Error", JOptionPane.ERROR_MESSAGE);
	}

	private void initializeSettings() throws HeadlessException, AuthTokenNotFoundException
	{
		//Wichtig, da sonst die Settings nicht funktionieren:
		Settings s = new Settings();
		s.addSetting("botname", "darkblackside");
		s.addSetting("server", "irc.twitch.tv");
		s.addSetting("port", "6667");
		s.addSetting("oauthkey", "oauth:xxxx");
		s.addSetting("channelname", "darkblackside");
		
		if(!DefaultSettingsDAO.getInstance().settingsExist())
		{
			s.updateSetting("botname", JOptionPane.showInputDialog("Name your chatbot (likely your channelname on twitch)"));
			s.updateSetting("oauthkey", JOptionPane.showInputDialog("Enter your oauth key (for beta you have to get the key without this program)"));
			s.updateSetting("channelname", JOptionPane.showInputDialog("Type your channelname"));
						
			try {
				DefaultSettingsDAO.getInstance().saveSettings(s);
			} catch (IOException e) {
				showError("Error while initializing settings");
				e.printStackTrace();
			}
		}
	
		try
		{
			Object mightykey = DefaultSettingsDAO.getInstance().getSettings().getSetting("mightyoauthkey");
			
			if(mightykey == null)
			{
				Settings s2 = DefaultSettingsDAO.getInstance().getSettings();
				
				s2.addSetting("mightyoauthkey", "");
				
				s2.updateSetting("mightyoauthkey", APIKeyConnection.getAuthToken(JOptionPane.showInputDialog("Type your API Key (visit " + APIKeyConnection.editUrl + " and authorize. Paste result url here.")));

				try {
					DefaultSettingsDAO.getInstance().saveSettings(s2);
				} catch (IOException e) {
					showError("Error while initializing settings");
					e.printStackTrace();
				}
			}
		}
		catch (ClassNotFoundException | IOException e)
		{
			showError("Error while initializing settings");
		}
	}

	public OptionPane getOptionsPane()
	{
		return tabbedpane;
	}
	
	public static void main(String[] args)
	{
		try
		{
			Mainframe mf = new Mainframe();
			mf.setVisible(true);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.exit(ABORT);
		}
	}
}
