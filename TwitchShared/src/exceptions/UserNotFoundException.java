package exceptions;

public class UserNotFoundException extends Exception
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -510936290775525256L;

	public UserNotFoundException()
	{
		super();
	}
	
	public UserNotFoundException(String s)
	{
		super(s);
	}
}
