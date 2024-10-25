package com.example.mini_projet_cabinet;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class PatientDetails implements Initializable {

    @FXML
    private TableColumn<DetPat_class,String> coladd;

    @FXML
    private TableColumn<DetPat_class,String> colblood;

    @FXML
    private TableColumn<DetPat_class,Integer> colcin;

    @FXML
    private TableColumn<DetPat_class,String> coldate;

    @FXML
    private TableColumn<DetPat_class,String> coldies;

    @FXML
    private TableColumn<DetPat_class,String> colfirst;

    @FXML
    private TableColumn<DetPat_class,String> colgendre;

    @FXML
    private TableColumn<DetPat_class,Integer> colid;

    @FXML
    private TableColumn<DetPat_class,String> collast;

    @FXML
    private TableColumn<DetPat_class,Integer> colphone;

    @FXML
    private TableColumn<DetPat_class,String> colsymp;

    @FXML
    private TableColumn<DetPat_class,String> coluser;

    @FXML
    private TableView<DetPat_class> tablepatient1;

    ObservableList<DetPat_class> data = FXCollections.observableArrayList();

    @FXML
    void exist(ActionEvent event) {
        Stage stage = new Stage();
        Scene scene = null;
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setResizable(false);
        stage.setScene(scene);
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateTable();
    }

    private void table(){
        try {
            data.clear();
            Connection con = MaConnexion.connecter();
            ResultSet rs = con.createStatement().executeQuery("select username,Patient_id,firstname,lastname,disease,symptom,blood_group,gendre,cin,phone_no,address,date_birth from patient ");
            while (rs.next()){
                data.add(new DetPat_class(rs.getString("username"),Integer.parseInt(rs.getString("Patient_id")),rs.getString("firstname"),rs.getString("lastname"),rs.getString("disease"),rs.getString("symptom"),rs.getString("blood_group"),rs.getString("Gendre"),rs.getInt("CIN"),rs.getInt("Phone_NO"),rs.getString("Address"),rs.getString("date_birth")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void updateTable(){
        table();
        colid.setCellValueFactory(new PropertyValueFactory<DetPat_class,Integer>("patient_id"));
        colfirst.setCellValueFactory(new PropertyValueFactory<DetPat_class,String>("firstname"));
        collast.setCellValueFactory(new PropertyValueFactory<DetPat_class,String>("lastname"));
        coluser.setCellValueFactory(new PropertyValueFactory<DetPat_class,String>("username"));
        coldies.setCellValueFactory(new PropertyValueFactory<DetPat_class,String>("Disease"));
        colsymp.setCellValueFactory(new PropertyValueFactory<DetPat_class,String>("symptom"));
        colgendre.setCellValueFactory(new PropertyValueFactory<DetPat_class,String>("gendre"));
        colcin.setCellValueFactory(new PropertyValueFactory<DetPat_class,Integer>("cin"));
        colphone.setCellValueFactory(new PropertyValueFactory<DetPat_class,Integer>("phone"));
        coladd.setCellValueFactory(new PropertyValueFactory<DetPat_class,String>("address"));
        coldate.setCellValueFactory(new PropertyValueFactory<DetPat_class,String>("birth"));
        colblood.setCellValueFactory(new PropertyValueFactory<DetPat_class,String>("bloodGroup"));
        tablepatient1.setItems(data);
    }
}
