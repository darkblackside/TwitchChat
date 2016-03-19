package GUI.Controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import GUI.OptionPanes.SettingsView;
import dao.DefaultSettingsDAO;
import exceptions.SettingsNotInitializedException;
import models.Settings;

public class SettingsController implements ActionListener
{
	private static final boolean FORCELOAD = true;
	
	SettingsView settingsview;
	
	public SettingsController(SettingsView settingsv) throws SettingsNotInitializedException, ClassNotFoundException, IOException
	{
		settingsview = settingsv;
		settingsview.fillWithSettings(DefaultSettingsDAO.getInstance().getSettings());
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		try
		{
			Settings settings = DefaultSettingsDAO.getInstance().getSettings();
			settings.updateSetting("botname", settingsview.getBotname());
			settings.updateSetting("server", settingsview.getServer());
			settings.updateSetting("port", settingsview.getPort());
			settings.updateSetting("channelname", settingsview.getChannelname());
			settings.updateSetting("oauthkey", settingsview.getOAuthKey());
			
			switch(arg0.getActionCommand())
			{
				case "save": DefaultSettingsDAO.getInstance().saveSettings(settings); break;
				case "abort": try {
					settingsview.fillWithSettings(DefaultSettingsDAO.getInstance().getSettings(FORCELOAD));
				} catch (ClassNotFoundException | IOException e)
				{
					e.printStackTrace();
				} break;
			}
		}
		catch (ClassNotFoundException | IOException e1)
		{
			e1.printStackTrace();
		}
	}

}
