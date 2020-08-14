package Exceptions;

/**
 * an exception that describes trying to remove a subDepartment that is not empty of objects
 *
 */
public class SubDepartmentNotEmptyException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public SubDepartmentNotEmptyException(int id) {
		super("Cannot Delete SubDepartment With ID: "+id+" Beacuse Its Not Empty");
	}
		
}
