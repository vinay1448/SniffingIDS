package com.SniffID.SniffsIDS.controller;

import com.SniffID.SniffsIDS.JNetWrapper;
import com.SniffID.SniffsIDS.controller.SniffIDEntry;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.WindowEvent;
import org.jnetpcap.PcapAddr;
import org.jnetpcap.PcapClosedException;
import org.jnetpcap.PcapIf;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    VBox networkDevBox;

    @FXML
    Button continueBtn;

    ToggleGroup toggleGroup=new ToggleGroup();
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        List<PcapIf> allDevs= JNetWrapper.getAlldevs();
        toggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                continueBtn.setDisable(false);
            }
        });
        int i=0;
        for(PcapIf dev : allDevs){
            List<PcapAddr> addresses=dev.getAddresses();
            String additional="";
            if(addresses.size()>0)
                additional=addresses.get(0).getAddr().toString();
            RadioButton radioButton=new RadioButton("  "+dev.getDescription()+"    "+additional);
            radioButton.setId("NetRB_"+i);
            radioButton.setFont(new Font(13));
            radioButton.setToggleGroup(toggleGroup);
         
            networkDevBox.getChildren().add(radioButton);
            i++;
        }

    }

    @FXML
    public void continueToNext(){
        RadioButton selRB=(RadioButton)toggleGroup.getSelectedToggle();
        JNetWrapper.selectDevice(Integer.parseInt(selRB.getId().replace("NetRB_","")));
        SniffIDEntry.selectedDevString=selRB.getText();
      
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/SniffID/SniffsIDS/views/capture_window.fxml"));
        try {
            Parent root = fxmlLoader.load();
            SniffIDEntry.captureController=fxmlLoader.getController();
            SniffIDEntry.mainStage.setScene(new Scene(root));
            SniffIDEntry.mainStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    try {
                        JNetWrapper.stopCapturing();
                    } catch (PcapClosedException e) {
                        e.printStackTrace();
                    }
                    System.exit(0);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
