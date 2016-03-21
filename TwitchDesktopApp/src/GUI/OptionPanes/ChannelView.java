package GUI.OptionPanes;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import GUI.Controllers.ChannelController;
import ViewModels.ChannelViewModel;
import twitchModels.Channel;

public class ChannelView extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8854986657701325171L;
	private JTextField status;
	private JLabel statusLabel;
	private JTextField broadcasterLanguage;
	private JLabel broadcasterLanguageLabel;
	private JLabel displayName;
	private JTextField game;
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
	private JButton edit;
	private JButton abort;
	
	public ChannelView()
	{
		status = new JTextField("");
		statusLabel = new JLabel("Status: ");
		
		broadcasterLanguage = new JTextField("");
		broadcasterLanguageLabel = new JLabel("Broadcaster language: ");
		
		displayName = new JLabel("");
		
		game = new JTextField("");
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
		
		edit = new JButton("edit");
		edit.setActionCommand("edit");
		abort = new JButton("abort");
		abort.setActionCommand("abort");
		abort.setEnabled(false);
		
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
		bottom.add(edit);
		bottom.add(abort);

		bottom.setMinimumSize(new Dimension(380, 40));
		bottom.setPreferredSize(new Dimension(380, 40));
		bottom.setVisible(true);
		
		
		JPanel scrollPanel = new JPanel();
		scrollPanel.setLayout(new BorderLayout());
		scrollPanel.add(top, BorderLayout.NORTH);
		scrollPanel.add(middle, BorderLayout.CENTER);
		scrollPanel.add(bottom, BorderLayout.SOUTH);
		scrollPanel.setMinimumSize(new Dimension(380, 400));
		scrollPanel.setPreferredSize(new Dimension(380, 400));
		scrollPanel.setMaximumSize(new Dimension(380, 400));
		scrollPanel.setVisible(true);
		
		JScrollPane scrollPane = new JScrollPane(scrollPanel);
		scrollPane.setMinimumSize(new Dimension(380, 380));
		scrollPane.setPreferredSize(new Dimension(380, 380));
		scrollPane.setMaximumSize(new Dimension(380, 380));
		scrollPane.setVisible(true);
		
		this.add(scrollPane, BorderLayout.CENTER);
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
	
	public void setActive()
	{
		status.setEditable(true);
		broadcasterLanguage.setEditable(true);
		matureAudience.setEnabled(true);
		game.setEditable(true);
		abort.setEnabled(true);
		edit.setText("save");
		edit.setActionCommand("save");
	}
	
	public void setInactive()
	{
		status.setEditable(false);
		broadcasterLanguage.setEditable(false);
		matureAudience.setEnabled(false);
		game.setEditable(false);
		abort.setEnabled(false);
		edit.setText("edit");
		edit.setActionCommand("edit");
	}
	
	public boolean isActive()
	{
		return abort.isEnabled();
	}

	public void addActionListenerForButtons(ActionListener channelcontroller)
	{
		abort.addActionListener(channelcontroller);
		edit.addActionListener(channelcontroller);
	}

	public ChannelViewModel getChannelInformation() {
		ChannelViewModel viewModel = new ChannelViewModel();
		
		viewModel.status = status.getText();
		viewModel.broadcasterlanguage = broadcasterLanguage.getText();
		viewModel.game = game.getText();
		viewModel.isMatureContent = matureAudience.isSelected();
		
		return viewModel;
	}
}
