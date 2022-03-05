package com.example.gestionstage;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 768);
        stage.setTitle("Gestion des Stagaires");

        stage.setScene(scene);
        stage.getIcons().add(new Image(HelloApplication.class.getResourceAsStream("user_manage.png")));

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}