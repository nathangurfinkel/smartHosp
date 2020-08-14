package Exceptions;

/**
 * a patient that his status is above 100 or negative
 *
 */
public class StatusOutOfRangeException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public StatusOutOfRangeException(int status) {
		super("The Status " + status +" is Not In Range Of 0 To 100");
	}
}
