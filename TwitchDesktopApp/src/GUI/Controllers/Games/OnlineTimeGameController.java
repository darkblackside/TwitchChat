package GUI.Controllers.Games;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import ChatComponents.Chat;
import GUI.OptionPanes.Games.OnlineTimeGameView;
import games.LongestViewer;
import models.User;

public class OnlineTimeGameController implements ActionListener
{
	private OnlineTimeGameView view;
	private LongestViewer game;
	
	public OnlineTimeGameController(OnlineTimeGameView v, Chat c)
	{
		game = new LongestViewer(c);
		view = v;
		view.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		switch(arg0.getActionCommand())
		{
			case "start": game.startCompetition(); view.startGame(new ArrayList<User>()); break;
			case "end": view.endGame(game.endCompetition()); break;
		}
	}
}
