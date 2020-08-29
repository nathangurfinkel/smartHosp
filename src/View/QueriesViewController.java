package View;

import Model.ProgramUser;
import Model.SubDepartment;

public class QueriesViewController {
	private ProgramUser user;
	private SubDepartment currentSubDepartment;

	public ProgramUser getUser() {
		return user;
	}

	public void setUser(ProgramUser user) {
		this.user = user;
	}

	public void setCurrentSubDepartment(SubDepartment currentSubDepartment) {
		this.currentSubDepartment = currentSubDepartment;
	}

	public SubDepartment getCurrentSubDepartment() {
		return currentSubDepartment;
	}

}
