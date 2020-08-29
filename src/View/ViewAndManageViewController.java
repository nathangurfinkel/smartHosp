package View;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import Model.Department;
import Model.Disease;
import Model.Doctor;
import Model.Hospital;
import Model.Nurse;
import Model.Patient;
import Model.ProgramUser;
import Model.SubDepartment;
import Utils.Condition;
import Utils.Specialization;
import Utils.Symptoms;
import Utils.Treatments;
import Utils.UserPrivilege;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Callback;
import javafx.util.Duration;
import javafx.util.converter.IntegerStringConverter;

public class ViewAndManageViewController {

	private ProgramUser user;
	private CurrentView currentView;
	private SubDepartment currentSubDepartment;

	public void setCurrentView(CurrentView currentView) {
		this.currentView = currentView;
	}

	public void setCurrentSubDepartment(SubDepartment currentSubDepartment) {
		this.currentSubDepartment = currentSubDepartment;
	}

	public SubDepartment getCurrentSubDepartment() {
		return currentSubDepartment;
	}

	public void setUser(ProgramUser user) {
		this.user = user;
	}

	public ProgramUser getUser() {
		return user;
	}

	public UserPrivilege getUserPrivelege() {
		return this.user.getPrivilege();

	}

	public CurrentView getCurrentView() {
		return currentView;
	}

	@FXML
	private VBox mainNode, adderVbox, infoVBox;

	@FXML
	private PieChart patientsPie;
	@FXML
	private Label addingBoxLabel, totalLabel, totalCriticalLabel, totalInHotelLabel;
	@FXML
	private Label dynamicLabel;
	@FXML
	private Pane slideOutPanel, confirmationPopUpPane;

	@FXML
	private CheckBox coughCheckBox, faintingCheckBox, feverCheckBox, tirednessCheckBox, difficultyBreathingCheckBox, backachesCheckBox;

	@FXML
	private VBox symptomsVBox;
	@FXML
	private HBox confirmationPopUp;
	@FXML
	private TextField diseaseNameField, fNameField, lNameField, statusField, newStatusField;

	@FXML
	private ComboBox<Specialization> specsComboBox;

	@FXML
	private ComboBox<Treatments> treatsComboBox;

	@FXML
	private TableView table;

	@FXML
	private HBox buttonsHBox, addingBoxFields, diseaseHBox;

	@FXML
	private ToggleButton addToggle;

	@FXML
	private Button removeButton, checkPatientButton, addConfirmationButton, getAllDifficultyBreathingPatientsButton, addSymptomButton;

	@FXML
	private ComboBox<SubDepartment> subDepartmentComboBox;

	@FXML
	private Label inLabel, diseaseLabel;

	@FXML
	private ComboBox<Department> departmentComboBox;

	@FXML
	private Button goButton;

	@FXML
	private Button confirmRemoveButton, cancelDeletionButton;

	@FXML
	private Button checkDiseaseButton;

	@FXML
	private Button removeRecoveredButton;

	@FXML
	private Button removeToHotelButton;
	@FXML
	private ListView symptomsListView;

	@FXML
	void removeRecoveredButtonHandler(ActionEvent event) {

	}

	@FXML
	void addSymptomButtonHandler(ActionEvent event) {

	}

	@FXML
	void getAllDifficultyBreathingPatientsHandler(ActionEvent event) {
		ObservableList<Patient> observableList = FXCollections.observableArrayList(Hospital.getInstance().getAllDifficultBreathingPatients(currentSubDepartment.getDepartment()));
		table.setItems(observableList);
	}

	@FXML
	void removeToHotelButtonHandler(ActionEvent event) {

	}

	@FXML
	protected void initialize() {
		mainNode.setOpacity(0);

		SFx.fadeNode(mainNode, true, 250);
		SFx.fadeNode(buttonsHBox, true, 500);

		addToggle.setSelected(true);

		loadLabels();

		loadConfirmationPopUp();

		loadAddingMenu();

		loadButtonBar();

		populateTable();

		loadInfoVBox();

		loadChoiceBoxes();

	}

