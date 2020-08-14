package Exceptions;

import Utils.ReleaseNote;

/**
 * an exception that describes a patient without the proper release note to release him from
 * the hospital
 */
public class CannotReleasePatientException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public CannotReleasePatientException(ReleaseNote note, ReleaseNote proper){
		super("Cannot Release This Patient Because  He Have The Note: " +
				note+" And Not The Proper Release Note: "+proper);
	}

}
