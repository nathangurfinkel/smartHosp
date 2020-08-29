package Model;

import java.util.Date;
import java.util.HashSet;

import Utils.Specialization;
import Utils.Symptoms;
import Utils.UserPrivilege;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Doctor extends ProgramUser {

	/**
	 *
	 */

	private static final long serialVersionUID = 9054769563038430354L;
	private static int ID = 1;

	private transient SimpleIntegerProperty shiftCounter;
	private transient SimpleObjectProperty<Specialization> specialization;

	private Specialization _specialization;
	private int _shiftCounter;

	public Doctor(String firstName, String lastName, Specialization specialization, SubDepartment subDepartment) {
		super(ID++, firstName, lastName, subDepartment);
		setSpecialization(specialization);
		setShiftCounter(0);
	}

	public Doctor(String firstName, String lastName, Specialization specialization, SubDepartment subDepartment, String login, String password) {
		super(ID++, firstName, firstName, subDepartment, login, password, UserPrivilege.DOCTOR);
		setSpecialization(specialization);
		setShiftCounter(0);

	}

	/*
	 * field getters
	 */

	public Specialization getSpecialization() {
		if (specialization == null) {
			return _specialization;
		} else {
			return specialization.get();
		}
	}

	public int getShiftCounter() {
		if (shiftCounter == null) {
			return _shiftCounter;
		} else {
			return shiftCounter.get();
		}
	}

	/*
	 * field setters
	 */

	public void setShiftCounter(int shiftCounter) {
		if (this.shiftCounter == null) {
			_shiftCounter = shiftCounter;
		} else {
			this.shiftCounter.set(shiftCounter);
		}

	}

	public void setSpecialization(Specialization specialization) {
		if (this.specialization == null) {
			_specialization = specialization;
		} else {
			this.specialization.set(specialization);
		}
	}

	/*
	 * properties getters
	 */

	public IntegerProperty shiftCounterProperty() {
		if (shiftCounter == null) {
			shiftCounter = new SimpleIntegerProperty(_shiftCounter);
		}
		return shiftCounter;
	}

	public ObjectProperty<Specialization> specializationProperty() {
		if (specialization == null) {
			specialization = new SimpleObjectProperty<Specialization>(_specialization);
		}
		return specialization;
	}

	@Override
	public String toString() {
		return String.format("Doctor name: %s", super.toString());
	}

	public String toStringLong() {
		return String.format("%s, Specialization: %s", toString(), getSpecialization());
	}

	/**
	 * a method to add 1 to the shift counter
	 */
	public void updateShiftCounter() {
		setShiftCounter((getShiftCounter() + 1));
	}

	/**
	 * a method to check a patient, and creates a patient report
	 *
	 * @param  patient
	 * @return         true/false
	 */
	public boolean checkPatient(Patient p) {
		if (p == null) {
			return false;
		} else {
			Date now = new Date();
			updateShiftCounter();
			PatientReport report = new PatientReport(p, this, now, p.getDisease(), p.getSubDepartment(), p.checkCondition());
			p.getSubDepartment().getReports().add(report);
			Hospital.getInstance().getReportsById().put(report.getId(), report);
			hasTreatedPatient(p);
			return true;
		}
	}

	/**
	 * a method that classifies a patient disease to viral disease or chronic
	 * disease
	 *
	 * @param  patient
	 * @return         true/false
	 */
	public boolean checkDisease(Patient p) {
		if (p == null) {
			return false;
		}
		Disease d = p.getDisease();
		updateShiftCounter();
		if (d.getSymptoms().contains(Symptoms.DIFFICULTY_BREATHING) && d.getSymptoms().contains(Symptoms.FEVER)) {
			p.setDisease(new ViralDisease(d.getName(), d.getSymptoms(), true));
		} else {
			p.setDisease(new ChronicDisease(d.getName(), d.getSymptoms(), true));
		}
		hasTreatedPatient(p);
		return true;
	}

	/**
	 * a method to update the doctors that has treated a patient data structure
	 *
	 * @param  patient
	 * @return         boolean
	 */
	public boolean hasTreatedPatient(Patient p) {
		if (!Hospital.getInstance().getDoctorsByPatient().containsKey(p)) {
			Hospital.getInstance().getDoctorsByPatient().put(p, new HashSet<>());
		}
		Hospital.getInstance().getDoctorsByPatient().get(p).add(this);
		return true;
	}
}
