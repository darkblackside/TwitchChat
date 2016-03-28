package exceptions;

public class HttpRequestFailed extends Exception
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2445690339128609777L;
	public HttpRequestFailed(String s)
	{
		super(s);
	}
	public HttpRequestFailed()
	{
		super();
	}
}
