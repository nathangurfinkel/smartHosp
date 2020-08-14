package View;

import Model.Hospital;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Start extends Application {
	private static Hospital hospital;

	public static void main(String[] args) {

		System.out.println(Hospital.getInstance().getPatientsById());
		launch(args);
	}

	@Override
	public void start(Stage loginStage) throws Exception {
		try {
			Parent root = FXMLLoader.load(this.getClass().getResource("LoginPage.fxml"));
			Stage stage = new Stage();
			stage.setTitle("welcome to smartHosp");
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}