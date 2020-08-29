package Model;

import java.io.Serializable;
import java.util.HashSet;

import Exceptions.DiseaseWithNoSymptomsException;
import Utils.Symptoms;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Disease implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 9054769563038430354L;
	private static int ID = 1;

	private transient SimpleIntegerProperty id;
	private transient SimpleStringProperty diseaseName;
	private transient SimpleListProperty<Symptoms> symptoms;

	private int _id;
	private String _diseaseName;
	private HashSet<Symptoms> _symptoms;

	public Disease(String diseaseName) {
		super();
		setId(ID++);
		setDiseaseName(diseaseName);
		this._symptoms = new HashSet<Symptoms>();
		this.symptoms = new SimpleListProperty<>(getSymptomsObservableList());

	}

	public Disease(String diseaseName, HashSet<Symptoms> symptoms) {
		super();
		setId(ID++);
		setDiseaseName(diseaseName);
		this._symptoms = new HashSet<Symptoms>();
		setSymptoms(symptoms);
		this.symptoms = new SimpleListProperty<>(getSymptomsObservableList());

	}

	public Disease(int id) {
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

	public String getName() {
		if (diseaseName == null) {
			return _diseaseName;
		} else {
			return diseaseName.get();
		}
	}

	public HashSet<Symptoms> getSymptoms() {
		return _symptoms;
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

	public void setDiseaseName(String diseaseName) {
		if (this.diseaseName == null) {
			_diseaseName = diseaseName;
		} else {
			this.diseaseName.set(diseaseName);
		}
	}

	public String setSymptoms(HashSet<Symptoms> symptoms) {
		try {
			if (symptoms.isEmpty()) {
				throw new DiseaseWithNoSymptomsException();
			}
			this._symptoms = symptoms;
			return "Success";
		} catch (DiseaseWithNoSymptomsException e) {
			ID--;
			return e.toString();
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

	public StringProperty diseaseNameProperty() {
		if (diseaseName == null) {
			diseaseName = new SimpleStringProperty(_diseaseName);
		}
		return diseaseName;
	}

	public ObservableList<Symptoms> getSymptomsObservableList() {
		ObservableList<Symptoms> output = FXCollections.observableArrayList(getSymptoms());
		return output;
	}

	@Override
	public String toString() {
		return "Disease [name=" + _diseaseName + ", symptoms=" + _symptoms + "]";
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
		Disease other = (Disease) obj;
		if (_id != other._id) {
			return false;
		}
		return true;
	}

	public int compareTo(Disease o) {
		return o.getName().compareTo(this.getName());
	}

}
