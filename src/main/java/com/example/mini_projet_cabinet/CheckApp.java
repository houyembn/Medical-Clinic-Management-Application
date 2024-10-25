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
import java.time.LocalDate;
import java.util.ResourceBundle;

public class CheckApp implements Initializable{
    @FXML
    private TableView<Appoint_class> tableappoint1;


    @FXML
    private TableColumn colcard;

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
    private DatePicker selctdays;

    @FXML
    private Label name;

    //public static String username;

    Appoint_class check = null;

    ObservableList<Appoint_class> data = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        String user = UserState.usertype;
        System.out.println(user);
        if(!(UserState.usertype.equals("admin"))){
        updateTable(user);
        name.setText(user);
        }
    }

    private void table(String newValue){
        try {
            data.clear();
            Connection con = MaConnexion.connecter();

            PreparedStatement pst = con.prepareStatement("select id_App,Doctor_username,Patient_username,app_day,app_hours,work_days,work_hours from appointment where Doctor_username = ?");
            pst.setString(1, newValue);
            ResultSet rs = pst.executeQuery();
            while (rs.next()){
                data.add(new Appoint_class(rs.getInt("id_App"),rs.getString("Doctor_username"),rs.getString("patient_username"),rs.getString("app_day"),rs.getString("app_hours"),rs.getString("work_days"),rs.getString("work_hours")));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void updateTable(String user){
        table(user);
        coldays.setCellValueFactory(new PropertyValueFactory<Appoint_class, String>("workdays"));
        colseld.setCellValueFactory(new PropertyValueFactory<Appoint_class, String>("seldays"));
        colpuser.setCellValueFactory(new PropertyValueFactory<Appoint_class, String>("patientuser"));
        colidapp.setCellValueFactory(new PropertyValueFactory<Appoint_class, Integer>("app_id"));
        colselh.setCellValueFactory(new PropertyValueFactory<Appoint_class, String>("selhours"));
        colduser.setCellValueFactory(new PropertyValueFactory<Appoint_class, String>("doctoruser"));
        colhours.setCellValueFactory(new PropertyValueFactory<Appoint_class, String>("workhours"));


        //create a cell factory to insert a button in evry row
        Callback<TableColumn<Appoint_class, String>, TableCell<Appoint_class, String>> cellFactory=(Param) ->{

            //make the tablecell containing button
            final TableCell<Appoint_class, String> cell= new TableCell<Appoint_class, String>(){
                //override updateItem method
                @Override
                public void updateItem(String  item, boolean empty){
                    super.updateItem(item,empty);
                    //ensure that cell is created only on non-empty rows
                    if(empty){
                        setGraphic(null);
                        setText(null);
                    }else{
                        //now create the action button
                        final Button editButton = new Button("<>");

                        editButton.setStyle(
                                "-fx-background-color: #3c72a2;"
                        );

                        //attach listener on button, what to do when clicked
                        editButton.setOnAction(event ->{

                            //extract the clicked person object
                            check = tableappoint1.getSelectionModel().getSelectedItem();

                            //let show the card patient
                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fiche.fxml"));
                            Stage stage = new Stage();
                            Scene scene = null;
                            try {
                                scene = new Scene(fxmlLoader.load());
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }

                            //Fiche f = fxmlLoader.getController();
                           // Fiche f = new Fiche();
                            //Integer index = tableappoint1.getSelectionModel().getSelectedIndex();
                            //f.username.setText(colpuser.getCellData(index));
                            //f.setVi
                            //f.setItems(check.patientuser);

                            /*
                            Integer index;
                            index = tableappoint1.getSelectionModel().getSelectedIndex();
                            UserState.userpatient = colpuser.getCellData(index);
                            */

                            stage.setResizable(false);
                            stage.initStyle(StageStyle.UNDECORATED);
                            stage.setScene(scene);
                            stage.show();
                        });

                        HBox managebtn = new HBox(editButton);
                        managebtn.setStyle("-fx-alignment:center");

                        //remember to set the created button to cell
                        setGraphic(managebtn);
                        setText(null);
                    }
                };
            };
            //return the cell created
            return cell;
        };

        //set the factory to action column
        colcard.setCellFactory(cellFactory);

        tableappoint1.getItems().clear();
        tableappoint1.setItems(data);

    }
}
