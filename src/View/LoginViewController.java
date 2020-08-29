package View;

import java.io.IOException;

import Exceptions.IncorrectPasswordException;
import Exceptions.NoSuchUserException;
import Model.Hospital;
import Model.ProgramUser;
import Utils.UserPrivilege;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class LoginViewController {
	@FXML
	private BorderPane mainroot;
	@FXML
	private AnchorPane loginView;

	@FXML
	private Button loginButton;

	@FXML
	private PasswordField passwordField;

	@FXML
	private TextField userNameField;

	@FXML
	private AnchorPane logoPane;

	@FXML
	private HBox forgotHBox;

	@FXML
	private Button forgotPasswordButton;

	@FXML
	void handleLoginButton(ActionEvent event) throws IOException {
		System.out.println(Hospital.getInstance().getProgramUsersByLogin());
		try {
			String inputLogin = userNameField.getText();
			String inputPassword = passwordField.getText();

			if (userNameField.getText().isBlank() || passwordField.getText().isBlank()) {
				event.consume();
			}

			else if (!Hospital.getInstance().getProgramUsersByLogin().containsKey(inputLogin)) {
				SFx.fadeNode(forgotHBox, true, 250);

				throw new NoSuchUserException();
			} else {
				if (verifyPassword(inputLogin, inputPassword) == true) {

					ProgramUser user = Hospital.getInstance().getProgramUsersByLogin().get(inputLogin);
					loadMainPage(user);

				} else {
					SFx.fadeNode(forgotHBox, true, 250);

					throw new IncorrectPasswordException();
				}
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
			;
		}
	}

	private void loadMainPage(ProgramUser user) {
		try {
			System.out.println("loadMainPage invoked");

			SFx.fadeNode(loginView, false, 500);
			Parent mainPageParent;
			FXMLLoader loader = new FXMLLoader(getClass().getResource("MainView.fxml"));

			MainViewController mainViewController = new MainViewController();

			mainViewController.setUser(user);
			if (!user.getPrivilege().equals(UserPrivilege.ADMIN)) {
				mainViewController.setCurrentSubDepartment(user.getSubDepartment());
			}
			loader.setController(mainViewController);
			mainPageParent = loader.load();

			Scene mainScene = new Scene(mainPageParent);
			Stage window = new Stage();

			window.setScene(mainScene);
			window.show();

			Stage stageToClose = (Stage) loginButton.getScene().getWindow();
			stageToClose.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void initialize() {

		forgotHBox.setOpacity(0);
		SFx.fadeNode(loginView, true, 1000);
	}

	private boolean verifyPassword(String login, String password) {

		if (Hospital.getInstance().getProgramUsersByLogin().containsKey(login)) {
			if (Hospital.getInstance().getProgramUsersByLogin().get(login).getPassword().equals(password)) {
				return true;
			}
		}
		return false;

	}
}
