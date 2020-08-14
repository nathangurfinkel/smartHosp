package Model;

import java.io.Serializable;

import Exceptions.StatusOutOfRangeException;
import Utils.Condition;
import Utils.ReleaseNote;

public class Patient extends HospitalUser implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9054769563038430354L;
	private static int ID = 1;
	private int status;
	private Condition condition;
	private Disease dis;

	public Patient(String fname, String lname, SubDepartment s, Disease d) {
		super(ID++, fname, lname, s);
		dis = d;
	}

	Patient(int id) {
		super(id);
	}

	public int getStatus() {
		return status;
	}

	/**
	 * a setter of status, if the value is above 100 or negative threre is exception
	 * to handle
	 * 
	 * @param status
	 * @return string
	 */
	public String setStatus(int status) {
		try {
			if (status > 100 || status < 0) {
				throw new StatusOutOfRangeException(status);
			} else {
				this.status = status;
				if (status >= 0 && status < 40) {
					condition = Condition.CRITICAL;
				} else if (status >= 40 && status < 60) {
					condition = Condition.SERIOUS;
				} else if (status >= 60 && status < 80) {
					condition = Condition.FAIR;
				} else {
					condition = Condition.GOOD;
				}
				return "Success";
			}

		} catch (StatusOutOfRangeException e) {
			if (status > 100) {
				this.status = 100;
				condition = Condition.GOOD;
			} else {
				this.status = 0;
				condition = Condition.CRITICAL;
			}
			return e.toString();
		}

	}

	public Condition getCondition() {
		return condition;
	}

	public Disease getDis() {
		return dis;
	}

	public void setDis(Disease dis) {
		this.dis = dis;
	}

	@Override
	public String toString() {
		return String.format("Patient name: %s", super.toString());
	}

	public String toStringLong() {
		return String.format("%s, Status is: %s, Condition %s", toString(), getStatus(), condition);
	}

	/**
	 * choose the release not by the condition
	 * 
	 * @return release note
	 */
	public ReleaseNote checkCondition() {
		switch (condition) {
		case FAIR:
			return ReleaseNote.MOVE_TO_HOTEL;
		case GOOD:
			return ReleaseNote.CAN_GO_HOME;
		default:
			return ReleaseNote.STANDBY;
		}
	}

	public int compareTo(Patient o) {
		if (!o.getLname().equals(this.getLname())) {
			return o.getLname().compareTo(this.getLname());
		} else {
			return o.getFname().compareTo(this.getFname());
		}
	}

}
