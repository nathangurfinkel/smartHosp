package Exceptions;

public class IncorrectPasswordException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2003724970788515442L;

	public IncorrectPasswordException() {

		super("Password Incorrect");
	}

}
