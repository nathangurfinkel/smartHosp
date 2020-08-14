package Model;

import java.io.Serializable;
import java.util.HashSet;

import Exceptions.DiseaseWithNoSymptomsException;
import Utils.Symptoms;

public class Disease implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9054769563038430354L;
	private static int ID = 1;

	private int id;
	private String name;
	private HashSet<Symptoms> symptoms;

	public Disease(String name) {
		super();
		this.id = ID++;
		this.name = name;
		this.symptoms = new HashSet<Symptoms>();
	}

	public Disease(int id, String name, HashSet<Symptoms> syms) {
		super();
		this.id = id;
		this.name = name;
		this.symptoms = new HashSet<Symptoms>();
		this.symptoms.addAll(syms);
	}

	public Disease(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public HashSet<Symptoms> getSymptoms() {
		return symptoms;
	}

	public String setSymptoms(HashSet<Symptoms> symptoms) {
		try {
			if (symptoms.isEmpty()) {
				throw new DiseaseWithNoSymptomsException();
			}
			this.symptoms = symptoms;
			return "Success";
		} catch (DiseaseWithNoSymptomsException e) {
			ID--;
			return e.toString();
		}
	}

	@Override
	public String toString() {
		return "Disease [name=" + name + ", symptoms=" + symptoms + "]";
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
		Disease other = (Disease) obj;
		if (id != other.id) {
			return false;
		}
		return true;
	}

	public int compareTo(Disease o) {
		return o.getName().compareTo(this.getName());
	}

}
