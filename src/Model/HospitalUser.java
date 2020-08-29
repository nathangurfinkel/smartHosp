package Model;

import java.io.Serializable;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public abstract class HospitalUser implements Serializable {
	/**
	 *
	 */
//	private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

	private transient SimpleIntegerProperty id;
	private transient SimpleStringProperty firstName;
	private transient SimpleStringProperty lastName;
	private transient SimpleObjectProperty<SubDepartment> subDepartment;

	private static final long serialVersionUID = 9054769563038430354L;
	private int _id;
	private String _firstName;
	private String _lastName;
	private SubDepartment _subDepartment;

	public HospitalUser(int id, String firstName, String lastName, SubDepartment subDepartment) {
		super();
		setId(id);
		setFirstName(firstName);
		setLastName(lastName);
		setSubDepartment(subDepartment);
	}

	public HospitalUser(int id) {
		super();
		this._id = id;
	}

	/*
	 * field getters
	 */

	public int getId() {
		if (id == null) {
			return _id;
		} else {
			return id.get();
		}
	}

	public String getFirstName() {
		if (firstName == null) {
			return _firstName;
		} else {
			return firstName.get();
		}
	}

	public String getLastName() {
		if (lastName == null) {
			return _lastName;
		} else {
			return lastName.get();
		}
	}

	public SubDepartment getSubDepartment() {
		if (subDepartment == null) {
			return _subDepartment;
		} else {
			return subDepartment.get();
		}
	}

	/*
	 * field setters
	 */

	public void setId(int id) {
		if (this.id == null) {
			_id = id;
		} else {
			this.id.set(id);
		}
	}

	public void setFirstName(String firstName) {
		if (this.firstName == null) {
			_firstName = firstName;
		} else {
			this.firstName.set(firstName);
		}
	}

	public void setLastName(String lastName) {
		if (this.lastName == null) {
			_lastName = lastName;
		} else {
			this.lastName.set(lastName);
		}
	}

	public void setSubDepartment(SubDepartment subDepartment) {
		if (this.subDepartment == null) {
			_subDepartment = subDepartment;
		} else {
			this.subDepartment.set(subDepartment);
		}
	}

	/*
	 * properties getters
	 */

	public IntegerProperty idProperty() {
		if (id == null) {
			id = new SimpleIntegerProperty(_id);
		}
		return id;
	}

	public StringProperty firstNameProperty() {
		if (firstName == null) {
			firstName = new SimpleStringProperty(_firstName);
		}
		return firstName;
	}

	public StringProperty lastNameProperty() {
		if (lastName == null) {
			lastName = new SimpleStringProperty(_lastName);
		}
		return lastName;
	}

	public ObjectProperty<SubDepartment> subDepartmentProperty() {
		if (subDepartment == null) {
			subDepartment = new SimpleObjectProperty<SubDepartment>(_subDepartment);
		}
		return subDepartment;
	}

	@Override
	public String toString() {
		return String.format("%s %s", getFirstName(), getLastName());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + _id;
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
		HospitalUser other = (HospitalUser) obj;
		if (_id != other._id) {
			return false;
		}
		return true;
	}

}
