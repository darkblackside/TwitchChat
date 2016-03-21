package exceptions;

public class SettingNotInitializedException extends Exception
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8668386057271988199L;
	public SettingNotInitializedException(String s)
	{
		super(s);
	}
	public SettingNotInitializedException()
	{
		super();
	}
}
