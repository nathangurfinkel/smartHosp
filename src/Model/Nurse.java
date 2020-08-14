package Model;

import java.util.HashSet;

import Utils.Treatments;

public class Nurse extends HospitalUser {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9054769563038430354L;
	private static int ID = 1;
	private Treatments treat;
	private int shiftCounter;

	public Nurse(String fname, String lname, Treatments treat, SubDepartment s) {
		super(ID++, fname, lname, s);
		this.treat = treat;
		shiftCounter = 0;
	}

	Nurse(int id) {
		super(id);
	}

	public Treatments getTreat() {
		return treat;
	}

	public void setTreat(Treatments treat) {
		this.treat = treat;
	}

	@Override
	public String toString() {
		return String.format("Nurse name: %s", super.toString());
	}

	public String toStringLong() {
		return String.format("%s, Treatments: %s", toString(), getTreat());
	}

	public int getShiftCounter() {
		return shiftCounter;
	}

	public void setShiftCounter(int shiftCounter) {
		this.shiftCounter = shiftCounter;
	}

	/**
	 * a method to add 1 to the shift counter
	 */
	public void updateShiftCounter() {
		shiftCounter++;
	}

	/**
	 * a method to check a patient, and lower his status by 1
	 * 
	 * @param patient
	 * @return true/false
	 */
	public boolean checkPatient(Patient p) {
		if (p == null) {
			return false;
		} else {
			p.setStatus(p.getStatus() + 1);
			updateShiftCounter();
			hasTreatedPatient(p);
			return true;
		}
	}

	/**
	 * a method to check a disease, and 1 to his status
	 * 
	 * @param patient
	 * @return true/false
	 */
	public boolean checkDisease(Patient p) {
		if (p == null) {
			return false;
		} else {
			updateShiftCounter();
			p.setStatus(p.getStatus() - 1);
			hasTreatedPatient(p);
			return true;
		}
	}

	/**
	 * a method to update the nurses that has treated a patient data structure
	 * 
	 * @param patient
	 * @return boolean
	 */
	public boolean hasTreatedPatient(Patient p) {
		if (!Hospital.getInstance().getNursesByPatient().containsKey(p)) {
			Hospital.getInstance().getNursesByPatient().put(p, new HashSet<>());
		}
		Hospital.getInstance().getNursesByPatient().get(p).add(this);
		return true;
	}

}
