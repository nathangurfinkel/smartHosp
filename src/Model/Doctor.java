package Model;

import java.util.Date;
import java.util.HashSet;

import Utils.Specialization;
import Utils.Symptoms;

public class Doctor extends HospitalUser {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9054769563038430354L;
	private static int ID = 1;
	private Specialization spec;
	private int shiftCounter;

	public Doctor(String fname, String lname, Specialization spec, SubDepartment s) {
		super(ID++, fname, lname, s);
		this.spec = spec;
		shiftCounter = 0;
	}

	Doctor(int id) {
		super(id);
	}

	public Specialization getSpec() {
		return spec;
	}

	public void setSpec(Specialization spec) {
		this.spec = spec;
	}

	@Override
	public String toString() {
		return String.format("Doctor name: %s", super.toString());
	}

	public String toStringLong() {
		return String.format("%s, Specialization: %s", toString(), getSpec());
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
	 * a method to check a patient, and creates a patient report
	 * 
	 * @param patient
	 * @return true/false
	 */
	public boolean checkPatient(Patient p) {
		if (p == null) {
			return false;
		} else {
			Date now = new Date();
			updateShiftCounter();
			PatientReport report = new PatientReport(p, this, now, p.getDis(), p.getsDepartment(), p.checkCondition());
			p.getsDepartment().getReports().add(report);
			Hospital.getInstance().getReportsById().put(report.getId(), report);
			hasTreatedPatient(p);
			return true;
		}
	}

	/**
	 * a method that classifies a patient disease to viral disease or chronic
	 * disease
	 * 
	 * @param patient
	 * @return true/false
	 */
	public boolean checkDisease(Patient p) {
		if (p == null) {
			return false;
		}
		Disease d = p.getDis();
		updateShiftCounter();
		if (d.getSymptoms().contains(Symptoms.DIFFICULTY_BREATHING) && d.getSymptoms().contains(Symptoms.FEVER)) {
			p.setDis(new ViralDisease(d.getId(), d.getName(), d.getSymptoms(), true));
		} else {
			p.setDis(new ChronicDisease(d.getId(), d.getName(), d.getSymptoms(), true));
		}
		hasTreatedPatient(p);
		return true;
	}

	/**
	 * a method to update the doctors that has treated a patient data structure
	 * 
	 * @param patient
	 * @return boolean
	 */
	public boolean hasTreatedPatient(Patient p) {
		if (!Hospital.getInstance().getDoctorsByPatient().containsKey(p)) {
			Hospital.getInstance().getDoctorsByPatient().put(p, new HashSet<>());
		}
		Hospital.getInstance().getDoctorsByPatient().get(p).add(this);
		return true;
	}
}
