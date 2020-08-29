package View;

import java.security.SecureRandom;
import java.util.HashSet;

import Model.Admin;
import Model.Department;
import Model.Disease;
import Model.Doctor;
import Model.Hospital;
import Model.Nurse;
import Model.Patient;
import Model.SubDepartment;
import Utils.Specialization;
import Utils.Symptoms;
import Utils.Treatments;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Start extends Application {
	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage loginStage) throws Exception {
		try {
			createUsers();
			Parent root = FXMLLoader.load(this.getClass().getResource("LoginView.fxml"));
			Stage stage = new Stage();
			stage.setTitle("welcome to SMARTHOSP");

			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.setResizable(false);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void createUsers() {
		SecureRandom sRandom = new SecureRandom();

		Hospital.getInstance();
		Department d = new Department("dept1", Specialization.EMERGENCY);
		SubDepartment sd = new SubDepartment(d);
		HashSet<Symptoms> symptoms = new HashSet<Symptoms>();
		symptoms.add(Symptoms.DIFFICULTY_BREATHING);
		Disease disease = new Disease("corona", symptoms);
		Hospital.getInstance().addDisease(disease);
		Admin admin = new Admin("admin", "admin");

		Hospital.getInstance().addProgramUser(admin);

		System.out.println("Adding department... : " + d);

		Hospital.getInstance().addDepartment(d);

		System.out.println("Adding subdepartment... : " + sd);

		Hospital.getInstance().addSubDepartment(d, sd);

		for (int i = 1; i < 15; i++) {
			Doctor doctor = new Doctor("house", "md", Specialization.EMERGENCY, sd, "d" + i, "" + i);
			Hospital.getInstance().addDoctor(doctor, sd);
		}

		for (int i = 1; i < 5; i++) {
			Patient patient = new Patient("patientName" + i, "patientSurName" + i, sd, disease);
			patient.setStatus(sRandom.nextInt(101));
			Hospital.getInstance().addPatient(patient, sd);
		}

		for (int i = 1; i < 30; i++) {
			Nurse nurse = new Nurse("nathan" + i, "gurfinkel" + i, Treatments.BLOOD_PLASMA_TRANSFUSIONS, sd, "n" + i, "" + i);
			Hospital.getInstance().addNurse(nurse, sd);
		}

	}

}