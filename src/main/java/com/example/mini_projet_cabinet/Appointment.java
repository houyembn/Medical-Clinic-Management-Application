package com.example.mini_projet_cabinet;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

public class Appointment implements Initializable {

    @FXML
    private TableView<Appoint_class> tableappoint;

    @FXML
    private TableColumn coldel;

    @FXML
    private TableColumn<Appoint_class,String> coldays;

    @FXML
    private TableColumn<Appoint_class,String> colduser;

    @FXML
    private TableColumn<Appoint_class,String> colhours;

    @FXML
    private TableColumn<Appoint_class,Integer> colidapp;

    @FXML
    private TableColumn<Appoint_class,String> colpuser;

    @FXML
    private TableColumn<Appoint_class,String> colseld;

    @FXML
    private TableColumn<Appoint_class,String> colselh;

    @FXML
    private TextField appid;

    @FXML
    private ComboBox<String> patientuser;

    @FXML
    private ComboBox<String> doctoruser;

    @FXML
    private DatePicker selctdays;

    @FXML
    private DatePicker workdays;

    @FXML
    private TextField workhours;

    @FXML
    private TextField selhours;

    @FXML
    private Button submit;

    @FXML
    private Button restbtn;

    @FXML
    private Button updateB;


    Connection con=MaConnexion.connecter();
    PreparedStatement pst;
    ObservableList<Appoint_class> data = FXCollections.observableArrayList();
    Appoint_class del ;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateTable();
        getPatient();
        getDoctor();
    }

    @FXML
    void reset (ActionEvent event) {
        selhours.setText("");
        selctdays.setValue(null);
        patientuser.setValue(null);
        workdays.setValue(null);
        doctoruser.setValue(null);
        workhours.setText("");
        appid.setText("");
    }

    private void updateTable(){
        table();
        coldays.setCellValueFactory(new PropertyValueFactory<Appoint_class, String>("workdays"));
        colseld.setCellValueFactory(new PropertyValueFactory<Appoint_class, String>("seldays"));
        colpuser.setCellValueFactory(new PropertyValueFactory<Appoint_class, String>("patientuser"));
        colidapp.setCellValueFactory(new PropertyValueFactory<Appoint_class, Integer>("app_id"));
        colselh.setCellValueFactory(new PropertyValueFactory<Appoint_class, String>("selhours"));
        colduser.setCellValueFactory(new PropertyValueFactory<Appoint_class, String>("doctoruser"));
        colhours.setCellValueFactory(new PropertyValueFactory<Appoint_class, String>("workhours"));



//   **** Button delete :

        Callback<TableColumn<Appoint_class, String>, TableCell<Appoint_class, String>> cellFactory=(Param) ->{



            final TableCell<Appoint_class, String> cell= new TableCell<Appoint_class, String>(){

                @Override
                public void updateItem(String  item, boolean empty){
                    super.updateItem(item,empty);

                    if(empty){
                        setGraphic(null);
                        setText(null);
                    }else{
                        //now create the action button
                        final Button editButton = new Button("Delete");

                        editButton.setStyle(
                                "-fx-background-color: #3c72a2;"
                        );

                        editButton.setOnMouseClicked((MouseEvent event) ->{
                            Appoint_class selec = tableappoint.getSelectionModel().getSelectedItem();
                            if(selec == null){
                                Alert alert = new Alert(Alert.AlertType.WARNING);
                                alert.setTitle("Warning alert");

                                // Header Text: null
                                alert.setHeaderText(null);
                                alert.setContentText("Please select column!");

                                alert.showAndWait();

                            }else {
                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                alert.setTitle("Confirmation Dialog");
                                alert.setHeaderText(null);
                                alert.setContentText("Are you sure to delete ?");
                                Optional<ButtonType> action = alert.showAndWait();
                                if (action.get() == ButtonType.OK) {

                                    try {
                                        del = tableappoint.getSelectionModel().getSelectedItem();
                                        pst = con.prepareStatement("DELETE FROM `appointment` WHERE id_App=?");
                                        pst.setInt(1, del.getApp_id());
                                        pst.executeUpdate();
                                        updateTable();

                                    } catch (SQLException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                            }
                        });

                        HBox managebtn = new HBox(editButton);
                        managebtn.setStyle("-fx-alignment:center");
                        setGraphic(managebtn);
                        setText(null);
                    }
                };
            };
            //return the cell created
            return cell;
        };
        //set the factory to action column
        coldel.setCellFactory(cellFactory);


        //tableappoint.getItems().clear();
        tableappoint.setItems(data);
    }

    private void table(){
        try {
            data.clear();
            Connection con = MaConnexion.connecter();
            ResultSet rs = con.createStatement().executeQuery("select id_App,Doctor_username,Patient_username,app_day,app_hours,work_days,work_hours from appointment ");
            while (rs.next()){
                data.add(new Appoint_class(rs.getInt("id_App"),rs.getString("Doctor_username"),rs.getString("patient_username"),rs.getString("app_day"),rs.getString("app_hours"),rs.getString("work_days"),rs.getString("work_hours")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void getItems(MouseEvent event) {
        Integer index;
        index = tableappoint.getSelectionModel().getSelectedIndex();
        workdays.setValue(LocalDate.parse((coldays.getCellData(index))));
        appid.setText(colidapp.getCellData(index).toString());
        selctdays.setValue(LocalDate.parse((colseld.getCellData(index))));
        selhours.setText(colselh.getCellData(index));
        doctoruser.setValue(colduser.getCellData(index));
        patientuser.setValue(colpuser.getCellData(index));
        workhours.setText(colhours.getCellData(index));
    }

    private  void getPatient() {
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

    private  void getDoctor() {
        try {

            pst=con.prepareStatement("select username from doctor");
            ResultSet result = pst.executeQuery();
            ObservableList data = FXCollections.observableArrayList();
            while (result.next()){
                data.add(new String(result.getString(1)));
            }
            doctoruser.setItems(data);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
@FXML
    private void getValue(ActionEvent event){
        try {
            String user = doctoruser.getValue();
            System.out.println(user);
            pst = con.prepareStatement("select working_days,working_hours from doctor where username = ?");
            pst.setString(1, user);
            ResultSet result = pst.executeQuery();

            if (result.next() == true) {
                workdays.setValue(LocalDate.parse(result.getString(1)));
                workhours.setText(result.getString(2));
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @FXML
    void save (ActionEvent event) {

        if ((selhours.getText()=="" )|| selctdays.getValue()== null || workhours.getText()=="" || appid.getText()=="" || patientuser.getValue()== null|| workdays.getValue()== null || doctoruser.getValue()== null){
            System.out.println("Not Addedddd!");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Message");
            alert.setHeaderText("Erreur!!");
            alert.setContentText("One or more fields are empty!");
            alert.showAndWait();
        }else {
            try {
                pst=con.prepareStatement("insert into appointment(id_App,Doctor_username,Patient_username,app_day,app_hours,work_days,work_hours)values(?,?,?,?,?,?,?)");
                pst.setString(1, appid.getText());
                pst.setString(2, doctoruser.getValue());
                pst.setString(3, patientuser.getValue());
                pst.setString(4, selctdays.getValue().toString());
                pst.setString(5, selhours.getText());
                pst.setString(6, workdays.getValue().toString());
                pst.setString(7, workhours.getText());
                pst.executeUpdate();

                System.out.println("Inscription réussite");

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Message");
                alert.setHeaderText(null);
                alert.setContentText("Record Addedddd!");
                alert.showAndWait();
                updateTable();

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

    public void update(ActionEvent event){
        if ((selhours.getText()=="" )|| selctdays.getValue()== null || workhours.getText()=="" || appid.getText()=="" || patientuser.getValue()== null|| workdays.getValue()== null || doctoruser.getValue()== null){
            System.out.println("Not Addedddd!");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Message");
            alert.setHeaderText("Erreur!!");
            alert.setContentText("One or more fields are empty!");
            alert.showAndWait();

        }else {
            try {
                pst = con.prepareStatement("update appointment set id_App = ?,Patient_username = ?,app_day = ?,app_hours = ?,work_days = ?,work_hours = ? where Doctor_username = ?");
                pst.setString(1, appid.getText());
                pst.setString(2, patientuser.getValue());
                pst.setString(3, selctdays.getValue().toString());
                pst.setString(4, selhours.getText());
                pst.setString(5, workdays.getValue().toString());
                pst.setString(6, workhours.getText());
                pst.setString(7, doctoruser.getValue());
                pst.executeUpdate();

                System.out.println("Record update!!");

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Message");
                alert.setHeaderText(null);
                alert.setContentText("Record Updatttee!!!");
                alert.showAndWait();
                updateTable();

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
}
