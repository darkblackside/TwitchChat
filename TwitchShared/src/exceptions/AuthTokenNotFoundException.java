package exceptions;

public class AuthTokenNotFoundException extends Exception
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1591491960358921241L;

	public AuthTokenNotFoundException(String s)
	{
		super(s);
	}
	
	public AuthTokenNotFoundException()
	{
		super();
	}
}
