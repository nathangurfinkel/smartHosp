package Model;

import java.io.Serializable;
import java.util.ArrayList;

import Exceptions.SubDepartmentNotEmptyException;
import Utils.Specialization;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Department implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 9054769563038430354L;
	private static int ID = 1;

	private transient SimpleIntegerProperty id;
	private transient SimpleStringProperty departmentName;
	private transient SimpleListProperty<SubDepartment> subDepartments;
	private transient SimpleObjectProperty<Specialization> specialization;

	private int _id;
	private String _departmentName;
	private ArrayList<SubDepartment> _subDepartments;
	private Specialization _specialization;

	public Department(String departmentName, Specialization specialization) {
		super();
		setId(ID++);
		setDepartmentName(departmentName);
		setSpecialization(specialization);
		this._subDepartments = new ArrayList<SubDepartment>();
		this.subDepartments = new SimpleListProperty<>(getSubDepartmentsObservableList());
	}

	public Department(int id) {
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

	public String getDepartmentName() {
		if (departmentName == null) {
			return _departmentName;
		} else {
			return departmentName.get();
		}
	}

	public ArrayList<SubDepartment> getSubDepartments() {
		return _subDepartments;
	}

	public Specialization getSpecialization() {
		if (specialization == null) {
			return _specialization;
		} else {
			return specialization.get();
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

	public void setDepartmentName(String departmentName) {
		if (this.departmentName == null) {
			_departmentName = departmentName;
		} else {
			this.departmentName.set(departmentName);
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

	public IntegerProperty idProperty() {
		if (id == null) {
			id = new SimpleIntegerProperty(_id);
		}
		return id;
	}

	public StringProperty departmentNameProperty() {
		if (departmentName == null) {
			departmentName = new SimpleStringProperty(_departmentName);
		}
		return departmentName;
	}

	public ObjectProperty<Specialization> specializationProperty() {
		if (specialization == null) {
			specialization = new SimpleObjectProperty<Specialization>(_specialization);
		}
		return specialization;
	}

	public ObservableList<SubDepartment> getSubDepartmentsObservableList() {
		ObservableList<SubDepartment> output = FXCollections.observableArrayList(getSubDepartments());
		return output;
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
		Department other = (Department) obj;
		if (_id != other._id) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Department [deptName=" + _departmentName + ", spec=" + _specialization + "]";
	}

	/**
	 * a method that adds a subdepartment to subdepartment data structure and to the
	 * Hospital
	 *
	 * @param  subDepartment
	 * @return               true/false
	 */
	public boolean addSubDepartment(SubDepartment subDepartment) {
		if (getSubDepartments().contains(subDepartment) || Hospital.getInstance().getRealSubDepartment(subDepartment.getId()) != null) {
			System.out.println("Department - add subdept method failed 1");

			return false;
		} else {
			getSubDepartments().add(subDepartment);

			if (!Hospital.getInstance().addSubDepartment(this, subDepartment)) {
				System.out.println("Department - add subdept method failed 2");

				return false;
			}
		}
		return true;
	}

	/**
	 * a method that tries to delete a subdepartment, if its not empty it will
	 * transfer the sub to another sub
	 *
	 * @param  subdepartment to Delete
	 * @param  subdepartment to Move To
	 * @return               string
	 */
	public String removeSubDepartment(SubDepartment toDelete, SubDepartment toMoveTo) {
		if (!getSubDepartments().contains(toDelete)) {
			return null;
		} else {
			try {
				if (!toDelete.getPatients().isEmpty() || !toDelete.getDoctors().isEmpty() || !toDelete.getNurses().isEmpty() || !toDelete.getReports().isEmpty()) {
					throw new SubDepartmentNotEmptyException(toDelete.getId());
				}
				getSubDepartments().remove(toDelete);
				Hospital.getInstance().removeSubDepartment(toDelete);

			} catch (SubDepartmentNotEmptyException e) {
				moveSubDepartment(toDelete, toMoveTo);
				getSubDepartments().remove(toDelete);
				Hospital.getInstance().getSubDepartmentsById().remove(toDelete.getId(), toDelete);
				return e.toString();
			}
		}
		return "Success";
	}

	/**
	 * a method that transfers a subdepartment data structures to another
	 * subdepartment
	 *
	 * @param from
	 * @param to
	 */
	public void moveSubDepartment(SubDepartment a, SubDepartment b) {
		for (Patient p : a.getPatients()) {
			p.setSubDepartment(b);
		}
		for (Doctor d : a.getDoctors()) {
			d.setSubDepartment(b);
		}
		for (Nurse n : a.getNurses()) {
			n.setSubDepartment(b);
		}
		for (PatientReport rp : a.getReports()) {
			rp.setSdept(b);
		}
		b.getPatients().addAll(a.getPatients());
		b.getDoctors().addAll(a.getDoctors());
		b.getNurses().addAll(a.getNurses());
		b.getReports().addAll(a.getReports());
	}

	public String printAllDoctors() {
		String toReturn = "All doctors for " + this + "\n";
		for (SubDepartment s : getSubDepartments()) {
			for (Doctor d : s.getDoctors()) {
				toReturn += d.toStringLong() + "\n";
			}
		}
		return toReturn;
	}

	public String printAllNurses() {
		String toReturn = "All nurses for " + this + "\n";
		for (SubDepartment s : getSubDepartments()) {
			for (Nurse n : s.getNurses()) {
				toReturn += n.toStringLong() + "\n";
			}
		}
		return toReturn;
	}

	public String printAllPatients() {
		String toReturn = "All patients for " + this + "\n";
		for (SubDepartment s : getSubDepartments()) {
			for (Patient p : s.getPatients()) {
				toReturn += p.toStringLong() + "\n";
			}
		}
		return toReturn;
	}
}
