package games;

import java.util.Comparator;

import models.User;

public class OnlineTimeComparer implements Comparator<User>
{
	private String sortElement;
	
	public OnlineTimeComparer()
	{
	}
	
	public OnlineTimeComparer(String element)
	{
		sortElement = element;
	}
	
	@Override
	public int compare(User arg0, User arg1) {
		if(sortElement == null)
		{
			return (int) (arg0.getOnlineTime() - arg1.getOnlineTime());
		}
		else
		{
			return Integer.parseInt(arg0.getGameStat(sortElement).toString()) - Integer.parseInt(arg1.getGameStat(sortElement).toString());
		}		
	}
}
