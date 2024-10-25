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
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Doctor implements Initializable{

    @FXML
    private CheckBox checkbox;

    @FXML
    private ComboBox<String> cb;

    @FXML
    private ComboBox<String> gendre;

    @FXML
    private TextField phone;

    @FXML
    private TextField cin;

    @FXML
    private TextField address;

    @FXML
    private TextField doctorid;

    @FXML
    private TextField first;

    @FXML
    private TextField hours;

    @FXML
    private TextField last;

    @FXML
    private TextField pass;

    @FXML
    private TextField username;

    @FXML
    private DatePicker days;

    @FXML
    private PasswordField pass2;

    @FXML
    private Label date;

    @FXML
    private Label erreurcin;

    @FXML
    private Label erreurid;

    @FXML
    private Label erreurpass;

    @FXML
    private Label erreurphone;

    @FXML
    private Label erreurlast;

    @FXML
    private Label erreuruser;

    @FXML
    private Label erreurfirst;

    @FXML
    private TableView<Doctor_class> tabledoctor;

    @FXML
    private TableColumn<Doctor_class, String> coldays;

    @FXML
    private TableColumn<Doctor_class, String> colfirst;

    @FXML
    private TableColumn<Doctor_class, String> colhours;

    @FXML
    private TableColumn<Doctor_class, Integer> colid;

    @FXML
    private TableColumn<Doctor_class, String> collast;

    @FXML
    private TableColumn<Doctor_class, String> colpass;

    @FXML
    private TableColumn<Doctor_class, String> coluser;

    Connection con=MaConnexion.connecter();
    PreparedStatement pst;
    ObservableList<Doctor_class> data = FXCollections.observableArrayList();

    @FXML
    void reset (ActionEvent event) {
        hours.setText("");
        days.setValue(null);
        cin.setText("");
        pass.setText("");
        username.setText("");
        phone.setText("");
        address.setText("");
        first.setText("");
        last.setText("");
        doctorid.setText("");
        erreurid.setText("");
        erreurcin.setText("");
        erreurphone.setText("");
        cb.setValue(null);
        gendre.setValue(null);
        pass2.setText("");
        erreurlast.setText("");
        erreurpass.setText("");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gendre.setItems(FXCollections.observableArrayList("Female","Male"));
        cb.setItems(FXCollections.observableArrayList("Dentist","Cardiology","Neurology","orthopadics","General Surgey"));

        updateTable();
        //showDate();
    }
    private void updateTable(){
        table();
        coluser.setCellValueFactory(new PropertyValueFactory<Doctor_class, String>("username"));
        colid.setCellValueFactory(new PropertyValueFactory<Doctor_class, Integer>("doctor_id"));
        colfirst.setCellValueFactory(new PropertyValueFactory<Doctor_class, String>("firstname"));
        collast.setCellValueFactory(new PropertyValueFactory<Doctor_class, String>("lastname"));
        colpass.setCellValueFactory(new PropertyValueFactory<Doctor_class, String>("password"));
        coldays.setCellValueFactory(new PropertyValueFactory<Doctor_class, String>("working_days"));
        colhours.setCellValueFactory(new PropertyValueFactory<Doctor_class, String>("workind_hours"));
        //tabledoctor.getItems().clear();
        tabledoctor.getItems();
        tabledoctor.setItems(data);

        FilteredList<Doctor_class> filter = new FilteredList<>(data, b-> true );
        username.textProperty().addListener(((observable, oldValue, newValue) -> {
            filter.setPredicate(Doctor_class -> {

                if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
                    return true;
                }
                String searchKeyword = newValue.toLowerCase();
                if (Doctor_class.getUsername().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                }
                else
                    return false;
            });
        }));

        SortedList<Doctor_class> sortedData = new SortedList<>(filter);
        sortedData.comparatorProperty().bind(tabledoctor.comparatorProperty());
        tabledoctor.setItems(sortedData);
    }


    @FXML
    void getItems(MouseEvent event) {
        Integer index;
        index = tabledoctor.getSelectionModel().getSelectedIndex();
        username.setText(coluser.getCellData(index));
    }

    private void table(){
        try{
            data.clear();
            Connection con = MaConnexion.connecter();
            ResultSet rs = con.createStatement().executeQuery("select username,doctor_id,firstname,lastname,password,working_days,working_hours from doctor ");
            while (rs.next()){
                data.add(new Doctor_class(rs.getString("username"),rs.getInt("doctor_id"),rs.getString("firstname"),rs.getString("lastname"),rs.getString("password"),rs.getString("working_days"),rs.getString("working_hours")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void details(javafx.event.ActionEvent actionEvent){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("doctorDetails.fxml"));
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
    void changeVisibility(ActionEvent event){
        if (checkbox.isSelected()){
            pass.setText(pass2.getText());
            pass.setVisible(true);
            pass2.setVisible(false);
            return;
        }
        pass2.setText(pass.getText());
        pass2.setVisible(true);
        pass.setVisible(false);
    }

    public void saveAction(ActionEvent event){

        if (idValide() == false || cinValide() == false || phoneValide() == false){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Message");
            alert.setHeaderText("Erreur!!");
            alert.setContentText("One or more fields are incorrect!");
            alert.showAndWait();
        }
        else if((hours.getText()=="" )|| days.getValue()== null || cin.getText()=="" || pass2.getText()=="" || username.getText()=="" || phone.getText()=="" || address.getText()=="" || first.getText()=="" || last.getText()=="" || doctorid.getText()=="" || cb.getValue()== null || gendre.getValue()== null ){
            System.out.println("Not Addedddd!");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Message");
            alert.setHeaderText("Erreur!!");
            alert.setContentText("One or more fields are empty!");
            alert.showAndWait();
        }else {
            try {

                pst=con.prepareStatement("insert into doctor(doctor_id,firstname,lastname,username,password,gendre,phone_no,cin,address,working_days,working_hours,department)values(?,?,?,?,?,?,?,?,?,?,?,?)");
                pst.setString(1, doctorid.getText());
                pst.setString(2, first.getText());
                pst.setString(3, last.getText());
                pst.setString(4, username.getText());
                pst.setString(5, pass2.getText());
                pst.setString(6, gendre.getValue());
                pst.setString(7, phone.getText());
                pst.setString(8, cin.getText());
                pst.setString(9, address.getText());
                pst.setString(10, days.getValue().toString());
                pst.setString(11, hours.getText());
                pst.setString(12, cb.getValue());
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


    public void search(ActionEvent event){

        try {
            String user = username.getText();
            pst=con.prepareStatement("select doctor_id,firstname,lastname,username,password,gendre,phone_no,cin,address,working_days,working_hours,department from doctor where username = ?");
            pst.setString(1,user);
            ResultSet result = pst.executeQuery();

            if(result.next()==true){
                doctorid.setText(result.getString(1));
                first.setText(result.getString(2));
                last.setText(result.getString(3));
                address.setText(result.getString(9));
                cin.setText(result.getString(8));
                phone.setText(result.getString(7));
                hours.setText(result.getString(11));
                days.setValue(LocalDate.parse(result.getString(10)));
                pass2.setText(result.getString(5));
                pass.setText(result.getString(5));
                gendre.setValue(result.getString(6));
                cb.setValue(result.getString(12));
            }
            else {
                doctorid.setText("");
                first.setText("");
                last.setText("");
                address.setText("");
                cin.setText("");
                phone.setText("");
                hours.setText("");
                days.setValue(null);
                pass2.setText("");
                gendre.setValue(null);
                cb.setValue(null);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Message");
                alert.setHeaderText("Erreur!!");
                alert.setContentText("Invalid Doctor!");
                alert.showAndWait();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(ActionEvent event){
        if (idValide() == false || cinValide() == false || phoneValide() == false) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Message");
            alert.setHeaderText("Erreur!!");
            alert.setContentText("One or more fields are incorrect!");
            alert.showAndWait();
        }else if((hours.getText()=="" )|| days.getValue()== null || cin.getText()=="" || pass2.getText()=="" || username.getText()=="" || phone.getText()=="" || address.getText()=="" || first.getText()=="" || last.getText()=="" || doctorid.getText()=="" || cb.getValue()== null || gendre.getValue()== null ){
            System.out.println("Not Addedddd!");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Message");
            alert.setHeaderText("Erreur!!");
            alert.setContentText("One or more fields are empty!");
            alert.showAndWait();
        }else {
            try {
                String user = username.getText();
                pst = con.prepareStatement("update doctor set doctor_id = ?,firstname = ?,lastname = ?,password = ?,gendre = ?,phone_no = ?,cin = ?,address = ?,working_days = ?,working_hours = ?,department = ? where username = ?");
                pst.setString(1, doctorid.getText());
                pst.setString(2, first.getText());
                pst.setString(3, last.getText());
                pst.setString(4, pass.getText());
                pst.setString(5, gendre.getValue().toString());
                pst.setString(6, phone.getText());
                pst.setString(7, cin.getText());
                pst.setString(8, address.getText());
                pst.setString(9, days.getValue().toString());
                pst.setString(10, hours.getText());
                pst.setString(11, cb.getValue().toString());
                pst.setString(12, username.getText());

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
        String pat = doctorid.getText();
        if (pat.matches("^[0-9]*$") && pat.length() == 5) {
            erreurid.setText("");
            return true;
        } else if (doctorid.getText() == "") {
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

    private boolean chaineValide(){
        Pattern p = Pattern.compile("[a-zA-Z]+");
        Matcher m = p.matcher(first.getText());
        if (m.find() && m.group().equals(first.getText())) {
            erreurfirst.setText("");
            return true;
        } else if (phone.getText() == "") {
            erreurfirst.setText("");
            return true;
        } else {
            erreurfirst.setText("Error!!");
            return false;
        }
    }

    private boolean userValide(){
        if (username.getText() == first.getText()+"."+last.getText()){
            erreuruser.setText("");
            return true;
        }else if (username.getText() == "") {
            erreuruser.setText("");
            return true;
        } else {
            erreuruser.setText("Error!!");
            return false;
        }
    }
}

