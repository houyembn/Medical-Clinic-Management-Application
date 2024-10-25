package com.example.mini_projet_cabinet;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;

public class Patient implements Initializable{
    @FXML
    private ComboBox<String> gendrep;

    @FXML
    private ComboBox<String> cbp;

    @FXML
    private TextField address;

    @FXML
    private TextField cin;

    @FXML
    private DatePicker datebirth;

    @FXML
    private TextField disease;

    @FXML
    private TextField first;

    @FXML
    private Label erreurcin;

    @FXML
    private Label erreurid;

    @FXML
    private Label erreurphone;

    @FXML
    private TextField last;

    @FXML
    private TextField patrid;

    @FXML
    private TextField phone;

    @FXML
    private TextField symp;

    @FXML
    private TextField username;

    @FXML
    private TableColumn<Patient_class,String> colblood;

    @FXML
    private TableColumn<Patient_class,String> coldies;

    @FXML
    private TableColumn<Patient_class,String> colfirst;

    @FXML
    private TableColumn<Patient_class,Integer> colid;

    @FXML
    private TableColumn<Patient_class,String> collast;

    @FXML
    private TableColumn<Patient_class,String> colsymp;

    @FXML
    private TableColumn<Patient_class,String> coluser;

    @FXML
    private TableColumn<Patient_class,Integer> colphone;

    @FXML
    private TableColumn<Patient_class,Integer> colcin;

    @FXML
    private TableColumn<Patient_class,String> coldate;

    @FXML
    private TableColumn<Patient_class,String> colgendre;

    @FXML
    private TableColumn<Patient_class,String> coladd;


    @FXML
    private TableView<Patient_class> tablepatient;

    int nbr;

