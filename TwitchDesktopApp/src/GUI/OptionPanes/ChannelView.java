package GUI.OptionPanes;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import GUI.Controllers.ChannelController;
import twitchModels.Channel;

public class ChannelView extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8854986657701325171L;
	private JLabel status;
	private JLabel statusLabel;
	private JLabel broadcasterLanguage;
	private JLabel broadcasterLanguageLabel;
	private JLabel displayName;
	private JLabel game;
	private JLabel gameLabel;
	private JLabel language;
	private JCheckBox partner;
	private JLabel partnerLabel;
	private JLabel viewsLabel;
	private JLabel views;
	private JLabel followersLabel;
	private JLabel followers;
	private JCheckBox matureAudience;
	private JLabel matureAudienceLabel;
	
	public ChannelView()
	{
		status = new JLabel("");
		statusLabel = new JLabel("Status: ");
		
		broadcasterLanguage = new JLabel("");
		broadcasterLanguageLabel = new JLabel("Broadcaster language: ");
		
		displayName = new JLabel("");
		
		game = new JLabel("");
		gameLabel = new JLabel("Game: ");
		
		language = new JLabel("");
		
		partnerLabel = new JLabel("Is twitch partner ");
		partner = new JCheckBox();
		partner.setEnabled(false);
		
		viewsLabel = new JLabel("Views: ");
		views = new JLabel("");
		
		followersLabel = new JLabel("Followers: ");
		followers = new JLabel("");
		
		matureAudienceLabel = new JLabel("For mature audience ");
		matureAudience = new JCheckBox();
		matureAudience.setEnabled(false);
		
		JPanel top = new JPanel();
		top.setLayout(new FlowLayout());
		
		top.add(displayName);

		top.setMinimumSize(new Dimension(380, 20));
		top.setPreferredSize(new Dimension(380, 20));
		top.setVisible(true);
		
		JPanel middle = new JPanel();
		middle.setLayout(new GridLayout(5, 2));
		
		middle.add(matureAudienceLabel, 0);
		middle.add(matureAudience, 1);
		
		middle.add(statusLabel, 2);
		middle.add(status, 3);
		
		middle.add(broadcasterLanguageLabel, 4);
		middle.add(broadcasterLanguage, 5);
		
		middle.add(gameLabel, 6);
		middle.add(game, 7);
		
		middle.add(partnerLabel, 8);
		middle.add(partner, 9);

		middle.setMinimumSize(new Dimension(380, 340));
		middle.setPreferredSize(new Dimension(380, 340));
		middle.setVisible(true);
		
		JPanel bottom = new JPanel();
		bottom.setLayout(new FlowLayout());
		
		bottom.add(viewsLabel);
		bottom.add(views);
		bottom.add(followersLabel);
		bottom.add(followers);

		bottom.setMinimumSize(new Dimension(380, 40));
		bottom.setPreferredSize(new Dimension(380, 40));
		bottom.setVisible(true);
		
		this.add(top, BorderLayout.NORTH);
		this.add(middle, BorderLayout.CENTER);
		this.add(bottom, BorderLayout.SOUTH);
		
		this.setVisible(true);
	}
	
	public void fillMask(Channel c)
	{
		status.setText(c.getStatus());
		broadcasterLanguage.setText(c.getBroadcasterLanguage());
		displayName.setText(c.getName());
		game.setText(c.getGame());
		language.setText(c.getLanguage());
		partner.setSelected(c.isPartner());
		matureAudience.setSelected(c.isMature());
	}

	public void addActionListenerForButtons(ChannelController channelcontroller)
	{
		
	}
}
