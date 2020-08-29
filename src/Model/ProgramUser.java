package Model;

import Utils.UserPrivilege;

public class ProgramUser extends HospitalUser {

	private static final long serialVersionUID = 9054769563038430354L;

	private UserPrivilege privilege;
	private String login;
	private String password;

	public ProgramUser(int id, String firstName, String lastName, SubDepartment subDepartment, String login, String password, UserPrivilege privilege) {
		super(id, firstName, lastName, subDepartment);
		this.privilege = privilege;
		this.login = login;
		this.password = password;
	}

	// Constructor for admin (doesn't belong to any subdepartment)
	public ProgramUser(int id, String login, String password, UserPrivilege privilege) {
		super(id);
		this.login = login;
		this.setPassword(password);
		this.privilege = privilege;
	}

	public ProgramUser(int id, String firstName, String lastName, SubDepartment subDepartment) {
		super(id, firstName, lastName, subDepartment);
	}

	public ProgramUser(int id) {
		super(id);
	}

	public void setPrivilege(UserPrivilege privilege) {
		this.privilege = privilege;
	}

	public UserPrivilege getPrivilege() {
		return privilege;
	}

	public String getLogin() {
		return login;
	}

	public boolean bindUser(ProgramUser user, HospitalUser hospitalUser) {

		return true;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}

}