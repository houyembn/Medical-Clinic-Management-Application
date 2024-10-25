package com.example.mini_projet_cabinet;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

public class medicament {

    @FXML
    private Button fermerbtn;

    @FXML
    private Button impr;

    @FXML
    private Label labelstatus;
    @FXML
    private TextArea discrip;

    @FXML
    void exist(ActionEvent event) {
        Stage stage = new Stage();
        Scene scene = null;
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setResizable(false);
        stage.setScene(scene);
        stage.close();
    }


    @FXML
    void imprimie(ActionEvent event) {
        final FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showSaveDialog(new Stage());
        if (file != null) {
            saveSystem(file, discrip.getText());
        }
    }
    public void saveSystem(File file, String content){
        try{
            PrintWriter printWriter = new PrintWriter(file);
            printWriter.write(content);
            printWriter.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