	public void exceptionPopUp() {

	}

	@FXML
	public void loadChoiceBoxes() {

		ArrayList<Department> array = new ArrayList<Department>(Hospital.getInstance().getDepartmentsById().values());

		ObservableList<Department> departmentsObservableList = FXCollections.observableArrayList(array);
		departmentComboBox.getItems().addAll(departmentsObservableList);

		Callback<ListView<Department>, ListCell<Department>> cellFactory = new Callback<ListView<Department>, ListCell<Department>>() {

			@Override
			public ListCell<Department> call(ListView<Department> l) {
				return new ListCell<Department>() {

					@Override
					protected void updateItem(Department item, boolean empty) {
						super.updateItem(item, empty);
						if (item == null || empty) {
							setGraphic(null);
						} else {
							setText(item.getId() + "    " + item.getDepartmentName());
						}
					}
				};
			}
		};

		departmentComboBox.setButtonCell(cellFactory.call(null));
		departmentComboBox.setCellFactory(cellFactory);
		Callback<ListView<SubDepartment>, ListCell<SubDepartment>> cellFactoryForSub = new Callback<ListView<SubDepartment>, ListCell<SubDepartment>>() {

			@Override
			public ListCell<SubDepartment> call(ListView<SubDepartment> l) {
				return new ListCell<SubDepartment>() {

					@Override
					protected void updateItem(SubDepartment item, boolean empty) {
						super.updateItem(item, empty);
						if (item == null || empty) {
							setGraphic(null);
						} else {
							setText(item.getId() + "");
						}
					}
				};
			}
		};

		// Just set the button cell here:
		subDepartmentComboBox.setButtonCell(cellFactoryForSub.call(null));
		subDepartmentComboBox.setCellFactory(cellFactoryForSub);

		departmentComboBox.valueProperty().addListener(obs ->

		{

			if (departmentComboBox.getSelectionModel() == null) {
				subDepartmentComboBox.getItems().clear();
				subDepartmentComboBox.setDisable(true);
			} else {
				ObservableList<SubDepartment> subDepartmentObservableList = FXCollections.observableArrayList(departmentComboBox.getSelectionModel().getSelectedItem().getSubDepartments());
				subDepartmentComboBox.getItems().addAll(subDepartmentObservableList);
				subDepartmentComboBox.setDisable(false);
			}
		});

	}

	@FXML
	void goButtonHandler(ActionEvent event) {
		SubDepartment selectedSubDepartment = subDepartmentComboBox.getSelectionModel().getSelectedItem();

		setCurrentSubDepartment(selectedSubDepartment);

		loadConfirmationPopUp();

		loadAddingMenu();

		loadButtonBar();

		populateTable();

		loadInfoVBox();

		loadLabels();

	}

	@FXML
	void checkDiseaseButtonHandler(ActionEvent event) {
		Patient selectedPatient = (Patient) table.getSelectionModel().getSelectedItem();
		switch (getUserPrivelege()) {
			case DOCTOR: {
				((Doctor) user).checkDisease(selectedPatient);
				break;
			}
			case NURSE: {
				((Nurse) user).checkDisease(selectedPatient);
				break;
			}
			default:
				throw new IllegalArgumentException("Unexpected value: " + getUserPrivelege());
		}
	}

	@FXML
	public void loadLabels() {

		dynamicLabel.setText("Showing " + currentView.toString().toLowerCase() + " in ");
		addingBoxLabel.setText("Enter new " + currentView.toString().toLowerCase() + "' info:");

	}

	public void loadConfirmationPopUp() {
		Rectangle rect = new Rectangle(600, 60);
		confirmationPopUpPane.setClip(rect);
	}

