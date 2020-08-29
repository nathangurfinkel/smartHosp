package View;

import java.io.IOException;

import Model.ProgramUser;
import Model.SubDepartment;
import Utils.UserPrivilege;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class MainViewController {
	@FXML
	private BorderPane root;
	@FXML
	private Label userLabel1, mainLabel, userLabel2;

	@FXML
	private ToggleButton viewAndManageToggle, darkToggle;
	@FXML
	private VBox menuBar, manageEntriesVBox;

	@FXML
	private Pane manageEntriesPane;
	@FXML
	private Button queriesMenuButton, patientsMenuButton, nursesMenuButton, diseasesMenuButton, dashboardMenuButton;
	@FXML
	private ImageView userPic;

	private ProgramUser user;

	private SubDepartment currentSubDepartment;

	public void setCurrentSubDepartment(SubDepartment currentSubDepartment) {
		this.currentSubDepartment = currentSubDepartment;
	}

	public SubDepartment getCurrentSubDepartment() {
		return currentSubDepartment;
	}

	public ProgramUser getUser() {
		return user;

	}

	public void setUser(ProgramUser user) {
		this.user = user;
	}

	@FXML
	public UserPrivilege getUserPrivilage() {
		return user.getPrivilege();
	}

	@FXML
	public void exitApplication(ActionEvent event) {
		Platform.exit();
	}

	@FXML
	void darkMode(MouseEvent event) {

	}

	@FXML
	void dashboardMenuButtonHandler(ActionEvent event) {

		mainNodeLoader(CurrentView.DASHBOARD);

	}

	@FXML
	void queriesMenuButtonHandler(ActionEvent event) {
		mainNodeLoader(CurrentView.QUERIES);

	}

	@FXML
	void patientsButtonHandler(ActionEvent event) {
		mainNodeLoader(CurrentView.PATIENTS);
	}

	@FXML

	void nursesButtonHandler(ActionEvent event) {

		mainNodeLoader(CurrentView.NURSES);
	}

	@FXML
	void doctorsButtonHandler(ActionEvent event) {

		mainNodeLoader(CurrentView.DOCTORS);

	}

	@FXML
	void diseasesButtonHandler(ActionEvent event) {
		mainNodeLoader(CurrentView.DISEASES);

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
	public void initialize() {

		SFx.fadeNode(root, true, 500);
		System.out.println("initialize MainViewController invoked");
		Rectangle rect = new Rectangle(220, 120);
		manageEntriesPane.setClip(rect);
		userLabel1.setText(user.getFirstName() + " " + user.getLastName());
		if (user.getPrivilege().equals(UserPrivilege.ADMIN)) {
			userLabel2.setText(user.getPrivilege() + " of this Hospital");
		} else {
			userLabel2.setText(user.getPrivilege().toString() + " in subdepartment " + currentSubDepartment.getId());
		}
	}

	@FXML
	private void viewAndManageToggleHandler() {
		if (viewAndManageToggle.isSelected()) {
			TranslateTransition transition = new TranslateTransition();
			transition.setNode(manageEntriesVBox);
			transition.setFromY(-121);
			transition.setToY(0);
			transition.setDuration(Duration.millis(250));
			transition.play();
		} else {
			TranslateTransition transition = new TranslateTransition();
			transition.setNode(manageEntriesVBox);
			transition.setFromY(0);
			transition.setToY(-121);
			transition.setDuration(Duration.millis(250));
			transition.play();
		}
	}

	public void mainNodeLoader(CurrentView currentView) {
		FadeTransition fadeOut = new FadeTransition();
		fadeOut.setDuration(Duration.millis(250));
		fadeOut.setNode(root.getCenter());
		fadeOut.setFromValue(1);
		fadeOut.setToValue(0);
		fadeOut.setOnFinished(event -> {
			try {
				switch (currentView) {

					case DASHBOARD: {
						FXMLLoader loader = new FXMLLoader(getClass().getResource("DashboardView.fxml"));
						DashboardViewController controller = new DashboardViewController();

						controller.setUser(user);

						if (!user.getPrivilege().equals(UserPrivilege.ADMIN)) {
							controller.setCurrentSubDepartment(user.getSubDepartment());
						}

						loader.setController(controller);
						// Node nowLoadingNode = loader.load();
						// nowLoadingNode.setOpacity(0);
						root.setCenter(loader.load());
						break;
					}
					case QUERIES: {
						FXMLLoader loader = new FXMLLoader(getClass().getResource("QueriesView.fxml"));
						QueriesViewController controller = new QueriesViewController();
						controller.setUser(user);
						if (!user.getPrivilege().equals(UserPrivilege.ADMIN)) {
							controller.setCurrentSubDepartment(user.getSubDepartment());
						} // Node nowLoadingNode = loader.load();
							// nowLoadingNode.setOpacity(0);
						root.setCenter(loader.load());
						break;
					}

					default:
						manageEntriesLoader(currentView);
						break;
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		fadeOut.play();
	}

	public void manageEntriesLoader(CurrentView currentView) throws IOException {

		FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewAndManageView.fxml"));
		ViewAndManageViewController controller = new ViewAndManageViewController();
		controller.setUser(user);

		if (!user.getPrivilege().equals(UserPrivilege.ADMIN)) {
			System.out.println("NOT ADMIN");
			controller.setCurrentSubDepartment(user.getSubDepartment());
		}

		controller.setCurrentView(currentView);
		loader.setController(controller);
		root.setCenter(loader.load());

	}
}
