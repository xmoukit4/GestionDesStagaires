package com.example.gestionstage.Views;

import com.example.gestionstage.Controllers.UserController;
import com.example.gestionstage.HelloApplication;
import com.example.gestionstage.Models.PersonneModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class UserView {
    public static String Email;

    public static void ToUser(Stage stage, PersonneModel personneModel) throws IOException {
        Email = personneModel.getEmail();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("User.fxml"));
        StageUserChange(stage, personneModel, fxmlLoader);
    }

    public static void ShowUserOffers(Stage stage,PersonneModel personneModel) throws IOException {
        Email = personneModel.getEmail();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("UserOffres.fxml"));
        StageUserChange(stage, personneModel, fxmlLoader);
        UserController userController = fxmlLoader.getController();

        userController.buildData();
    }

    private static void StageUserChange(Stage stage, PersonneModel personneModel, FXMLLoader fxmlLoader) throws IOException {
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 1280, 768);
        stage.getScene().getWindow();
        stage.setScene(scene);
        UserController userController = fxmlLoader.getController();
        userController.UserName.setText(personneModel.getNom() + " " + personneModel.getPrenom());

        stage.show();
    }

    public static void ShowInformations(PersonneModel personneModel) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("MesInformations.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        UserController userController = fxmlLoader.getController();
        userController.NomPrenom.setText(personneModel.getNom() + " " + personneModel.getPrenom());
        userController.Email.setText(personneModel.getEmail());
        userController.Telephone.setText(personneModel.getTelephone());
        stage.show();
    }
    public static void ChangePass(int I) {
        if (I > 0) {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("MesInformations.fxml"));
            UserController userController = fxmlLoader.getController();
            userController.MotPasse.clear();
            userController.MotPassChange.setVisible(true);
        }
    }

    public static void ShowUserApplications(Stage stage, PersonneModel personneModel) throws IOException {
        Email = personneModel.getEmail();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("UserApplications.fxml"));
        StageUserChange(stage, personneModel, fxmlLoader);
        UserController userController = fxmlLoader.getController();
        userController.buildApplicationData();

    }
}
