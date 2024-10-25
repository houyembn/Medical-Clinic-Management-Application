package com.example.mini_projet_cabinet;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import javafx.stage.FileChooser;
import org.w3c.dom.Document;

import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

public class DoctorDetails implements Initializable {
    @FXML
    private TableView<DetDoct_class> tdoctordetails;

    @FXML
    private TableColumn<DetDoct_class, String> coldays;

    @FXML
    private TableColumn<DetDoct_class, String> coladdr;

    @FXML
    private TableColumn<DetDoct_class, Integer> colcin;

    @FXML
    private TableColumn<DetDoct_class, String> coldep;

    @FXML
    private TableColumn<DetDoct_class, String> colgendre;

    @FXML
    private TableColumn<DetDoct_class, Integer> colphone;

    @FXML
    private TableColumn<DetDoct_class, String> colfirst;

    @FXML
    private TableColumn<DetDoct_class, String> colhours;

    @FXML
    private TableColumn<DetDoct_class, Integer> colid;

    @FXML
    private TableColumn<DetDoct_class, String> collast;

    @FXML
    private TableColumn<DetDoct_class, String> colpass;

    @FXML
    private TableColumn<DetDoct_class, String> coluser;

    @FXML
    private Button fleche;

    ObservableList<DetDoct_class> data = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        updateTable();
    }

    @FXML
    void exist (ActionEvent event) {
        Stage stage = new Stage();
        Scene scene = null;
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setResizable(false);
        stage.setScene(scene);
        stage.close();
    }

    @FXML
    public void precd(javafx.event.ActionEvent actionEvent){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("menu.fxml"));
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

    @FXML
    void imprimie(ActionEvent event) {
/*
        MessageFormat header = new MessageFormat(("Details Doctor:"));
        MessageFormat footer = new MessageFormat("Page{0,number,integer}");
        try {
           // tdoctordetails.print(JTable.PrintMode.FIT8WIDTH, header, footer)
        }catch (Exception e){
            throw new RuntimeException(e);
        }

        final FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showSaveDialog(new Stage());
        if(file != null){

            saveSystem(file, tdoctordetails.getItems().toString());
        }*/
/*
        Document doc = new Document();
        try {
            PdfWriter.getInstance(doc,new FileOutputStream("doctor.pdf" ));
            doc.open();
            String format ="dd/mm/yy hh:mm";
            SimpleDateFormat formater = new SimpleDateFormat(format);
            java.util.Date = new java.util.Date();
            com.itextpdf.text.Image img
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }*/
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

    private void updateTable(){
        table();
        coluser.setCellValueFactory(new PropertyValueFactory<DetDoct_class, String>("username"));
        colid.setCellValueFactory(new PropertyValueFactory<DetDoct_class, Integer>("doctor_id"));
        colfirst.setCellValueFactory(new PropertyValueFactory<DetDoct_class, String>("firstname"));
        collast.setCellValueFactory(new PropertyValueFactory<DetDoct_class, String>("lastname"));
        colpass.setCellValueFactory(new PropertyValueFactory<DetDoct_class, String>("password"));
        colcin.setCellValueFactory(new PropertyValueFactory<DetDoct_class, Integer>("cin"));
        coldep.setCellValueFactory(new PropertyValueFactory<DetDoct_class, String>("department"));
        colgendre.setCellValueFactory(new PropertyValueFactory<DetDoct_class, String>("gendre"));
        colphone.setCellValueFactory(new PropertyValueFactory<DetDoct_class, Integer>("phone"));
        coladdr.setCellValueFactory(new PropertyValueFactory<DetDoct_class, String>("address"));
        coldays.setCellValueFactory(new PropertyValueFactory<DetDoct_class, String>("working_days"));
        colhours.setCellValueFactory(new PropertyValueFactory<DetDoct_class, String>("workind_hours"));
        tdoctordetails.getItems();
        tdoctordetails.setItems(data);
    }

    private void table(){
        try{
            data.clear();
            Connection con = MaConnexion.connecter();
            ResultSet rs = con.createStatement().executeQuery("select username,doctor_id,firstname,lastname,password,cin,department,gendre,phone_no,address,working_days,working_hours from doctor ");
            while (rs.next()){
                data.add(new DetDoct_class(rs.getString("username"),rs.getInt("doctor_id"),rs.getString("firstname"),rs.getString("lastname"),rs.getString("password"),rs.getInt("cin"),rs.getString("department"),rs.getString("gendre"),rs.getInt("Phone_NO"),rs.getString("address"),rs.getString("working_days"),rs.getString("working_hours")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}

