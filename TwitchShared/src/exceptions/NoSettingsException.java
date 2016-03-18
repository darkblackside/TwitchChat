package exceptions;

public class NoSettingsException extends Exception
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2027452374489319937L;

	public NoSettingsException()
	{
		super();
	}
	
	public NoSettingsException(String m)
	{
		super(m);
	}
}
