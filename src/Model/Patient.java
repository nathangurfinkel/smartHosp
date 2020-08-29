package Model;

import java.io.Serializable;

import Exceptions.StatusOutOfRangeException;
import Utils.Condition;
import Utils.ReleaseNote;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Patient extends HospitalUser implements Serializable {
	/**
	 *
	 */

	private static final long serialVersionUID = 9054769563038430354L;

	private transient SimpleIntegerProperty status;
	private transient SimpleObjectProperty<Condition> condition;
	private transient SimpleObjectProperty<Disease> disease;

	private static int ID = 1;
	private int _status;
	private Condition _condition;
	private Disease _disease;

	public Patient(String firstName, String lastName, SubDepartment subDepartment, Disease disease) {
		super(ID++, firstName, lastName, subDepartment);
		setDisease(disease);

	}

	Patient(int id) {
		super(id);
	}

	public int getStatus() {
		if (status == null) {
			return _status;
		} else {
			return status.get();
		}
	}

	public Condition getCondition() {
		if (condition == null) {
			return _condition;
		} else {
			return condition.get();
		}
	}

	public Disease getDisease() {
		if (disease == null) {
			return _disease;
		} else {
			return disease.get();
		}
	}
	/*
	 * field setters
	 */

	/*
	 * public void setCondition(Condition condition) { if (this.condition == null) {
	 * _condition = condition; } else { this.condition.set(condition); } }
	 */

	public void setDisease(Disease disease) {
		if (this.disease == null) {
			_disease = disease;
		} else {
			this.disease.set(disease);
		}
	}

	/*
	 * properties getters
	 */

	public IntegerProperty statusProperty() {
		if (status == null) {
			status = new SimpleIntegerProperty(_status);
		}
		return status;
	}

	public ObjectProperty<Condition> conditionProperty() {
		if (condition == null) {
			condition = new SimpleObjectProperty<Condition>(_condition);
		}
		return condition;
	}

	public ObjectProperty<Disease> diseaseProperty() {
		if (disease == null) {
			disease = new SimpleObjectProperty<Disease>(_disease);
		}
		return disease;
	}

	/**
	 * a setter of status, if the value is above 100 or negative threre is exception
	 * to handle
	 *
	 * @param  status
	 * @return        string
	 */
	public String setStatus(int status) {
		try {
			if (status > 100 || status < 0) {
				throw new StatusOutOfRangeException(status);
			} else {
				this._status = status;
				this.statusProperty().set(status);
				if (status >= 0 && status < 40) {
					_condition = Condition.CRITICAL;
				} else if (status >= 40 && status < 60) {
					_condition = Condition.SERIOUS;
				} else if (status >= 60 && status < 80) {
					_condition = Condition.FAIR;
				} else {
					_condition = Condition.GOOD;
				}
				this.conditionProperty().set(_condition);
				return "Success";
			}

		} catch (StatusOutOfRangeException e) {
			if (status > 100) {
				this._status = 100;
				this.statusProperty().set(100);

				_condition = Condition.GOOD;
			} else {
				this._status = 0;
				this.statusProperty().set(0);
				_condition = Condition.CRITICAL;
			}
			this.conditionProperty().set(_condition);
			return e.toString();
		}

	}

	@Override
	public String toString() {
		return String.format("Patient name: %s", super.toString());
	}

	public String toStringLong() {
		return String.format("%s, Status is: %s, Condition %s", toString(), getStatus(), _condition);
	}

	/**
	 * choose the release not by the condition
	 *
	 * @return release note
	 */
	public ReleaseNote checkCondition() {
		switch (_condition) {
			case FAIR:
				return ReleaseNote.MOVE_TO_HOTEL;
			case GOOD:
				return ReleaseNote.CAN_GO_HOME;
			default:
				return ReleaseNote.STANDBY;
		}
	}

	public int compareTo(Patient o) {
		if (!o.getLastName().equals(this.getLastName())) {
			return o.getLastName().compareTo(this.getLastName());
		} else {
			return o.getFirstName().compareTo(this.getFirstName());
		}
	}

}
