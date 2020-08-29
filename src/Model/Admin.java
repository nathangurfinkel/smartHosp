package Model;

import Utils.UserPrivilege;

public class Admin extends ProgramUser {

	private static int ID = 1;
	private static final long serialVersionUID = 9054769563038430354L;

	public Admin(String login, String password) {
		super(ID++, login, password, UserPrivilege.ADMIN);

	}

}