    Connection con=MaConnexion.connecter();
    PreparedStatement pst;
    ObservableList<Patient_class> data = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gendrep.setItems(FXCollections.observableArrayList("Female","Male","child"));
        cbp.setItems(FXCollections.observableArrayList("O-","O+","A-","A+","B-","B+","AB-","AB+"));
        updateTable();
    }

    private void table(){
        try {
            data.clear();
            Connection con = MaConnexion.connecter();
            ResultSet rs = con.createStatement().executeQuery("select username,Patient_id,firstname,lastname,disease,symptom,blood_group from patient ");
            while (rs.next()){
                data.add(new Patient_class(rs.getString("username"),Integer.parseInt(rs.getString("Patient_id")),rs.getString("firstname"),rs.getString("lastname"),rs.getString("disease"),rs.getString("symptom"),rs.getString("blood_group")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void updateTable(){
        table();
        colid.setCellValueFactory(new PropertyValueFactory<Patient_class,Integer>("patient_id"));
        colfirst.setCellValueFactory(new PropertyValueFactory<Patient_class,String>("firstname"));
        collast.setCellValueFactory(new PropertyValueFactory<Patient_class,String>("lastname"));
        coluser.setCellValueFactory(new PropertyValueFactory<Patient_class,String>("username"));
        coldies.setCellValueFactory(new PropertyValueFactory<Patient_class,String>("Disease"));
        colsymp.setCellValueFactory(new PropertyValueFactory<Patient_class,String>("symptom"));
        colblood.setCellValueFactory(new PropertyValueFactory<Patient_class,String>("bloodGroup"));

        tablepatient.setItems(data);

        FilteredList<Patient_class> filter = new FilteredList<>(data, b-> true );
        username.textProperty().addListener(((observable, oldValue, newValue) -> {
            filter.setPredicate(Patient_class -> {

                if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
                    return true;
                }
                String searchKeyword = newValue.toLowerCase();
                if (Patient_class.getUsername().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                }
                else
                    return false;
            });
        }));

        SortedList<Patient_class> sortedData = new SortedList<>(filter);
        sortedData.comparatorProperty().bind(tablepatient.comparatorProperty());
        tablepatient.setItems(sortedData);
    }

    @FXML
    void getItems(MouseEvent event) {
        Integer index;
        index = tablepatient.getSelectionModel().getSelectedIndex();
       /* if(index<=1){
            return;
        }*/
        username.setText(coluser.getCellData(index).toString());
    }

    @FXML
    public void details(javafx.event.ActionEvent actionEvent){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("patientDetails.fxml"));
        Stage stage = new Stage();
        Scene scene = null;
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
    void resetp (ActionEvent event) {
        symp.setText("");
        disease.setText("");
        cin.setText("");
        username.setText("");
        phone.setText("");
        address.setText("");
        first.setText("");
        last.setText("");
        patrid.setText("");
        erreurcin.setText("");
        erreurphone.setText("");
        erreurid.setText("");
        cbp.setValue(null);
        gendrep.setValue(null);
        datebirth.setValue(null);
    }

    public void saveAction(ActionEvent event){
        if (idValide() == false || cinValide() == false || phoneValide() == false){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Message");
            alert.setHeaderText("Erreur!!");
            alert.setContentText("One or more fields are incorrect!");
            alert.showAndWait();
        }
        else if ((symp.getText()=="" )|| datebirth.getValue()== null || cin.getText()=="" || patrid.getText()=="" || username.getText()=="" || phone.getText()=="" || address.getText()=="" || first.getText()=="" || last.getText()=="" || disease.getText()=="" || cbp.getValue()== null || gendrep.getValue()== null ){
            System.out.println("Not Addedddd!");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Message");
            alert.setHeaderText("Erreur!!");
            alert.setContentText("One or more fields are empty!");
            alert.showAndWait();
        }else {

            try {

                pst = con.prepareStatement("insert into patient(patient_id,firstname,lastname,username,gendre,cin,phone_no,date_birth,address,blood_group,disease,symptom)values(?,?,?,?,?,?,?,?,?,?,?,?)");
                pst.setString(1, patrid.getText());
                pst.setString(2, first.getText());
                pst.setString(3, last.getText());
                pst.setString(4, username.getText());
                pst.setString(5, gendrep.getValue());
                pst.setString(6, cin.getText());
                pst.setString(7, phone.getText());
                pst.setString(8, datebirth.getValue().toString());
                pst.setString(9, address.getText());
                pst.setString(10, cbp.getValue());
                pst.setString(11, disease.getText());
                pst.setString(12, symp.getText());

                pst.executeUpdate();
                updateTable();

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
            tablepatient.setItems(data);
        }
    }


    public void search(ActionEvent event){

        try {
            String user = username.getText();
            pst=con.prepareStatement("select patient_id,firstname,lastname,username,gendre,cin,phone_no,date_birth,address,blood_group,disease,symptom from patient where username = ?");
            pst.setString(1,user);
            ResultSet result = pst.executeQuery();

            if(result.next()==true){
                patrid.setText(result.getString(1));
                first.setText(result.getString(2));
                last.setText(result.getString(3));
                address.setText(result.getString(9));
                cin.setText(result.getString(6));
                phone.setText(result.getString(7));
                disease.setText(result.getString(11));
                cbp.setValue(result.getString(10));
                datebirth.setValue(LocalDate.parse(result.getString(8)));
                gendrep.setValue(result.getString(5));
                symp.setText(result.getString(12));
            }
            else {
                patrid.setText("");
                first.setText("");
                last.setText("");
                address.setText("");
                cin.setText("");
                phone.setText("");
                disease.setText("");
                cbp.setValue(null);
                datebirth.setValue(null);
                gendrep.setValue(null);
                symp.setText("");

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Message");
                alert.setHeaderText("Erreur!!");
                alert.setContentText("Invalid Patient!");
                alert.showAndWait();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(ActionEvent event){
        if (idValide() == false || cinValide() == false || phoneValide() == false){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Message");
            alert.setHeaderText("Erreur!!");
            alert.setContentText("One or more fields are incorrect!");
            alert.showAndWait();
        } else if ((symp.getText()=="" )|| datebirth.getValue()== null || cin.getText()=="" || patrid.getText()=="" || username.getText()=="" || phone.getText()=="" || address.getText()=="" || first.getText()=="" || last.getText()=="" || disease.getText()=="" || cbp.getValue()== null || gendrep.getValue()== null ){
            System.out.println("Not Addedddd!");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Message");
            alert.setHeaderText("Erreur!!");
            alert.setContentText("One or more fields are empty!");
            alert.showAndWait();
        }else{

            try {
                String user = username.getText();
                pst = con.prepareStatement("update patient set patient_id = ?,firstname = ?,lastname = ?,gendre = ?,phone_no = ?,cin = ?,address = ?,date_birth = ?,blood_group = ?,disease = ?,symptom  = ? where username = ?");
                pst.setString(1, patrid.getText());
                pst.setString(2, first.getText());
                pst.setString(3, last.getText());
                pst.setString(4, gendrep.getValue().toString());
                pst.setString(5, phone.getText());
                pst.setString(6, cin.getText());
                pst.setString(7, address.getText());
                pst.setString(8, datebirth.getValue().toString());
                pst.setString(9, cbp.getValue().toString());
                pst.setString(10, disease.getText());
                pst.setString(11, symp.getText());
                pst.setString(12, username.getText());

                pst.executeUpdate();
                updateTable();

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


    private boolean cinValide(){
        String cin1=cin.getText();
        if (cin1.matches("^[0-9]*$")&& cin1.length()==8) {
            erreurcin.setText("");
            return true;
        }else if (cin.getText()=="") {
            erreurcin.setText("");
            return true;
        } else {
            erreurcin.setText("Error!!");
            return false;
        }
    }

    private boolean idValide(){
        String pat = patrid.getText();
        if (pat.matches("^[0-9]*$") && pat.length() == 5) {
            erreurid.setText("");
            return true;
        } else if (patrid.getText() == "") {
            erreurid.setText("");
            return true;
        } else {
            erreurid.setText("Error, Please Enter 5 Numbers!!");
            return false;
        }
    }

    private boolean phoneValide(){
        String phone1 = phone.getText();
        if (phone1.matches("^[0-9]*$") && phone1.length() == 8) {
            erreurphone.setText("");
            return true;
        } else if (phone.getText() == "") {
            erreurphone.setText("");
            return true;
        } else {
            erreurphone.setText("Error!!");
            return false;
        }
    }
}
