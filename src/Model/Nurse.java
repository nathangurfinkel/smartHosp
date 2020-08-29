package Model;

import java.util.HashSet;

import Utils.Treatments;
import Utils.UserPrivilege;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Nurse extends ProgramUser {

	/**
	 *
	 */

	private transient SimpleIntegerProperty shiftCounter;
	private transient SimpleObjectProperty<Treatments> treatments;

	private static final long serialVersionUID = 9054769563038430354L;
	private static int ID = 1;
	private Treatments _treatments;
	private int _shiftCounter;

	public Nurse(String firstName, String lastName, Treatments treatments, SubDepartment subDepartment) {
		super(ID++, firstName, lastName, subDepartment);
		setTreatments(treatments);
		setShiftCounter(0);
	}

	public Nurse(String firstName, String lastName, Treatments treatments, SubDepartment subDepartment, String login, String password) {
		super(ID++, firstName, lastName, subDepartment, login, password, UserPrivilege.NURSE);
		setTreatments(treatments);
		setShiftCounter(0);

	}

	Nurse(int id) {
		super(id);
	}

//	public void addPropertyChangeSupport(PropertyChangeListener listener) {
//		propertyChangeSupport.addPropertyChangeListener(listener);
//	}

	/*
	 * field getters
	 */

	public Treatments getTreatments() {
		if (treatments == null) {
			return _treatments;
		} else {
			return treatments.get();
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

	public void setTreatments(Treatments treatments) {
		if (this.treatments == null) {
			_treatments = treatments;
		} else {
			this.treatments.set(treatments);
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

	public ObjectProperty<Treatments> treatmentsProperty() {
		if (treatments == null) {
			treatments = new SimpleObjectProperty<Treatments>(_treatments);
		}
		return treatments;
	}

	@Override
	public String toString() {
		return String.format("Nurse name: %s", super.toString());
	}

	public String toStringLong() {
		return String.format("%s, Treatments: %s", toString(), getTreatments());
	}

	/**
	 * a method to add 1 to the shift counter
	 */
	public void updateShiftCounter() {
		_shiftCounter++;
	}

	/**
	 * a method to check a patient, and lower his status by 1
	 *
	 * @param  patient
	 * @return         true/false
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
	 * @param  patient
	 * @return         true/false
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
	 * @param  patient
	 * @return         boolean
	 */
	public boolean hasTreatedPatient(Patient p) {
		if (!Hospital.getInstance().getNursesByPatient().containsKey(p)) {
			Hospital.getInstance().getNursesByPatient().put(p, new HashSet<>());
		}
		Hospital.getInstance().getNursesByPatient().get(p).add(this);
		return true;
	}

}
