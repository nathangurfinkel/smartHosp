package Model;

import java.io.Serializable;
import java.util.HashSet;

import Utils.Symptoms;

public class ViralDisease extends Disease implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 9054769563038430354L;
	private boolean isInfectious;

	public ViralDisease(String name, HashSet<Symptoms> symptoms, boolean isInfectious) {
		super(name, symptoms);
		this.isInfectious = isInfectious;
	}

	public ViralDisease(int id) {
		super(id);
	}

	public boolean isInfectious() {
		return isInfectious;
	}

	public void setInfectious(boolean isInfectious) {
		this.isInfectious = isInfectious;
	}

	@Override
	public String toString() {
		return super.toString();
	}

}
