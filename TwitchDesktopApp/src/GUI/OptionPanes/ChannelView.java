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
	private JLabel delayLabel;
	private JTextField delay;
	private JCheckBox matureAudience;
	private JLabel matureAudienceLabel;
	private JButton edit;
	private JButton abort;
	
	public ChannelView()
	{
		//TODO resize/change so that it looks pretty
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
		
		delay = new JTextField();
		delay.setEditable(false);
		delayLabel = new JLabel("Delay");
		
		edit = new JButton("edit");
		edit.setActionCommand("edit");
		abort = new JButton("abort");
		abort.setActionCommand("abort");
		abort.setEnabled(false);
		
		JPanel top = new JPanel();
		top.setLayout(new FlowLayout());
		
		top.add(displayName);

		top.setMinimumSize(new Dimension(380, 20));
		top.setPreferredSize(new Dimension(480, 20));
		top.setVisible(true);
		
		JPanel middle = new JPanel();
		middle.setLayout(new GridLayout(6, 2));
		
		middle.add(matureAudienceLabel, 0);
		middle.add(matureAudience, 1);
		
		middle.add(statusLabel, 2);
		middle.add(status, 3);
		
		middle.add(gameLabel, 4);
		middle.add(game, 5);
		
		middle.add(delayLabel, 6);
		middle.add(delay, 7);

		middle.add(broadcasterLanguageLabel, 8);
		middle.add(broadcasterLanguage, 9);
		
		middle.add(partnerLabel, 10);
		middle.add(partner, 11);

		middle.setMinimumSize(new Dimension(380, 340));
		middle.setPreferredSize(new Dimension(480, 340));
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
		bottom.setPreferredSize(new Dimension(480, 40));
		bottom.setVisible(true);
		
		
		JPanel scrolledPanel = new JPanel();
		scrolledPanel.setLayout(new BorderLayout());
		scrolledPanel.add(top, BorderLayout.NORTH);
		scrolledPanel.add(middle, BorderLayout.CENTER);
		scrolledPanel.add(bottom, BorderLayout.SOUTH);
		scrolledPanel.setVisible(true);
		scrolledPanel.setMinimumSize(new Dimension(380, 400));
		scrolledPanel.setPreferredSize(new Dimension(460, 560));
		
		JScrollPane scrollPane = new JScrollPane(scrolledPanel);
		scrollPane.setMinimumSize(new Dimension(380, 380));
		scrollPane.setPreferredSize(new Dimension(500, 580));
		scrollPane.setMaximumSize(new Dimension(500, 580));
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
		views.setText(c.getViews().toString() + "");
		followers.setText(c.getFollowers() + "");
		if(c.isPartner() == null)
		{
			partner.setSelected(false);
		}
		else
		{
			partner.setSelected(c.isPartner());
		}
		if(c.isMature() == null)
		{
			matureAudience.setSelected(false);
		}
		else
		{
			matureAudience.setSelected(c.isMature());
		}
	}
	
	public void setActive()
	{
		status.setEditable(true);
		game.setEditable(true);
		abort.setEnabled(true);
		edit.setText("save");
		//matureAudience.setEnabled(true); -- Isn't supported by API, return value of put call is wrong
		broadcasterLanguage.setEditable(true);
		
		if(partner.isSelected())
		{
			delay.setEditable(true);
		}
		
		edit.setActionCommand("save");
	}
	
	public void setInactive()
	{
		status.setEditable(false);
		broadcasterLanguage.setEditable(false);
		matureAudience.setEnabled(false);
		game.setEditable(false);
		abort.setEnabled(false);
		delay.setEditable(false);
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
		if(delay.getText() != null && !delay.getText().equals(""))
		{
			viewModel.delay = Integer.parseInt(delay.getText());	
		}
		
		return viewModel;
	}
}