	@FXML
	public void loadInfoVBox() {
		getAllDifficultyBreathingPatientsButton.setVisible(false);
		getAllDifficultyBreathingPatientsButton.setDisable(true);

		patientsPie.setDisable(true);
		switch (currentView) {
			case PATIENTS: {
				patientsPie.setDisable(false);
				getAllDifficultyBreathingPatientsButton.setVisible(true);
				getAllDifficultyBreathingPatientsButton.setDisable(false);

				totalLabel.setText("Total patients in the subdepartment: " + currentSubDepartment.getPatients().size());
				loadPatientsPie();
				break;

			}
			case NURSES: {
				totalLabel.setText("Total nurses in the subdepartment: " + currentSubDepartment.getNurses().size());
				break;

			}
			case DOCTORS: {
				totalLabel.setText("Total doctors in the subdepartment: " + currentSubDepartment.getDoctors().size());

				break;

			}
			case DISEASES: {
				totalLabel.setText("Total diseases in the Hospital: " + Hospital.getInstance().getDiseasesById().size());
				break;
			}
			default:
				break;
		}
	}

	@FXML
	public void loadPatientsPie() {
		System.out.println("loading pie");
		if (patientsPie.getData() != null) {
			patientsPie.getData().clear();
		}
		if (currentSubDepartment.getPatients() != null) {
			int critical = 0, fair = 0, good = 0, serious = 0;
			for (Patient p : currentSubDepartment.getPatients()) {
				System.out.println(p.getCondition());
				if (p.getCondition().equals(Condition.CRITICAL)) {
					critical++;
				} else if (p.getCondition().equals(Condition.FAIR)) {

					fair++;
				} else if (p.getCondition().equals(Condition.GOOD)) {

					good++;
				} else if (p.getCondition().equals(Condition.SERIOUS)) {

					serious++;
				}
			}
			PieChart.Data slice1 = new PieChart.Data("Good", good);
			PieChart.Data slice2 = new PieChart.Data("Fair", fair);
			PieChart.Data slice3 = new PieChart.Data("Critical", critical);
			PieChart.Data slice4 = new PieChart.Data("Serious", serious);

			patientsPie.getData().add(slice3);
			patientsPie.getData().add(slice4);
			patientsPie.getData().add(slice2);
			patientsPie.getData().add(slice1);

			final Label caption = new Label("");
			caption.setTextFill(Color.valueOf("#F39292"));
			caption.setStyle("-fx-font: 24 arial;");

			Tooltip container = new Tooltip();
			container.setGraphic(caption);

			for (final PieChart.Data data : patientsPie.getData()) {
				data.getNode().addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent e) {
						if (container.isShowing()) {
							container.hide();
						}
						caption.setText(String.format("%s: %.0f %%", data.getName(), data.getPieValue() * 100 / currentSubDepartment.getPatients().size()));
						container.show(infoVBox.getScene().getWindow(), e.getScreenX(), e.getScreenY());
					}
				});
				data.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent e) {

						getPatientsByCondition(Condition.valueOf(data.getName().toUpperCase()));
					}
				});

				data.getNode().addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent e) {
						caption.setMouseTransparent(true);
						container.hide();
					}
				});
			}
		}

	}

	@FXML
	private void loadAddingMenu() {
		adderVbox.visibleProperty().bind(addToggle.selectedProperty().not());
		adderVbox.disableProperty().bind(addToggle.selectedProperty());
		for (Node n : adderVbox.getChildren()) {
			if (!(n instanceof Button)) {
				n.setVisible(false);
				n.managedProperty().bind(n.visibleProperty());
			}
		}
		switch (currentView) {
			case PATIENTS: {

				statusField.textProperty().addListener((observable, oldValue, newValue) -> {
					if (newValue.matches("\\d*")) {
						return;
					}
					statusField.setText(newValue.replaceAll("[^\\d]", ""));
				});
				fNameField.setVisible(true);
				lNameField.setVisible(true);
				statusField.setVisible(true);
				diseaseLabel.setVisible(true);
				diseaseHBox.setVisible(true);
				break;

			}
			case NURSES: {

				treatsComboBox.setVisible(true);

				treatsComboBox.getItems().setAll(Treatments.values());

				break;

			}
			case DOCTORS: {

				specsComboBox.setVisible(true);

				specsComboBox.getItems().setAll(Specialization.values());
				break;

			}
			case DISEASES: {
				fNameField.setVisible(false);
				lNameField.setVisible(false);
				diseaseNameField.setVisible(true);

				break;

			}
			default:
				break;
		}
	}

	@FXML
	void confirmButtonHandler(ActionEvent event) {
		switch (currentView) {
			case PATIENTS: {

				HashSet<Symptoms> symptoms = new HashSet<>();
				if (coughCheckBox.isSelected()) {
					symptoms.add(Symptoms.COUGH);
				}
				if (faintingCheckBox.isSelected()) {
					symptoms.add(Symptoms.FAINTING);
				}
				if (feverCheckBox.isSelected()) {
					symptoms.add(Symptoms.FEVER);

				}
				if (tirednessCheckBox.isSelected()) {
					symptoms.add(Symptoms.TIREDNESS);

				}
				if (difficultyBreathingCheckBox.isSelected()) {
					symptoms.add(Symptoms.DIFFICULTY_BREATHING);

				}
				if (backachesCheckBox.isSelected()) {
					symptoms.add(Symptoms.BACKHACHES);
				}

				Disease disease = new Disease(diseaseNameField.getText(), symptoms);
				Hospital.getInstance().addDisease(disease);

				Patient patient = new Patient(fNameField.getText(), lNameField.getText(), currentSubDepartment, disease);
				patient.setStatus(Integer.parseInt(statusField.getText()));

				Hospital.getInstance().addPatient(patient, currentSubDepartment);

				// table.setItems(currentSubDepartment.getPatientsObservableList());

				break;
			}
			case NURSES: {
				Nurse nurse = new Nurse(fNameField.getText(), lNameField.getText(), (Treatments) treatsComboBox.getValue(), currentSubDepartment);
				Hospital.getInstance().addNurse(nurse, currentSubDepartment);

				// table.setItems(currentSubDepartment.getNursesObservableList());

				break;

			}
			case DOCTORS: {
				Doctor doctor = new Doctor(fNameField.getText(), lNameField.getText(), (Specialization) specsComboBox.getValue(), currentSubDepartment);
				Hospital.getInstance().addDoctor(doctor, currentSubDepartment);

				// table.setItems(currentSubDepartment.getDoctorsObservableList());

				break;

			}
			case DISEASES: {
				HashSet<Symptoms> symptoms = new HashSet<>();
				Disease disease = new Disease(diseaseNameField.getText(), symptoms);
				Hospital.getInstance().addDisease(disease);

				ArrayList<Disease> array = new ArrayList<>(Hospital.getInstance().getDiseasesById().values());
				ObservableList<Disease> diseasesObservableList = FXCollections.observableArrayList(array);
				System.out.println(array);
				table.setItems(diseasesObservableList);
				break;

			}
			default:
				break;
		}
		fNameField.clear();
		lNameField.clear();
		addToggle.setSelected(false);
		SFx.slideHorizontally(slideOutPanel, false, 250);

	}

	@FXML
	public void addToggleHandler(ActionEvent event) {
		for (Node n : adderVbox.getChildren()) {
			if (n instanceof TextField) {
				((TextField) n).clear();
			}
		}
		if (addToggle.isSelected()) {
			SFx.slideHorizontally(slideOutPanel, false, 250);

		} else

		{
			SFx.slideHorizontally(slideOutPanel, true, 250);
		}
	}

	private void loadButtonBar() {

		for (Node n : buttonsHBox.getChildren()) {
			if (n instanceof Button) {
				n.setVisible(false);
				n.managedProperty().bind(n.visibleProperty());
				n.disableProperty().bind(Bindings.isEmpty(table.getSelectionModel().getSelectedItems()));
			}
		}

		switch (currentView) {
			case PATIENTS: {
				newStatusField.setVisible(false);
				checkPatientButton.setVisible(true);
				checkDiseaseButton.setVisible(true);
				removeButton.setVisible(true);
				removeRecoveredButton.setVisible(true);
				removeToHotelButton.setVisible(true);
				break;

			}
			case NURSES: {

				removeButton.setVisible(true);
				break;

			}
			case DOCTORS: {
				removeButton.setVisible(true);

				break;

			}
			case DISEASES: {

				removeButton.setVisible(true);

				break;

			}
			default:
				break;
		}
	}

	public static List<Field> getAllFields(List<Field> fields, Class<?> type) {
		if (type != ProgramUser.class) {
			fields.addAll(Arrays.asList(type.getDeclaredFields()));
		}

		if (type.getSuperclass() != null) {
			getAllFields(fields, type.getSuperclass());
		}

		return fields;
	}

	@FXML
	void checkPatientButtonHandler(ActionEvent event) {

		switch (user.getPrivilege()) {
			case DOCTOR: {

				Patient selectedPatient = (Patient) table.getSelectionModel().getSelectedItem();

				((Doctor) user).checkPatient(selectedPatient);

				break;
			}
			case NURSE: {
				Patient selectedPatient = (Patient) table.getSelectionModel().getSelectedItem();

				((Nurse) user).checkPatient(selectedPatient);

				break;
			}
			case ADMIN: {

				break;
			}
			default:
				throw new IllegalArgumentException("Unexpected value: " + user.getPrivilege());
		}

	}

	@FXML
	public void removeButtonHandler(ActionEvent event) {

		confirmationPopUpSwitch();
	}

	@FXML
	public void confirmRemoveButtonHandler(ActionEvent event) {

		switch (currentView) {
			case PATIENTS: {

				Patient selectedPatient = (Patient) table.getSelectionModel().getSelectedItem();
				System.out.println("Selected patient to remove " + table.getSelectionModel());

				if (Hospital.getInstance().removePatient(selectedPatient)) {
					System.out.println("REMOVED SUCCESFULLY");
				}

				break;

			}
			case NURSES: {
				Nurse selectedNurse = (Nurse) table.getSelectionModel().getSelectedItem();
				Hospital.getInstance().removeNurse(selectedNurse);
				table.setItems(currentSubDepartment.getNursesObservableList());

				break;

			}
			case DOCTORS: {
				Doctor selectedDoctor = (Doctor) table.getSelectionModel().getSelectedItem();
				Hospital.getInstance().removeDoctor(selectedDoctor);
				table.setItems(currentSubDepartment.getDoctorsObservableList());

				break;

			}
			case DISEASES: {
				Disease selectedDisease = (Disease) table.getSelectionModel().getSelectedItem();
				Hospital.getInstance().removeDisease(selectedDisease);

				ObservableList<Disease> diseasesObservableList = FXCollections.observableArrayList(Hospital.getInstance().getDiseasesById().values());
				table.setItems(diseasesObservableList);

				break;

			}
			default:
				break;
		}
		confirmationPopUpSwitch();

	}

	@FXML
	private void populateTable() {
		if (table.getColumns() != null) {
			table.getColumns().clear();
		}
		System.out.println(currentView);
		switch (currentView) {
			case PATIENTS: {
				System.out.println(currentSubDepartment.getPatients());
				TableColumn<Patient, Integer> idColumn = new TableColumn<Patient, Integer>("ID");
				TableColumn<Patient, String> firtNameColumn = new TableColumn<Patient, String>("First Name");
				TableColumn<Patient, String> lastNameColumn = new TableColumn<Patient, String>("Last Name");
				TableColumn<Patient, Integer> statusColumn = new TableColumn<Patient, Integer>("Status");
				TableColumn<Patient, Condition> conditionColumn = new TableColumn<Patient, Condition>("Condition");
				TableColumn<Patient, Disease> diseaseColumn = new TableColumn<Patient, Disease>("Disease");

				idColumn.setCellValueFactory(new PropertyValueFactory<Patient, Integer>("id"));
				firtNameColumn.setCellValueFactory(new PropertyValueFactory<Patient, String>("firstName"));
				lastNameColumn.setCellValueFactory(new PropertyValueFactory<Patient, String>("lastName"));
				statusColumn.setCellValueFactory(new PropertyValueFactory<Patient, Integer>("status"));
				conditionColumn.setCellValueFactory(new PropertyValueFactory<Patient, Condition>("condition"));
				diseaseColumn.setCellValueFactory(new PropertyValueFactory<Patient, Disease>("disease"));

				lastNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
				firtNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());

				statusColumn.setCellFactory(TextFieldTableCell.<Patient, Integer>forTableColumn(new IntegerStringConverter() {
					@Override
					public Integer fromString(String value) {
						try {
							return super.fromString(value);
						} catch (NumberFormatException e) {
							return null;
						}
					}
				}));

				statusColumn.setOnEditCommit(t -> {
					if (t.getNewValue() != null) {
						t.getRowValue().setStatus(t.getNewValue());
					} else {
						exceptionPopUp();
						t.getTableView().getColumns().get(0).setVisible(false);
						t.getTableView().getColumns().get(0).setVisible(true);
						System.err.println("NOTANINT");
					}

				});

				table.getColumns().add(idColumn);
				table.getColumns().add(firtNameColumn);
				table.getColumns().add(lastNameColumn);
				table.getColumns().add(statusColumn);
				table.getColumns().add(conditionColumn);
				table.getColumns().add(diseaseColumn);

				System.out.println("Patients list: " + currentSubDepartment.getPatients());
				System.out.println("Patients OBSERVABLE: " + currentSubDepartment.getPatientsObservableList());

				table.setItems(currentSubDepartment.getPatientsObservableList());

				break;

			}
			case NURSES: {
				System.out.println("Nurses case");

				TableColumn<Nurse, Number> idColumn = new TableColumn<Nurse, Number>("ID");
				TableColumn<Nurse, String> firtNameColumn = new TableColumn<Nurse, String>("First Name");
				TableColumn<Nurse, String> lastNameColumn = new TableColumn<Nurse, String>("Last Name");
				TableColumn<Nurse, Number> shiftCounterColumn = new TableColumn<Nurse, Number>("Shift Counter");

				idColumn.setCellValueFactory(new PropertyValueFactory<Nurse, Number>("id"));
				firtNameColumn.setCellValueFactory(new PropertyValueFactory<Nurse, String>("firstName"));
				lastNameColumn.setCellValueFactory(new PropertyValueFactory<Nurse, String>("lastName"));
				shiftCounterColumn.setCellValueFactory(new PropertyValueFactory<Nurse, Number>("shiftCounter"));

				table.getColumns().add(idColumn);
				table.getColumns().add(firtNameColumn);
				table.getColumns().add(lastNameColumn);
				table.getColumns().add(shiftCounterColumn);

				ObservableList nursesList = FXCollections.observableArrayList(currentSubDepartment.getNurses());

				table.setItems(nursesList);

				break;

			}
			case DOCTORS: {
				TableColumn<Doctor, Number> idColumn = new TableColumn<Doctor, Number>("ID");
				TableColumn<Doctor, String> firtNameColumn = new TableColumn<Doctor, String>("First Name");
				TableColumn<Doctor, String> lastNameColumn = new TableColumn<Doctor, String>("Last Name");
				TableColumn<Doctor, Number> shiftCounterColumn = new TableColumn<Doctor, Number>("Shift Counter");

				idColumn.setCellValueFactory(new PropertyValueFactory<Doctor, Number>("id"));
				firtNameColumn.setCellValueFactory(new PropertyValueFactory<Doctor, String>("firstName"));
				lastNameColumn.setCellValueFactory(new PropertyValueFactory<Doctor, String>("lastName"));
				shiftCounterColumn.setCellValueFactory(new PropertyValueFactory<Doctor, Number>("shiftCounter"));

				table.getColumns().add(idColumn);
				table.getColumns().add(firtNameColumn);
				table.getColumns().add(lastNameColumn);
				table.getColumns().add(shiftCounterColumn);

				ObservableList doctorsList = FXCollections.observableArrayList(currentSubDepartment.getDoctors());

				table.setItems(doctorsList);
				break;

			}
			case DISEASES: {
				TableColumn<Disease, Number> idColumn = new TableColumn<Disease, Number>("ID");
				TableColumn<Disease, String> diseaseNameColumn = new TableColumn<Disease, String>("Name");

				idColumn.setCellValueFactory(new PropertyValueFactory<Disease, Number>("id"));
				diseaseNameColumn.setCellValueFactory(new PropertyValueFactory<Disease, String>("diseaseName"));

				table.getColumns().add(idColumn);
				table.getColumns().add(diseaseNameColumn);

				ArrayList<Disease> array = new ArrayList<>(Hospital.getInstance().getDiseasesById().values());
				ObservableList<Disease> diseasesObservableList = FXCollections.observableArrayList(array);
				System.out.println(array);
				table.setItems(diseasesObservableList);

				break;

			}
			default:
				break;
		}

		for (int i = 1; i < table.getColumns().size(); i++) {

			((TableColumn) table.getColumns().get(i)).setPrefWidth(150);

		}
		/*
		 * this event filter checks where mouse click is coming from, clears table
		 * selection if click comes from outside the table or bottom button control
		 */
		mainNode.addEventFilter(MouseEvent.MOUSE_CLICKED, evt -> {
			Node source = evt.getPickResult().getIntersectedNode();

			/*
			 * move up through the node hierarchy until a TableRow or scene root is found
			 * exclude buttons container
			 */
			while (source != null && !(source instanceof TableRow) && !(source != buttonsHBox)) {
				source = source.getParent();
			}
			/*
			 * clear selection on click anywhere but on a filled row
			 */
			if (source == null || (source instanceof TableRow && ((TableRow) source).isEmpty())) {
				table.getSelectionModel().clearSelection();
			}

		});
		SFx.fadeInAndOut(table, table, 250);

	}

	@FXML
	public void cancelDeletionButtonHandler() {
		confirmationPopUpSwitch();
	}

	public void getPatientsByCondition(Condition condition) {
		ArrayList<Patient> list = new ArrayList<>();
		for (Patient p : currentSubDepartment.getPatients()) {
			if (p.getCondition().equals(condition)) {
				list.add(p);
			}
		}
		ObservableList<Patient> observableList = FXCollections.observableArrayList(list);
		table.setItems(observableList);
	}

	/*
	 *
	 *
	 *
	 *
	 */
	public void confirmationPopUpSwitch() {
		/*
		 *
		 */
		if (confirmationPopUpPane.getPrefHeight() == 0) {

			for (Node n : mainNode.getChildren()) {
				if (!n.equals(confirmationPopUpPane)) {
					n.setOpacity(0.6);
					n.setDisable(true);
				}

			}

			Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, new KeyValue(confirmationPopUpPane.prefHeightProperty(), confirmationPopUpPane.getHeight())),
					new KeyFrame(Duration.millis(250), new KeyValue(confirmationPopUpPane.prefHeightProperty(), 60)));
			timeline.play();
			TranslateTransition transition = new TranslateTransition();
			transition.setNode(confirmationPopUp);
			transition.setFromY(60);
			transition.setToY(0);
			transition.setDuration(Duration.millis(350));
			transition.play();
		} else {

			Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, new KeyValue(confirmationPopUpPane.prefHeightProperty(), confirmationPopUpPane.getHeight())),
					new KeyFrame(Duration.millis(250), new KeyValue(confirmationPopUpPane.prefHeightProperty(), 0)));
			timeline.play();
			TranslateTransition transition = new TranslateTransition();
			transition.setNode(confirmationPopUp);
			transition.setFromY(0);
			transition.setToY(60);
			transition.setDuration(Duration.millis(350));
			transition.play();
			for (Node n : mainNode.getChildren()) {
				if (!n.equals(confirmationPopUpPane)) {
					n.setOpacity(1);
					n.setDisable(false);
				}

			}

		}
	}

}
