package com.example.gestionstage.Controllers;

import com.example.gestionstage.Models.DbModel;
import com.example.gestionstage.Models.PersonneModel;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;

import static com.example.gestionstage.Views.MainView.ChangeView;

public class SignupController {
    @FXML
    private TextField NomField;
    @FXML
    private TextField PrenomField;
    @FXML
    private TextField CinField;
    @FXML
    private TextField PasswordField;
    @FXML
    private TextField PhoneField;
    @FXML
    private TextField EmailField;
    @FXML
    private HBox Success;
    private int id = 0;

    public void Signup() {
        id++;

        PersonneModel personneModel = new PersonneModel(
                id, NomField.getText(), PrenomField.getText(),
                PhoneField.getText(), EmailField.getText(),
                PasswordField.getText(), CinField.getText());

        if (DbModel.InsertPersonne(personneModel)) {
            System.out.println("good");
            NomField.clear();
            PrenomField.clear();
            CinField.clear();
            PasswordField.clear();
            PhoneField.clear();
            EmailField.clear();
            Success.setVisible(true);
        } else {

        }
    }

    public void ReturnToLogin() throws IOException {
        Stage stage = (Stage) EmailField.getScene().getWindow();
        ChangeView(stage, "Login.fxml");
    }
}
