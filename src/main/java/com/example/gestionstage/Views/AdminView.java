package com.example.gestionstage.Views;

import com.example.gestionstage.Controllers.AdminController;
import com.example.gestionstage.Controllers.UserController;
import com.example.gestionstage.HelloApplication;
import com.example.gestionstage.Models.PersonneModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminView {
    public static String Email;

    public static void ShowStagaires(Stage stage, PersonneModel personneModel) throws IOException {
        Email = personneModel.getEmail();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("AdminStagaires.fxml"));
        StageUserChange(stage, personneModel, fxmlLoader);
        AdminController adminController = fxmlLoader.getController();
        adminController.stagaireData();

    }
    public static void ShowUserOffers(Stage stage, PersonneModel personneModel) throws IOException {
        Email = personneModel.getEmail();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("offreAdmin.fxml"));
        StageUserChange(stage, personneModel, fxmlLoader);
        AdminController adminController = fxmlLoader.getController();
        adminController.offreData();


    }
    private static void StageUserChange(Stage stage, PersonneModel personneModel, FXMLLoader fxmlLoader) throws IOException {
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 1280, 768);
        stage.getScene().getWindow();
        stage.setScene(scene);

        stage.show();
    }

    public static void AjouterOffre() throws IOException {

            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("AjouterOffre.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }


}
