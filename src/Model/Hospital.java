package Model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Observable;
import java.util.TreeSet;

import Exceptions.CannotReleasePatientException;
import Utils.Condition;
import Utils.ReleaseNote;
import Utils.Specialization;
import Utils.Symptoms;
import Utils.Treatments;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.collections.ObservableSet;

public class Hospital implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9054769563038430354L;
	private static Hospital theHospital = null;

	private static Hospital deserialize() {
		Hospital instance = new Hospital();
		try {
			System.out.println("DESERIALIZING");
			FileInputStream inputFile = new FileInputStream("Hospital.ser");
			ObjectInputStream inStream = new ObjectInputStream(inputFile);
			instance = (Hospital) inStream.readObject();

			inputFile.close();
			inStream.close();
			return instance;
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return instance;

	}

	public static Hospital getInstance() {
		try {
			File serializedFile = new File("Hospital.ser");
			if (theHospital == null && serializedFile.exists()) {
				System.out.println("FILE EXISTS, INVOKING DESERIALIZNG");

				theHospital = deserialize();
			}

			else if (theHospital == null && !serializedFile.exists()) {
				System.out.println("FILE Does NOT EXIST");
				theHospital = new Hospital();
				Hospital.serialize();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return theHospital;
	}

	private static void serialize() {
		try {
			System.out.println("SERIALIZING");
			FileOutputStream outputFile = new FileOutputStream("Hospital.ser");
			ObjectOutputStream outputStream = new ObjectOutputStream(outputFile);
			outputStream.writeObject(theHospital);
			outputFile.close();
			outputFile.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private HashMap<Integer, Department> departmentsById;
	private HashMap<Integer, Disease> diseasesById;
	private HashMap<Integer, Doctor> doctorsById;
	private HashMap<Patient, HashSet<Doctor>> doctorsByPatient;
	private HashMap<Integer, Patient> hotelPatientsById;
	private HashMap<Integer, Nurse> nursesById;
	private HashMap<Patient, HashSet<Nurse>> nursesByPatient;
	private HashMap<Integer, Patient> patientsById;

	private HashMap<Integer, PatientReport> reportsById;

	private HashMap<Integer, SubDepartment> subDepartmentById;

	private Hospital() {
		this.patientsById = new HashMap<>();
		this.nursesById = new HashMap<>();
		this.reportsById = new HashMap<>();
		this.doctorsById = new HashMap<>();
		this.hotelPatientsById = new HashMap<>();
		this.diseasesById = new HashMap<>();
		this.departmentsById = new HashMap<>();
		this.subDepartmentById = new HashMap<>();
		this.doctorsByPatient = new HashMap<>();
		this.nursesByPatient = new HashMap<>();

	}

	public boolean addDepartment(Department d) {
		if (d == null) {
			return false;
		}
		if (!this.getDepartmentsById().containsValue(d)) {
			this.getDepartmentsById().put(d.getId(), d);
			Hospital.serialize();
		} else {
			return false;
		}
		return true;
	}

	public boolean addDisease(Disease disease) {
		if (disease == null) {
			return false;
		}
		if (!this.getDiseasesById().containsValue(disease)) {
			this.getDiseasesById().put(disease.getId(), disease);
			Hospital.serialize();

			return true;
		}
		return false;
	}

	public boolean addDoctor(Doctor doc, SubDepartment s) {
		if (doc == null || s == null) {
			return false;
		}
		if (!this.getDoctorsById().containsValue(doc)) {
			this.getDoctorsById().put(doc.getId(), doc);
		} else {
			return false;
		}
		s.addDoctor(doc);
		Hospital.serialize();

		return true;
	}

	public boolean addNurse(Nurse nurse, SubDepartment s) {
		if (nurse == null || s == null) {
			return false;
		}
		if (!this.getNursesById().containsValue(nurse)) {
			this.getNursesById().put(nurse.getId(), nurse);
		} else {
			return false;
		}
		s.addNurse(nurse);
		Hospital.serialize();

		return true;
	}

	public boolean addPatient(Patient patient, SubDepartment s) {
		if (patient == null || s == null) {
			return false;
		}
		if (!this.getPatientsById().containsValue(patient)) {
			this.getPatientsById().put(patient.getId(), patient);
		} else {
			return false;
		}
		s.addPatient(patient);
		Hospital.serialize();

		return true;
	}

	public PatientReport addPatientReport(Patient pat, Doctor doc, Date date, Disease des, ReleaseNote note) {

		if (doc == null) {
			return null;
		}
		if (pat == null) {
			return null;
		}
		if (des == null) {
			return null;
		}
		if (note == null) {
			return null;
		}
		PatientReport pr = new PatientReport(pat, doc, date, des, pat.getsDepartment(), note);
		this.getReportsById().put(pr.getId(), pr);
		pat.getsDepartment().addPatientReport(pr);
		Hospital.serialize();

		return pr;

	}

	public boolean addSubDepartment(Department d, SubDepartment s) {
		if (this.getSubDepartmentById().put(s.getId(), s) == null) {
			Hospital.serialize();
			return true;
		} else {
			Hospital.serialize();
			return false;
		}

	}

	public Nurse findHardestWorkingNurse() {
		HashMap<Nurse, Integer> nursesPatients = new HashMap<>();
		for (Patient p : this.getPatientsById().values()) {
			if (this.getNursesByPatient().containsKey(p)) {
				for (Nurse n : this.getNursesByPatient().get(p)) {
					if (nursesPatients.containsKey(n)) {
						nursesPatients.put(n, nursesPatients.get(n) + 1);
					} else {
						nursesPatients.put(n, 1);
					}
				}
			}
		}
		Nurse hardWorkingNurse = null;
		int maxNumber = 0;
		for (Nurse n : nursesPatients.keySet()) {
			if (hardWorkingNurse == null) {
				hardWorkingNurse = n;
				maxNumber = nursesPatients.get(n);
			} else if (maxNumber < nursesPatients.get(n)) {
				hardWorkingNurse = n;
				maxNumber = nursesPatients.get(n);
			}
		}
		return hardWorkingNurse;
	}

	public ArrayList<Patient> getAllBadConditionPatients(Doctor d) {
		ArrayList<Patient> patients = new ArrayList<>();
		for (Patient p : this.getPatientsById().values()) {
			if (this.getDoctorsByPatient().containsKey(p)) {
				if (this.getDoctorsByPatient().get(p).contains(d) && (p.getCondition().equals(Condition.CRITICAL)
						|| p.getCondition().equals(Condition.SERIOUS))) {
					patients.add(p);
				}
			}
		}
		patients.sort(new Comparator<Patient>() {

			@Override
			public int compare(Patient o1, Patient o2) {
				Integer o1Status = o1.getStatus();
				Integer o2Status = o2.getStatus();
				return o2Status.compareTo(o1Status);
			}
		});
		return patients;
	}

	public LinkedList<Patient> getAllDifficultBreathingPatients(Department d) {
		LinkedList<Patient> patients = new LinkedList<>();
		for (SubDepartment s : d.getSdepts()) {
			for (Patient p : s.getPatients()) {
				if (p.getDis().getSymptoms().contains(Symptoms.DIFFICULTY_BREATHING)
						&& this.getNursesByPatient().containsKey(p)) {
					for (Nurse n : this.getNursesByPatient().get(p)) {
						if (n.getTreat().equals(Treatments.BREATHING_SUPPORT)) {
							patients.add(p);
							break;
						}
					}
				}
			}
		}
		patients.sort(new Comparator<Patient>() {

			@Override
			public int compare(Patient o1, Patient o2) {
				if (o1.getsDepartment().getId() != o2.getsDepartment().getId()) {
					Integer o1SubID = o1.getsDepartment().getId();
					Integer o2SubID = o2.getsDepartment().getId();
					return o2SubID.compareTo(o1SubID);
				} else {
					return o2.getLname().compareTo(o1.getLname());
				}
			}
		});
		return patients;
	}

	public TreeSet<SubDepartment> getBestStatusSubDepartments() {
		TreeSet<SubDepartment> subs = new TreeSet<>(new Comparator<SubDepartment>() {

			@Override
			public int compare(SubDepartment o1, SubDepartment o2) {
				Integer o1Size = o1.getPatients().size();
				Integer o2Size = o2.getPatients().size();
				return o2Size.compareTo(o1Size);
			}
		});
		HashMap<SubDepartment, Integer> subCountGoodPatients = new HashMap<>();
		for (SubDepartment s : this.getSubDepartmentById().values()) {
			int counter = 0;
			for (Patient p : s.getPatients()) {
				if (p.getCondition().equals(Condition.GOOD)) {
					counter++;
				}
			}
			subCountGoodPatients.put(s, counter);
		}
		int max = 0;
		int secondMax = 0;
		SubDepartment bestSub = null;
		SubDepartment secondBestSub = null;
		for (SubDepartment s : subCountGoodPatients.keySet()) {
			if (subCountGoodPatients.get(s) > max) {
				secondBestSub = bestSub;
				secondMax = max;
				max = subCountGoodPatients.get(s);
				bestSub = s;
				continue;
			} else if (subCountGoodPatients.get(s) > secondMax) {
				secondBestSub = s;
				secondMax = subCountGoodPatients.get(s);
				continue;
			}
		}
		subs.add(bestSub);
		subs.add(secondBestSub);
		return subs;
	}

	public TreeSet<Patient> getCriticalSteroidsNeuPatients() {
		TreeSet<Patient> patients = new TreeSet<>(new Comparator<Patient>() {

			@Override
			public int compare(Patient o1, Patient o2) {
				if (!o1.getLname().equals(o2.getLname())) {
					return o1.getLname().compareTo(o2.getLname());
				} else {
					return o1.getFname().compareTo(o2.getFname());
				}
			}
		});
		for (Patient p : this.getPatientsById().values()) {
			if (!p.getCondition().equals(Condition.CRITICAL)) {
				continue;
			}
			boolean hasNurse = false;
			boolean hasDoctor = false;
			if (this.getNursesByPatient().containsKey(p)) {
				for (Nurse n : this.getNursesByPatient().get(p)) {
					if (n.getTreat().equals(Treatments.STEROIDS)) {
						hasNurse = true;
						break;
					}
				}
			}
			if (hasNurse && this.getDoctorsByPatient().containsKey(p)) {
				for (Doctor d : this.getDoctorsByPatient().get(p)) {
					if (d.getSpec().equals(Specialization.NEUROLOGY)) {
						hasDoctor = true;
						break;
					}
				}
			}
			if (hasNurse && hasDoctor) {
				patients.add(p);
			}
		}
		return patients;
	}

	public HashMap<Integer, Department> getDepartmentsById() {
		return this.departmentsById;
	}

	public HashMap<Integer, Disease> getDiseasesById() {
		return this.diseasesById;
	}

	public TreeSet<Disease> getDiseasesByRange(char start, char end) {
		TreeSet<Disease> diseases = new TreeSet<>();
		for (Disease d : this.getDiseasesById().values()) {
			char[] dName = d.getName().toLowerCase().toCharArray();
			if (dName[0] >= start && dName[0] <= end) {
				diseases.add(d);
			}
		}
		return diseases;
	}

	public TreeSet<Doctor> getDoctorBySpec(Specialization s) {
		TreeSet<Doctor> doctors = new TreeSet<>(new Comparator<Doctor>() {

			@Override
			public int compare(Doctor o1, Doctor o2) {
				Integer o1Count = o1.getShiftCounter();
				Integer o2Count = o2.getShiftCounter();
				return o2Count.compareTo(o1Count);
			}
		});
		for (PatientReport pr : this.getReportsById().values()) {
			if (pr.getDoctor().getSpec().equals(s)) {
				doctors.add(pr.getDoctor());
			}
		}
		return doctors;
	}

	public HashMap<Integer, Doctor> getDoctorsById() {
		return this.doctorsById;
	}

	public HashMap<Patient, HashSet<Doctor>> getDoctorsByPatient() {
		return this.doctorsByPatient;
	}

	public HashMap<Integer, Patient> getHotelPatientsById() {
		return this.hotelPatientsById;
	}

	public HashMap<Integer, Nurse> getNursesById() {
		return this.nursesById;
	}

	public HashMap<Patient, HashSet<Nurse>> getNursesByPatient() {
		return this.nursesByPatient;
	}

	public HashMap<Integer, Patient> getPatientsById() {
		return this.patientsById;
	}

	public Department getRealDepartment(int did) {
		return this.getDepartmentsById().get(did);
	}

	public Disease getRealDisease(int did) {
		return this.getDiseasesById().get(did);
	}

	public Doctor getRealDoctor(int did) {
		return this.getDoctorsById().get(did);
	}

	public Nurse getRealNurse(int nid) {
		return this.getNursesById().get(nid);
	}

	public Patient getRealPatient(int pid) {
		return this.getPatientsById().get(pid);
	}

	public PatientReport getRealPatientReport(int prid) {
		return this.getReportsById().get(prid);
	}

	public SubDepartment getRealSubDepartment(int sid) {
		return this.getSubDepartmentById().get(sid);
	}

	public HashMap<Integer, PatientReport> getReportsById() {
		return this.reportsById;
	}

	public HashMap<Integer, SubDepartment> getSubDepartmentById() {
		return this.subDepartmentById;
	}

	public boolean printAllDoctors(Department dep) {
		if (dep == null) {
			return false;
		}
		dep.printAllDoctors();
		return true;

	}

	public boolean printAllNurses(Department d) {
		if (d == null) {
			return false;
		}

		d.printAllNurses();
		return true;

	}

	public boolean printAllPatients(Department dep) {
		if (dep == null) {
			return false;
		}
		dep.printAllPatients();
		return true;
	}

	public boolean removeDepartment(Department d) {
		return this.getDepartmentsById().remove(d.getId(), d);
	}

	public boolean removeDisease(Disease disease) {
		if (disease == null) {
			return false;
		}
		this.getDiseasesById().remove(disease.getId(), disease);
		return true;
	}

	public boolean removeDoctor(Doctor doc) {
		if (doc == null) {
			return false;
		}
		doc.getsDepartment().removeDoctor(doc);
		this.getDoctorsById().remove(doc.getId(), doc);
		return true;

	}

	public boolean removeNurse(Nurse nurse) {
		if (nurse == null) {
			return false;
		}
		nurse.getsDepartment().removeNurse(nurse);
		this.getNursesById().remove(nurse.getId(), nurse);
		return true;

	}

	public boolean removePatient(Patient patient) {
		if (patient == null) {
			return false;
		}
		patient.getsDepartment().removePatient(patient);
		this.getPatientsById().remove(patient.getId(), patient);
		this.getDoctorsByPatient().remove(patient);
		this.getNursesByPatient().remove(patient);
		return true;

	}

	public boolean removePatientReport(PatientReport pr) {
		if (pr == null) {
			return false;
		}
		this.getReportsById().remove(pr.getId(), pr);
		pr.getSdept().removePatientReport(pr);
		return true;
	}

	public String removeRecoverPatient(Patient patient) {
		try {
			if (patient.checkCondition().equals(ReleaseNote.CAN_GO_HOME)) {
				boolean res = this.removePatient(patient);
				if (res) {
					return "Success";
				} else {
					return "Fail";
				}
			} else {
				throw new CannotReleasePatientException(patient.checkCondition(), ReleaseNote.CAN_GO_HOME);
			}
		} catch (CannotReleasePatientException e) {
			return e.toString();
		}
	}

	public boolean removeSubDepartment(SubDepartment s) {
		return this.getSubDepartmentById().remove(s.getId(), s);
	}

	public String removeToHotelPatient(Patient patient) {
		try {

			if (patient.checkCondition().equals(ReleaseNote.MOVE_TO_HOTEL)) {
				boolean res = this.removePatient(patient);
				if (res) {
					this.getHotelPatientsById().put(patient.getId(), patient);
					return "Success";
				} else {
					return "Fail";
				}
			} else {
				throw new CannotReleasePatientException(patient.checkCondition(), ReleaseNote.MOVE_TO_HOTEL);
			}
		} catch (CannotReleasePatientException e) {
			return e.toString();
		}
	}

	public void setDepartmentsById(HashMap<Integer, Department> departmentsById) {
		this.departmentsById = departmentsById;
	}

	public void setDiseasesById(HashMap<Integer, Disease> diseasesById) {
		this.diseasesById = diseasesById;
	}

	public void setDoctorsById(HashMap<Integer, Doctor> doctorsById) {
		this.doctorsById = doctorsById;
	}

	public void setDoctorsByPatient(HashMap<Patient, HashSet<Doctor>> doctorsByPatient) {
		this.doctorsByPatient = doctorsByPatient;
	}

	public void setHotelPatientsById(HashMap<Integer, Patient> hotelPatientsById) {
		this.hotelPatientsById = hotelPatientsById;
	}

	public void setNursesById(HashMap<Integer, Nurse> nursesById) {
		this.nursesById = nursesById;
	}

	public void setNursesByPatient(HashMap<Patient, HashSet<Nurse>> nursesByPatient) {
		this.nursesByPatient = nursesByPatient;
	}

	public void setPatientsById(HashMap<Integer, Patient> patientsById) {
		this.patientsById = patientsById;
	}

	public void setReportsById(HashMap<Integer, PatientReport> reportsById) {
		this.reportsById = reportsById;
	}

	public void setSubDepartmentById(HashMap<Integer, SubDepartment> subDepartmentById) {
		this.subDepartmentById = subDepartmentById;
	}

	public TreeSet<Patient> treatDiseases(Department d) {
		TreeSet<Patient> viralPatients = new TreeSet<>();
		TreeSet<Patient> chronicPatients = new TreeSet<>();
		for (SubDepartment s : d.getSdepts()) {
			Iterator<Doctor> doctorIter = s.getDoctors().iterator();
			for (Patient p : s.getPatients()) {
				if (!doctorIter.hasNext()) {
					doctorIter = s.getDoctors().iterator();
				}
				doctorIter.next().checkDisease(p);
			}
		}
		for (SubDepartment s : d.getSdepts()) {
			for (Patient p : s.getPatients()) {
				if (p.getDis() instanceof ChronicDisease) {
					chronicPatients.add(p);
				} else if (p.getDis() instanceof ViralDisease) {
					viralPatients.add(p);
				}
			}
		}
		return (viralPatients.size() > chronicPatients.size() ? viralPatients : chronicPatients);
	}

	public TreeSet<Patient> treatPatients(Department d) {
		TreeSet<Patient> patients = new TreeSet<>(new Comparator<Patient>() {

			@Override
			public int compare(Patient o1, Patient o2) {
				Integer o1ID = o1.getId();
				Integer o2ID = o2.getId();
				return o1ID.compareTo(o2ID);
			}

		});
		for (SubDepartment s : d.getSdepts()) {
			Iterator<Nurse> nurseIter = s.getNurses().iterator();
			for (Patient p : s.getPatients()) {
				Condition oldCondition = p.getCondition();
				if (!nurseIter.hasNext()) {
					nurseIter = s.getNurses().iterator();
				}
				nurseIter.next().checkPatient(p);
				if (!p.getCondition().equals(oldCondition)) {
					patients.add(p);
				}
			}
		}
		return patients;
	}

}
