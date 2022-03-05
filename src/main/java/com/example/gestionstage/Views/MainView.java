package com.example.gestionstage.Views;
import com.example.gestionstage.HelloApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainView {

    public static void ChangeView(Stage stage, String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(fxml));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 1280, 768);
        stage.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }


}
