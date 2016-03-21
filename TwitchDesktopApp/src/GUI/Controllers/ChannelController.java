package GUI.Controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import GUI.OptionPanes.ChannelView;
import ViewModels.ChannelViewModel;
import dao.Authentication;
import dao.DefaultSettingsDAO;
import exceptions.ChannelAPINotCallableException;
import exceptions.SettingNotInitializedException;
import twitchModels.Channel;
import twitchRestApi.ChannelFunctions;

public class ChannelController implements ActionListener
{
	ChannelView view;
	
	public ChannelController(ChannelView c) throws ClassNotFoundException, IOException, ChannelAPINotCallableException, SettingNotInitializedException
	{
		view = c;
		loadSettings();
	}
	
	public void loadSettings() throws ClassNotFoundException, IOException, ChannelAPINotCallableException, SettingNotInitializedException
	{
		ChannelFunctions channelfunc = new ChannelFunctions();
		Object name = DefaultSettingsDAO.getInstance().getSettings().getSetting("channelname");
		if(name != null)
		{
			Channel c = channelfunc.getChannelInformation(name.toString(), Authentication.getMightyAuthenticationHeader());
			view.fillMask(c);
			view.setInactive();
		}
		else
		{
			throw new SettingNotInitializedException("channelname");
		}
	}
	
	public void actionPerformed(ActionEvent e)
	{
		switch(e.getActionCommand())
		{
		case "save":
			try
			{
				saveChannel();
				view.setInactive();
			}
			catch (ClassNotFoundException | IOException | ChannelAPINotCallableException | SettingNotInitializedException e2)
			{
				e2.printStackTrace();
				try
				{
					loadSettings();
				}
				catch (ClassNotFoundException | IOException | ChannelAPINotCallableException
						| SettingNotInitializedException e1)
				{
					e1.printStackTrace();
				}
			}
			break;
		case "edit": view.setActive(); break;
		case "abort":
			try
			{
				loadSettings();
				view.setInactive();
			}
			catch (ClassNotFoundException | IOException | ChannelAPINotCallableException | SettingNotInitializedException e1)
			{
				e1.printStackTrace();
			} break;
		}
	}

	private void saveChannel() throws ClassNotFoundException, IOException, ChannelAPINotCallableException, SettingNotInitializedException
	{
		ChannelViewModel viewModel = view.getChannelInformation();

		Channel c = new Channel();
		
		c.setStatus(viewModel.status);
		c.setGame(viewModel.game);
		c.setBroadcasterLanguage(viewModel.broadcasterlanguage);
		c.setMature(viewModel.isMatureContent);
		
		ChannelFunctions channelfunc = new ChannelFunctions();
		Object name = DefaultSettingsDAO.getInstance().getSettings().getSetting("channelname");
		if(name != null)
		{
			channelfunc.updateChannelInformation(name.toString(), Authentication.getMightyAuthenticationHeader(), c);
		}
		else
		{
			throw new SettingNotInitializedException("channelname");
		}
	}

}
