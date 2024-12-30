package com.SniffID.SniffsIDS.controller;

import com.SniffID.SniffsIDS.JNetWrapper;
import com.SniffID.SniffsIDS.controller.SniffIDEntry;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import org.jnetpcap.PcapClosedException;

import java.net.URL;
import java.util.ResourceBundle;

public class CaptureController implements Initializable {

    @FXML
    Button captureBtn;

    @FXML
    Label console, selDevLabel;

    @FXML
    CheckBox cbDumpCAP,cbSavePacketInfo,cbDetectSSLStripping;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        idleMode();
        selDevLabel.setText("("+ SniffIDEntry.selectedDevString+"  )");
    }

    public void captureMode(){
        captureBtn.setText("Stop Capturing");
        captureBtn.setStyle("-fx-background-color: red");
        captureBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stopCapturing();
            }
        });

        cbDumpCAP.setDisable(true);
        cbSavePacketInfo.setDisable(true);
        cbDetectSSLStripping.setDisable(true);
    }

    public void idleMode(){
        captureBtn.setText("Start Capturing");
        captureBtn.setStyle("-fx-background-color: teal");
        captureBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                startCapturing();
            }
        });

        cbDumpCAP.setDisable(false);
        cbSavePacketInfo.setDisable(false);
        cbDetectSSLStripping.setDisable(false);
    }

    @FXML
    public void startCapturing(){

        captureMode();
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        JNetWrapper.startCapturing(new JNetWrapper.OnPacketCaptured() {
            @Override
            public void onCapture(String info, String data) {
                writeToConsole(info);
                writeToConsole(data);
            }
        },cbDumpCAP.isSelected(),cbSavePacketInfo.isSelected(),cbDetectSSLStripping.isSelected());
    }

    public void stopCapturing() {
        try {
            JNetWrapper.stopCapturing();
        } catch (PcapClosedException e) {
            e.printStackTrace();
        }
        idleMode();
    }

    public void writeToConsole(String s){
        console.setText(console.getText()+"\n"+s);
    }
}
