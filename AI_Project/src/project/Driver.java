package project;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

public class Driver extends Application {

	private double paneWidth = 700;
	private double paneHeight = 700;

	@Override // Override the start method in the Application class
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("Layout.fxml"));
			Scene scene = new Scene(root, paneWidth, paneHeight);
			primaryStage.setScene(scene); // Place the scene in the stage
			primaryStage.show(); // Display the stage

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String args[]) {

		launch(args);
	}
}