package Exceptions;

/**
 * an exception that describes a disease without a symptoms
 *
 */
public class DiseaseWithNoSymptomsException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public DiseaseWithNoSymptomsException() {
		super("This Disease Has No Symptoms");
	}
	
}
