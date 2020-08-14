package Model;

import java.io.Serializable;
import java.util.Date;

import Utils.ReleaseNote;

public class PatientReport implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9054769563038430354L;
	private static int ID = 1;

	private int id;
	private Patient patient;
	private Doctor doctor;
	private Date date;
	private Disease disease;
	private SubDepartment sdept;
	private ReleaseNote note;

	public PatientReport(Patient patient, Doctor doctor, Date date, Disease disease, SubDepartment sdept,
			ReleaseNote note) {
		super();
		this.id = ID++;
		this.patient = patient;
		this.doctor = doctor;
		this.date = date;
		this.disease = disease;
		this.sdept = sdept;
		this.note = note;
	}

	PatientReport(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public Patient getPatient() {
		return patient;
	}

	public Doctor getDoctor() {
		return doctor;
	}

	public Date getDate() {
		return date;
	}

	public Disease getDisease() {
		return disease;
	}

	public SubDepartment getSdept() {
		return sdept;
	}

	public void setSdept(SubDepartment sdept) {
		this.sdept = sdept;
	}

	public ReleaseNote getNote() {
		return note;
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
		PatientReport other = (PatientReport) obj;
		if (id != other.id) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "PatientReport [patient=" + patient + ", doctor=" + doctor + ", date=" + date + ", disease=" + disease
				+ "]";
	}

}
