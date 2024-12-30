package com.SniffID.SniffsIDS;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;

public class App extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		try {
			
			Scene scenes = new Scene(new AnchorPane());	
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/SniffID/SniffsIDS/views/SniffIDFacilityFinal.fxml"));
			scenes.setRoot(loader.load());
			primaryStage.setScene(scenes);
			primaryStage.show();
	
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	
	public static void main(String[] args) {
		launch(args);
	}
}
