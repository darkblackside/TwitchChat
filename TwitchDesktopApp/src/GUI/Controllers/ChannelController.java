package GUI.Controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import GUI.OptionPanes.ChannelView;
import ViewModels.ChannelViewModel;
import dao.DefaultSettingsDAO;
import twitchModels.Channel;
import twitchRestApi.ChannelFunctions;

public class ChannelController implements ActionListener
{
	ChannelView view;
	
	public ChannelController(ChannelView c) throws ClassNotFoundException, IOException
	{
		view = c;
		loadSettings();
	}
	
	public void loadSettings() throws ClassNotFoundException, IOException
	{
		ChannelFunctions channelfunc = new ChannelFunctions();
		Channel c = channelfunc.getChannelInformation(DefaultSettingsDAO.getInstance().getSettings().getSetting("channelname").toString());
		view.fillMask(c);
		view.setInactive();
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
			catch (ClassNotFoundException | IOException e2)
			{
				e2.printStackTrace();
			} break;
		case "edit": view.setActive(); break;
		case "abort":
			try
			{
				loadSettings();
				view.setInactive();
			} catch (ClassNotFoundException | IOException e1)
			{
				e1.printStackTrace();
			} break;
		}
	}

	private void saveChannel() throws ClassNotFoundException, IOException
	{
		ChannelViewModel viewModel = view.getChannelInformation();

		ChannelFunctions channelfunc = new ChannelFunctions();
		Channel c = channelfunc.getChannelInformation(DefaultSettingsDAO.getInstance().getSettings().getSetting("channelname").toString());
		
		if(viewModel.status != null)
		{
			if(viewModel.status == "")
			{
				c.setStatus(null);
			}
			else
			{
				c.setStatus(viewModel.status);
			}
		}
		if(viewModel.game != null)
		{
			if(viewModel.game == "")
			{
				c.setGame(null);
			}
			else
			{
				c.setGame(viewModel.game);
			}
		}
		if(viewModel.broadcasterlanguage != null)
		{
			if(viewModel.broadcasterlanguage == "")
			{
				c.setBroadcasterLanguage(null);
			}
			else
			{
				c.setBroadcasterLanguage(viewModel.broadcasterlanguage);
			}
		}
		c.setMature(viewModel.isMatureContent);
		
		channelfunc.saveChannel(c, DefaultSettingsDAO.getInstance().getSettings().getSetting("channelname").toString(), DefaultSettingsDAO.getInstance().getSettings().getSetting("mightyoauthkey").toString());
	}

}
