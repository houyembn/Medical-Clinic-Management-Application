package com.example.mini_projet_cabinet;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.EventObject;
import java.util.ResourceBundle;

public class loginuser implements Initializable {
        @FXML
        private Label messageerreur;

        @FXML
        private Label messageerreur1;

        @FXML
        private PasswordField passwordfeild;

        @FXML
        private TextField textfieldpassword;

        @FXML
        private CheckBox checkbox;

        @FXML
        private Label date;

        @FXML
        private TextField usernametextfield;

        public static String user;

         Connection con=MaConnexion.connecter();
         PreparedStatement pst;

    private EventObject actionEvent;


    @FXML
    void enter(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            passwordfeild.requestFocus();
        }
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
        void changeVisibility(ActionEvent event){
            if (checkbox.isSelected()){
                textfieldpassword.setText(passwordfeild.getText());
                textfieldpassword.setVisible(true);
                passwordfeild.setVisible(false);
                return;
            }
            passwordfeild.setText(textfieldpassword.getText());
            passwordfeild.setVisible(true);
            textfieldpassword.setVisible(false);
        }

        @FXML
        void loginButtonOnAction(ActionEvent event){

            if(usernametextfield.getText().isBlank() == true && passwordfeild.getText().isBlank() == true){
                messageerreur1.setText("Please entrer Username ");
                messageerreur.setText("Please entrer Password");
            }

            else if (usernametextfield.getText().isBlank() == true && passwordfeild.getText().isBlank() == false ){
                messageerreur1.setText("Please entrer Username ");
            }
            else if (usernametextfield.getText().isBlank() == false && passwordfeild.getText().isBlank() == true ){
                messageerreur.setText("Please entrer Password");
            }
            else if ((usernametextfield.getText() .equals("test") )&&
                    (textfieldpassword.getText().equals( "test")||(passwordfeild.getText().equals("test")))){


                /*Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Message");
                alert.setHeaderText(null);
                alert.setContentText("Successfully Login!!!");
                alert.show();*/

                FXMLLoader fxmlLoader1 = new FXMLLoader(getClass().getResource("menu.fxml"));
                Stage stage1 = new Stage();
                Scene scene1 = null;
                stage1 = (Stage) ((Node) event.getSource()).getScene().getWindow();
                try {
                    scene1 = new Scene(fxmlLoader1.load());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                stage1.setResizable(false);
                stage1.setScene(scene1);
                stage1.show();

                UserState.usertype=usernametextfield.getText();
            }
            else{
                try
                {
                    pst=con.prepareStatement("SELECT * FROM doctor where username = ? and password = ?");
                    pst.setString(1, usernametextfield.getText());
                    pst.setString(2, passwordfeild.getText());
                    ResultSet result = pst.executeQuery();

                    UserState.usertype = usernametextfield.getText();

                    if (result.next()){
                        /*System.out.println("Successfully Login!!!");
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Successfully Login!!!");
                        alert.show();*/

                        FXMLLoader fxmlLoader1 = new FXMLLoader(getClass().getResource("menu.fxml"));
                        Stage stage1 = new Stage();
                        Scene scene1 = null;
                        stage1 = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        try {
                            scene1 = new Scene(fxmlLoader1.load());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        stage1.setResizable(false);
                        stage1.setScene(scene1);
                        stage1.show();


                    }else{
                        System.out.println("Not connected!!!");
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Message");
                        alert.setHeaderText("Erreur!!");
                        alert.setContentText("Not connected!!!");
                        alert.show();
                    }
                }catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        @FXML
        void restButtonOnAction(ActionEvent event){
            usernametextfield.setText("");
            passwordfeild.setText("");
            textfieldpassword.setText("");
            messageerreur.setText("");
            messageerreur1.setText("");
        }

        @FXML
        void showDate(){
            Date d =new Date();
            SimpleDateFormat s = new SimpleDateFormat("dd-MM-YYYY");
            String dat =s.format(d);
            date.setText(dat);
        }

        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
        showDate();
        }
}
