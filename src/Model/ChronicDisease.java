package Model;

import java.io.Serializable;
import java.util.HashSet;

import Utils.Symptoms;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class ChronicDisease extends Disease implements Serializable {

	/**
	 *
	 */

	private transient SimpleBooleanProperty isInherited;

	private static final long serialVersionUID = 9054769563038430354L;
	private boolean _isInherited;

	public ChronicDisease(String name, HashSet<Symptoms> symptoms, boolean isInherited) {
		super(name, symptoms);
		setIsInherited(isInherited);
	}

	public ChronicDisease(int id) {
		super(id);
	}

	/*
	 * field getters
	 */
	public boolean getIsInherited() {
		if (isInherited == null) {
			return _isInherited;
		} else {
			return isInherited.get();
		}
	}

	/*
	 * field setters
	 */
	public void setIsInherited(boolean isInherited) {
		if (this.isInherited == null) {
			_isInherited = isInherited;
		} else {
			this.isInherited.set(isInherited);
		}
	}

	/*
	 * properties getters
	 */

	public BooleanProperty isInheritedProperty() {
		if (isInherited == null) {
			isInherited = new SimpleBooleanProperty(_isInherited);
		}
		return isInherited;
	}

	@Override
	public String toString() {
		return super.toString();
	}

}
