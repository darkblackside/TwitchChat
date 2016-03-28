package exceptions;

public class JSONMalformedException extends Exception
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8696592726506978954L;
	public JSONMalformedException()
	{
		super();
	}
	public JSONMalformedException(String s)
	{
		super(s);
	}
}
