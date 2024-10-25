package com.example.mini_projet_cabinet;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class Menu implements Initializable {
    @FXML
    private StackPane content;

    @FXML
    private Label labelstatus;

    @FXML
    private Button doctorBtn;

    @FXML
    private Button patientBtn;

    @FXML
    private Button appBtn;

    @FXML
    private Button appBtn1;

    @FXML
    private BorderPane pn1status;

    @FXML
    private AnchorPane pn2status;

    @FXML
    void exist (ActionEvent event) {
        Stage stage = new Stage();
        Scene scene = null;
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setResizable(false);
        stage.setScene(scene);
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        if(UserState.usertype.equals("admin")){
            appBtn1.setDisable(true);
            try {
                Parent fxml = FXMLLoader.load(getClass().getResource("doctor.fxml"));
                content.getChildren().removeAll();
                content.getChildren().setAll(fxml);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }else {
            doctorBtn.setDisable(true);
            appBtn.setDisable(true);
            patientBtn.setDisable(true);
            try {
                Parent fxml = FXMLLoader.load(getClass().getResource("checkApp.fxml"));
                content.getChildren().removeAll();
                content.getChildren().setAll(fxml);
                labelstatus.setText("/menu/checkApp");
                labelstatus.setText("Check Appoint");
            }catch (IOException ex){
                throw new RuntimeException(ex);
            }
        }
    }

    @FXML
    public void logout(javafx.event.ActionEvent actionEvent){
        UserState.setusertypeAdmin();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("loginuser.fxml"));
        Stage stage = new Stage();
        Scene scene = null;
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.setResizable(false);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    int seconds;
    @FXML
    private void doctor(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("doctor.fxml"));
        Scene scene = doctorBtn.getScene();
        root.translateZProperty().set(scene.getHeight());
        labelstatus.setText("/menu/Doctor");
        labelstatus.setText("Doctor Card");
        //"@../../../image/doc.PNG"
        //ImageView("Details Doctor");
        content.getChildren().add(root);
        content.getChildren().removeAll();
        content.getChildren().setAll(root);
        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(root.translateZProperty(),0, Interpolator.EASE_IN);
        KeyFrame Kf = new KeyFrame(Duration.seconds(1),kv);
        timeline.getKeyFrames().add(Kf);
        timeline.play();
    }

    @FXML
    private void pateint(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("patient.fxml"));
        Scene scene = patientBtn.getScene();
        root.translateYProperty().set(scene.getHeight());
        labelstatus.setText("/menu/Patient");
        labelstatus.setText("Patient Card");
        content.getChildren().add(root);
        content.getChildren().removeAll();
        content.getChildren().setAll(root);
        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(root.translateYProperty(),0, Interpolator.EASE_IN);
        KeyFrame Kf = new KeyFrame(Duration.seconds(1),kv);
        timeline.getKeyFrames().add(Kf);
        timeline.play();
    }

    @FXML
    private void appointment(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("appointment.fxml"));
        Scene scene = appBtn.getScene();
        root.translateXProperty().set(scene.getHeight());
        labelstatus.setText("/menu/Appointment");
        labelstatus.setText("Appointment");
        content.getChildren().add(root);
        content.getChildren().removeAll();
        content.getChildren().setAll(root);
        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(root.translateXProperty(),0, Interpolator.EASE_IN);
        KeyFrame Kf = new KeyFrame(Duration.seconds(1),kv);
        timeline.getKeyFrames().add(Kf);
        timeline.play();
    }

    @FXML
    private void check(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("checkApp.fxml"));
        Scene scene = doctorBtn.getScene();
        root.translateZProperty().set(scene.getHeight());
        labelstatus.setText("/menu/checkApp");
        labelstatus.setText("Check App");
        //"@../../../image/doc.PNG"
        //ImageView("Details Doctor");
        content.getChildren().add(root);
        content.getChildren().removeAll();
        content.getChildren().setAll(root);
        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(root.translateZProperty(),0, Interpolator.EASE_IN);
        KeyFrame Kf = new KeyFrame(Duration.seconds(1),kv);
        timeline.getKeyFrames().add(Kf);
        timeline.play();
    }

 /* public void doctor(javafx.event.ActionEvent actionEvent) throws IOException{
        Parent fxml = FXMLLoader.load(getClass().getResource("doctor.fxml"));
        content.getChildren().removeAll();
        content.getChildren().setAll(fxml);
    }

    public void pateint(javafx.event.ActionEvent actionEvent) throws IOException{
        Parent fxml = FXMLLoader.load(getClass().getResource("patient.fxml"));
        content.getChildren().removeAll();
        content.getChildren().setAll(fxml);
    }

    public void appointment(javafx.event.ActionEvent actionEvent) throws IOException{
        Parent fxml = FXMLLoader.load(getClass().getResource("appointment.fxml"));
        content.getChildren().removeAll();
        content.getChildren().setAll(fxml);
    }*/
}

