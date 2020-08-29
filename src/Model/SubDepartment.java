package Model;

import java.io.Serializable;
import java.util.HashSet;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SubDepartment implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 9054769563038430354L;
	private static int ID = 1;

	private transient ObservableList<Patient> patients = FXCollections.observableArrayList();
	private transient ObservableList<Doctor> doctors = FXCollections.observableArrayList();
	private transient ObservableList<Nurse> nurses = FXCollections.observableArrayList();
	private transient ObservableList<PatientReport> reports = FXCollections.observableArrayList();
	private transient SimpleObjectProperty<Department> department;

	private int id;
	private HashSet<Patient> _patients;
	private HashSet<Doctor> _doctors;
	private HashSet<Nurse> _nurses;
	private HashSet<PatientReport> _reports;
	private Department _department;

	public SubDepartment(Department d) {
		super();
		id = ID++;
		_patients = new HashSet<Patient>();
		_doctors = new HashSet<Doctor>();
		_nurses = new HashSet<Nurse>();
		_reports = new HashSet<PatientReport>();
		_department = d;

	}

	public SubDepartment(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public HashSet<Patient> getPatients() {
		return _patients;
	}

	public HashSet<Doctor> getDoctors() {
		return _doctors;
	}

	public HashSet<Nurse> getNurses() {
		return _nurses;
	}

	public HashSet<PatientReport> getReports() {
		return _reports;
	}

	public Department getDepartment() {
		return _department;
	}

	/*
	 * properties getters
	 */

	public ObservableList<Patient> getPatientsObservableList() {
		if (patients == null) {
			patients = FXCollections.observableArrayList(getPatients());
		}
		return patients;
	}

	public ObservableList<Doctor> getDoctorsObservableList() {
		if (doctors == null) {
			doctors = FXCollections.observableArrayList(getDoctors());
		}
		return doctors;
	}

	public ObservableList<Nurse> getNursesObservableList() {
		if (nurses == null) {
			nurses = FXCollections.observableArrayList(getNurses());
		}
		return nurses;
	}

	public ObservableList<PatientReport> getReportsObservableList() {
		ObservableList<PatientReport> output = FXCollections.observableArrayList(getReports());
		return output;
	}

	public ObjectProperty<Department> departmentProperty() {
		if (department == null) {
			department = new SimpleObjectProperty<Department>(_department);
		}
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
		return "SubDepartment [id=" + id + ", department=" + _department + "]";
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

	public void addDoctor(Doctor doctor) {
		if (findDoctor(doctor)) {
			System.err.printf("%s should not be in Sdept %d\n", doctor, getId());
		}

		System.out.println("Adding doctor: " + doctor + " to :" + this + "with effect: " + getDoctorsObservableList().add(doctor));
		getDoctors().add(doctor);

	}

	public void addNurse(Nurse nurse) {
		if (findNurse(nurse)) {
			System.err.printf("%s should not be in Sdept %d\n", nurse, getId());
		}
		getNursesObservableList().add(nurse);
		getNurses().add(nurse);

	}

	public void addPatient(Patient patient) {
		if (findPatient(patient)) {
			System.err.printf("%s should not be in Sdept %d\n", patient, getId());
		}
		System.out.println("Adding patient: " + patient + " to :" + this + "with effect: " + getPatientsObservableList().add(patient));
		getPatients().add(patient);

	}

	public void addPatientReport(PatientReport patientReport) {
		if (findPatientReport(patientReport)) {
			System.err.printf("%s should not be in Sdept %d\n", patientReport, getId());
		}
		getReports().add(patientReport);

	}

	public boolean removeDoctor(Doctor doctor) {
		getDoctorsObservableList().remove(doctor);
		return getDoctors().remove(doctor);

	}

	public boolean removeNurse(Nurse nurse) {
		getNursesObservableList().remove(nurse);
		return getNurses().remove(nurse);

	}

	public boolean removePatient(Patient patient) {
		System.out.println("REMOVING is: " + getPatientsObservableList().remove(patient));

		return getPatients().remove(patient);

	}

	public boolean removePatientReport(PatientReport patientReport) {

		return getReports().remove(patientReport);

	}

}
