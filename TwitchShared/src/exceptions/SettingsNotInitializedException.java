package exceptions;
public class SettingsNotInitializedException extends Exception
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4899837121113801985L;

	public SettingsNotInitializedException()
	{
		super();
	}
	
	public SettingsNotInitializedException(String s)
	{
		super(s);
	}
}
