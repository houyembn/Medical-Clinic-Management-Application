package com.example.mini_projet_cabinet;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.animation.FadeTransition;
import javafx.util.Duration;

public class Splash implements Initializable {

    @FXML
    private Button buttontest;

    @FXML
    private AnchorPane root ;

    AnchorPane pane;

    @FXML
    private void loadSplashScreen() {
        try {
            HelloApplication.isSplashLoaded = true;
            pane = FXMLLoader.load(getClass().getResource(("splash.fxml")));
            root.getChildren().setAll(pane);

            FadeTransition fadeIn = new FadeTransition(Duration.seconds(3),pane);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            fadeIn.setCycleCount(1);

            FadeTransition fadeOut = new FadeTransition(Duration.seconds(3),pane);
            fadeOut.setFromValue(1);
            fadeOut.setToValue(0);
            fadeOut.setCycleCount(1);

            fadeIn.play();

            fadeIn.setOnFinished((e) -> {
                fadeOut.play();
            });

            fadeOut.setOnFinished((e) -> {
                try {
                    pane = FXMLLoader.load(getClass().getResource(("Loginuser.fxml")));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                root.getChildren().setAll(pane);
            });

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (!HelloApplication.isSplashLoaded){
            loadSplashScreen();
        }

    }
}



