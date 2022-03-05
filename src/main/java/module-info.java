module com.example.gestionstage {

    requires javafx.controls;
    requires javafx.fxml;

    requires validatorfx;
    requires org.kordamp.ikonli.javafx;

    opens com.example.gestionstage to javafx.base;
    opens com.example.gestionstage.Controllers to javafx.fxml;

    exports com.example.gestionstage;
    requires org.kordamp.ikonli.materialdesign2;
    requires java.sql;

}