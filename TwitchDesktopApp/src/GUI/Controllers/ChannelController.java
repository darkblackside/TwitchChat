package GUI.Controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import GUI.OptionPanes.ChannelView;
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
	}
	
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
