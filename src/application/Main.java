package application;
	
/**
 * Start here
 */
import javafx.application.Application;


import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	
	
	@Override
	public void start(Stage primaryStage) {
		// instatiate contoller as an object
		ServerHomeController sc = new ServerHomeController(primaryStage);
		// pass sc in as a scene parameter, since it extends borderpane
		Scene scene = new Scene(sc, 600, 500);
		// set the scene, and display it
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	public static void main(String[] args) {
		launch(args);
	}
}
