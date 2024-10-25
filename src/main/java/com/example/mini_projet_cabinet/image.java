package com.example.mini_projet_cabinet;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;

public class image {


    @FXML
    private Button btnimage;

    @FXML
    private TextField selurl;

    @FXML
    private ImageView newimage;

    public static String url;

    @FXML
    void upload(ActionEvent event) {
        FileChooser chooser = new FileChooser();
        File f = chooser.showOpenDialog((new Stage()));
        String pathing = f.getAbsolutePath();
        selurl.setText(pathing);
        ImageView newartimage = new ImageView(""+pathing+"");
        newimage.setImage(newartimage.getImage());
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
}
