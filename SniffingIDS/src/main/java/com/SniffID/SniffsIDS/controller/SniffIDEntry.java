package com.SniffID.SniffsIDS.controller;

import java.io.IOException;

import com.SniffID.SniffsIDS.JNetWrapper;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class SniffIDEntry {
	
    	 MainController mainController;
    	    public static CaptureController captureController;
    	    public static String selectedDevString="";
    	    public static Stage mainStage;
    	    Stage primaryStage = new Stage();
    
    	    public void handlePacketSniffingAction(ActionEvent event) throws IOException {
    	        try {
    	            JNetWrapper.init(); // Ensure the library loads successfully here
    	            mainStage = primaryStage;
    	            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/SniffID/SniffsIDS/views/main_window.fxml"));
    	            Parent root = fxmlLoader.load();
    	            mainController = fxmlLoader.getController();
    	            mainStage.setTitle("IDS");
    	            mainStage.setScene(new Scene(root));
    	            mainStage.show();
    	        } catch (Exception e) {
    	            e.printStackTrace();
    	            showErrorDialog("Error Initializing Packet Sniffing", e.getMessage());
    	        }
    	    }

    	    private void showErrorDialog(String title, String message) {
    	        Alert alert = new Alert(Alert.AlertType.ERROR);
    	        alert.setTitle(title);
    	        alert.setHeaderText(null);
    	        alert.setContentText(message);
    	        alert.showAndWait();
    	    }

}