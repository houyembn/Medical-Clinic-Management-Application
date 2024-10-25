module com.example.mini_projet_cabinet {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.mini_projet_cabinet to javafx.fxml;
    exports com.example.mini_projet_cabinet;
}