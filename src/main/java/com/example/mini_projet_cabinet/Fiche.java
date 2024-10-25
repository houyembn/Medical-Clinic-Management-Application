package com.example.mini_projet_cabinet;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.w3c.dom.Document;

import java.io.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;

public class Fiche implements Initializable{

    @FXML
    private ComboBox<String> cbp;

    @FXML
    private TextField disease;

    @FXML
    private Button fermerbtn;

    @FXML
    private TextField first;

    @FXML
    private TextField last;

    @FXML
    private TextField patrid;

    @FXML
    private TextArea discrip;

    @FXML
    private Button btnsave;

    @FXML
    private TextField symp;

    @FXML
    private TextField message;

    @FXML
    private Label calcul;

    @FXML
    private ComboBox<String> patientuser;

    Connection con=MaConnexion.connecter();
    PreparedStatement pst;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getPatient();
        //revenir en ligne
        discrip.setWrapText(true);
    }

    @FXML
    void count (KeyEvent event){
        ObservableList<CharSequence> list = discrip.getParagraphs();
        int par = list.size();
        String[] words = discrip.getText().split("\\s+");
        calcul.setText("Paragraph: "+ par +" | Words:"+ words.length+ " |Characters:"+ discrip.getLength() );
    }

    @FXML
    void imprimie(ActionEvent event) {

        //MessageFormat header = new MessageFormat(("Details Doctor:"));
        //MessageFormat footer = new MessageFormat("Page{0,number,integer}");
    }


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
    private void getPatient() {
        try {
            pst=con.prepareStatement("select username from patient");
            ResultSet result = pst.executeQuery();
            ObservableList data = FXCollections.observableArrayList();

            while (result.next()){
                data.add(new String(result.getString(1)));
            }
            patientuser.setItems(data);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @FXML
    void medicament(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("medicament.fxml"));
        Stage stage = new Stage();
        Scene scene = null;
        //stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void getValue(ActionEvent event){
        try {
            String user = patientuser.getValue();
            System.out.println(user);
            pst = con.prepareStatement("select Patient_id,firstname,lastname,blood_group,disease,symptom from patient where username = ?");
            pst.setString(1, user);
            ResultSet result = pst.executeQuery();

            if (result.next() == true) {
                cbp.setValue(result.getString(4));
                patrid.setText(result.getString(1));
                first.setText(result.getString(2));
                last.setText(result.getString(3));
                disease.setText(result.getString(5));
                symp.setText(result.getString(6));
            }

            String userdoc = UserState.usertype;
            pst = con.prepareStatement("select description from fiche where userpatient = ? and userdoctor = ?");
            pst.setString(1, patientuser.getValue());
            pst.setString(2, userdoc);
            ResultSet result1 = pst.executeQuery();

            if (result1.next() == true) {
                discrip.setText(result1.getString(1));
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @FXML
    void updateAction(ActionEvent event) {
        if (patrid.getText() == "" || first.getText() == "" || last.getText() == "" || patientuser.getValue() == null || disease.getText() == "" || symp.getText() == "" || cbp.getValue() == null || discrip.getText() == "") {
            System.out.println("Not Addedddd!");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Message");
            alert.setHeaderText("Erreur!!");
            alert.setContentText("One or more fields are empty!");
            alert.showAndWait();
        } else {
            try {
                String userdoc = UserState.usertype;
                pst = con.prepareStatement("update fiche set description = ? where userpatient = ? and userdoctor = ?");
               // pst.setString(1, patrid.getText());
                pst.setString(1, discrip.getText());
                pst.setString(2, patientuser.getValue());
                pst.setString(3, userdoc);
                pst.executeUpdate();

                System.out.println("Record update!!");

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Message");
                alert.setHeaderText(null);
                alert.setContentText("Record Updatttee!!!");
                alert.showAndWait();

            } catch (SQLException e) {
                System.out.println("Not Updatttee!!!");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Message");
                alert.setHeaderText("Erreur!!");
                alert.setContentText("Not Updatttee!!!");
                alert.showAndWait();
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    void saveAction(ActionEvent event) {
        if (patrid.getText() == "" || first.getText() == "" || last.getText() == "" || patientuser.getValue() == null || disease.getText() == "" || symp.getText() == "" || cbp.getValue() == null || discrip.getText() == "") {
            System.out.println("Not Addedddd!");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Message");
            alert.setHeaderText("Erreur!!");
            alert.setContentText("One or more fields are empty!");
            alert.showAndWait();
        } else{
            try {
                String userdoc = UserState.usertype;
                pst = con.prepareStatement("insert into fiche (id,description,userpatient,userdoctor) values(?,?,?,?)");
                pst.setString(1, patrid.getText());
                pst.setString(2, discrip.getText());
                pst.setString(3, patientuser.getValue());
                pst.setString(4, userdoc);
                pst.executeUpdate();

                System.out.println("Inscription réussite");

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Message");
                alert.setHeaderText(null);
                alert.setContentText("Record Addedddd!");
                alert.showAndWait();
            } catch (SQLException e) {
                System.out.println("Not Addedddd!");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Message");
                alert.setHeaderText("Erreur!!");
                alert.setContentText("Not Addedddd!");
                alert.showAndWait();
                throw new RuntimeException(e);
            }
        }
    }
}
