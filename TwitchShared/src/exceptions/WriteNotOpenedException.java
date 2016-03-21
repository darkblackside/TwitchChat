package exceptions;

public class WriteNotOpenedException extends Exception
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1904446329664704696L;
	public WriteNotOpenedException(String s)
	{
		super(s);
	}
	public WriteNotOpenedException()
	{
		super();
	}
}
