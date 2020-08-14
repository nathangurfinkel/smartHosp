package Model;

import java.io.Serializable;
import java.util.ArrayList;

import Exceptions.SubDepartmentNotEmptyException;
import Utils.Specialization;

public class Department implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9054769563038430354L;
	private static int ID = 1;

	private int id;

	private String deptName;
	private ArrayList<SubDepartment> sdepts;
	private Specialization spec;

	public Department(String deptName, Specialization spec) {
		super();
		this.id = ID++;
		this.deptName = deptName;
		this.spec = spec;
		this.sdepts = new ArrayList<SubDepartment>();
	}

	Department(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public String getDeptName() {
		return deptName;
	}

	public ArrayList<SubDepartment> getSdepts() {
		return sdepts;
	}

	public Specialization getSpec() {
		return spec;
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
		Department other = (Department) obj;
		if (id != other.id) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Department [deptName=" + deptName + ", spec=" + spec + "]";
	}

	/**
	 * a method that adds a subdepartment to subdepartment data structure and to the
	 * Hospital
	 * 
	 * @param subDepartment
	 * @return true/false
	 */
	public boolean addSubDepartment(SubDepartment s) {
		if (getSdepts().contains(s) || Hospital.getInstance().getRealSubDepartment(s.getId()) != null) {
			return false;
		} else {
			getSdepts().add(s);
			if (!Hospital.getInstance().addSubDepartment(this, s)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * a method that tries to delete a subdepartment, if its not empty it will
	 * transfer the sub to another sub
	 * 
	 * @param subdepartment to Delete
	 * @param subdepartment to Move To
	 * @return string
	 */
	public String removeSubDepartment(SubDepartment toDelete, SubDepartment toMoveTo) {
		if (!getSdepts().contains(toDelete)) {
			return null;
		} else {
			try {
				if (!toDelete.getPatients().isEmpty() || !toDelete.getDoctors().isEmpty()
						|| !toDelete.getNurses().isEmpty() || !toDelete.getReports().isEmpty()) {
					throw new SubDepartmentNotEmptyException(toDelete.getId());
				}
				getSdepts().remove(toDelete);
				Hospital.getInstance().removeSubDepartment(toDelete);

			} catch (SubDepartmentNotEmptyException e) {
				moveSubDepartment(toDelete, toMoveTo);
				getSdepts().remove(toDelete);
				Hospital.getInstance().getSubDepartmentById().remove(toDelete.getId(), toDelete);
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
			p.setsDepartment(b);
		}
		for (Doctor d : a.getDoctors()) {
			d.setsDepartment(b);
		}
		for (Nurse n : a.getNurses()) {
			n.setsDepartment(b);
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
		for (SubDepartment s : getSdepts()) {
			for (Doctor d : s.getDoctors()) {
				toReturn += d.toStringLong() + "\n";
			}
		}
		return toReturn;
	}

	public String printAllNurses() {
		String toReturn = "All nurses for " + this + "\n";
		for (SubDepartment s : getSdepts()) {
			for (Nurse n : s.getNurses()) {
				toReturn += n.toStringLong() + "\n";
			}
		}
		return toReturn;
	}

	public String printAllPatients() {
		String toReturn = "All patients for " + this + "\n";
		for (SubDepartment s : getSdepts()) {
			for (Patient p : s.getPatients()) {
				toReturn += p.toStringLong() + "\n";
			}
		}
		return toReturn;
	}
}
