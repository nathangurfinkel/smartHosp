package View;

import java.io.IOException;
import java.net.URL;
import java.util.Map.Entry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

import Model.Department;
import Model.Disease;
import Model.Doctor;
import Model.Hospital;
import Model.Nurse;
import Model.Patient;
import Model.SubDepartment;
import Utils.Specialization;
import Utils.Treatments;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.collections.ObservableSet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Duration;

public class FXMLController implements Initializable {

	@FXML
	private ToggleButton darkToggle;

	@FXML
	private Button manageEntriesButton;

	@FXML
	private VBox mainNode;

	@FXML
	private Button forgotPasswordButton;

	@FXML
	private Button loginButton;

	@FXML
	private AnchorPane loginPage;

	@FXML
	private AnchorPane loginPane;

	@FXML
	private AnchorPane logoPane;

	@FXML

	private PasswordField passwordField;
	@FXML
	private Pane Patients;

	@FXML
	private Tab tab;
	@FXML
	private TextField userNameField;
	@FXML
	ImageView userPic;

	@FXML
	private AnchorPane patientsPane;

	@FXML
	private TableView<?> patientsTable;

	@FXML
	private TableColumn<?, ?> firstNameColumn;

	@FXML
	private TableColumn<?, ?> lastNameColumn;

	@FXML
	private TableColumn<?, ?> conditionColumn;

	@FXML
	private TableColumn<?, ?> dateRegisteredColumn;

	@FXML
	private Label tableTitle;

	@FXML
	private Button patientsMenuButton;

	@FXML
	private Button nursesMenuButton;

	@FXML
	private Button diseasesMenuButton;

	@FXML
	private Button controlCenterButton;

	@FXML
	private Button addNurseButton;

	@FXML
	private Button removeNurseButton;

	@FXML
	void addNurseButtonHandler(ActionEvent event) {
		Department d = new Department("NATHANOLOGY", Specialization.UROLOGY);
		SubDepartment sd = new SubDepartment(d);
		Nurse nurse = new Nurse("nathan", "gurfinkel", Treatments.BREATHING_SUPPORT, sd);
		Hospital.getInstance().addDepartment(d);
		Hospital.getInstance().addSubDepartment(d, sd);
		Hospital.getInstance().addNurse(nurse, sd);
		System.out.println(Hospital.getInstance().getNursesById());
	}

	@FXML
	void removeNurseButtonHandler(ActionEvent event) {
		System.out.println(Hospital.getInstance().getNursesById());

	}

	@FXML
	void controlCenterButtonHandler(ActionEvent event) {

	}

	@FXML
	void darkMode(MouseEvent event) {

	}

	@FXML
	void diseasesMenuButtonHandler(ActionEvent event) {

	}

	private ObservableList<?> populateTable(String className) {

		switch (className) {
		case "Nurse": {

			ArrayList<Nurse> valueList = new ArrayList<Nurse>(Hospital.getInstance().getNursesById().values());

			ObservableList<Nurse> data = FXCollections.observableArrayList(valueList);

			return data;

		}

		case "Doctor": {

			ArrayList<Doctor> valueList = new ArrayList<Doctor>(Hospital.getInstance().getDoctorsById().values());

			ObservableList<Doctor> data = FXCollections.observableArrayList(valueList);

			return data;

		}
		case "Disease": {

			ArrayList<Disease> valueList = new ArrayList<Disease>(Hospital.getInstance().getDiseasesById().values());

			ObservableList<Disease> data = FXCollections.observableArrayList(valueList);

			return data;

		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + className);
		}

	}

	@FXML
	void nursesMenuButtonHandler(ActionEvent event) {

		try {
			TableView table = new TableView();
			table.setItems(populateTable("Nurse"));
			HBox buttonBar = FXMLLoader.load(getClass().getResource("NursesButtonBar.fxml"));
			// mainAnchorPane.prefWidthProperty().bind(mainNode.widthProperty()());
			// mainAnchorPane.prefHeightProperty().bind(mainNode.heightProperty());

			VBox.setVgrow(table, Priority.ALWAYS);
			mainNode.getChildren().clear();
			mainNode.getChildren().add(table);
			mainNode.getChildren().add(buttonBar);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void patientsMenuButtonHandler(ActionEvent event) {

		try {
			VBox patientsList = FXMLLoader.load(getClass().getResource("Patients.fxml"));
			HBox buttonBar = FXMLLoader.load(getClass().getResource("PatientsButtonBar.fxml"));
			// mainAnchorPane.prefWidthProperty().bind(mainNode.widthProperty()());
			// mainAnchorPane.prefHeightProperty().bind(mainNode.heightProperty());
			VBox.setVgrow(patientsList, Priority.ALWAYS);
			mainNode.getChildren().clear();
			mainNode.getChildren().add(patientsList);
			mainNode.getChildren().add(buttonBar);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML

	private void addPatient() {
		int count = 0;
		SubDepartment sd = new SubDepartment(1);
		Disease d = new Disease(1);
		Patient patient = new Patient("Nathan", "Gurfin", sd, d);
		Hospital.getInstance().getRealSubDepartment(1).addPatient(patient);
		;
	}

	public void darkMode() {
		if (this.darkToggle.isSelected()) {
			System.out.println("selected");
			this.darkToggle.setText("light mode");
		} else {
			this.darkToggle.setText("dark mode");
		}
	}

	@FXML
	public void forgotpassword(ActionEvent actionEvent) {
		WebView forgotPasswordViewer = new WebView();

		VBox root = new VBox();
		root.getChildren().addAll(forgotPasswordViewer);

		Scene scene = new Scene(root, 800, 500);
	}

	@FXML
	private void handleLoginButton() throws IOException {
		// if(the Password IS ok)
		makeFadeOut();
		Stage stage = (Stage) loginButton.getScene().getWindow();
		stage.close();
		Parent mainSceneParent = FXMLLoader.load(getClass().getResource("MainScene.fxml"));

		Scene scene2 = new Scene(mainSceneParent);

		Stage window = new Stage();
		window.setScene(scene2);
		window.show();

	}

//@FXML

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {

	}

	private void loadMainPage() {
		try {
			Parent mainPageParent;
			mainPageParent = FXMLLoader.load(getClass().getResource("MainScene.fxml"));
			Scene mainScene = new Scene(mainPageParent);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private void makeFadeOut() {
		System.out.println("makefadeout");
		FadeTransition fadeTransition = new FadeTransition();
		fadeTransition.setDuration(Duration.millis(1000));
		fadeTransition.setNode(loginPage);
		fadeTransition.setFromValue(1);
		fadeTransition.setToValue(0);
		fadeTransition.setOnFinished((ActionEvent event) -> {
			// loadMainPage();
		});
		fadeTransition.play();

	}

	@FXML
	private void secondMenuButtonHandler() {

	}

	@FXML
	private void thirdMenuButtonHandler() {

	}

}
