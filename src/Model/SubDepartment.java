package Model;

import java.io.Serializable;
import java.util.HashSet;

public class SubDepartment implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9054769563038430354L;
	private static int ID = 1;

	private int id;
	private HashSet<Patient> patients;
	private HashSet<Doctor> doctors;
	private HashSet<Nurse> nurses;
	private HashSet<PatientReport> reports;
	private Department department;

	public SubDepartment(Department d) {
		super();
		id = ID++;
		patients = new HashSet<Patient>();
		doctors = new HashSet<Doctor>();
		nurses = new HashSet<Nurse>();
		reports = new HashSet<PatientReport>();
		department = d;

	}

	public SubDepartment(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public HashSet<Patient> getPatients() {
		return patients;
	}

	public HashSet<Doctor> getDoctors() {
		return doctors;
	}

	public HashSet<Nurse> getNurses() {
		return nurses;
	}

	public HashSet<PatientReport> getReports() {
		return reports;
	}

	public Department getDepartment() {
		return department;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		SubDepartment other = (SubDepartment) obj;
		if (id != other.id) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "SubDepartment [id=" + id + ", department=" + department + "]";
	}

	public boolean findDoctor(Doctor doc) {
		return getDoctors().contains(doc);
	}

	public boolean findNurse(Nurse nurse) {
		return getNurses().contains(nurse);
	}

	public boolean findPatient(Patient patient) {
		return getPatients().contains(patient);
	}

	public boolean findPatientReport(PatientReport patientReport) {
		return getReports().contains(patientReport);
	}

	public void addDoctor(Doctor doc) {
		if (findDoctor(doc)) {
			System.err.printf("%s should not be in Sdept %d\n", doc, getId());
		}
		getDoctors().add(doc);

	}

	public void addNurse(Nurse nurse) {
		if (findNurse(nurse)) {
			System.err.printf("%s should not be in Sdept %d\n", nurse, getId());
		}
		getNurses().add(nurse);

	}

	public void addPatient(Patient patient) {
		if (findPatient(patient)) {
			System.err.printf("%s should not be in Sdept %d\n", patient, getId());
		}
		getPatients().add(patient);

	}

	public void addPatientReport(PatientReport patientReport) {
		if (findPatientReport(patientReport)) {
			System.err.printf("%s should not be in Sdept %d\n", patientReport, getId());
		}
		getReports().add(patientReport);

	}

	public boolean removeDoctor(Doctor doc) {
		return getDoctors().remove(doc);

	}

	public boolean removeNurse(Nurse nurse) {
		return getNurses().remove(nurse);

	}

	public boolean removePatient(Patient patient) {

		return getPatients().remove(patient);

	}

	public boolean removePatientReport(PatientReport patientReport) {

		return getReports().remove(patientReport);

	}

}
