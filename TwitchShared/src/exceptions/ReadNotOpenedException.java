package exceptions;

public class ReadNotOpenedException extends Exception
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 198724031431954694L;

	public ReadNotOpenedException()
	{
		super();
	}
	
	public ReadNotOpenedException(String s)
	{
		super(s);
	}
}
