package com.example.gestionstage.Controllers;

import com.example.gestionstage.Models.PersonneModel;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

import static com.example.gestionstage.Models.DbModel.*;
import static com.example.gestionstage.Views.MainView.ChangeView;
import static com.example.gestionstage.Views.UserView.ToUser;

public class LoginController {

    @FXML
    private TextField EmailField;
    @FXML
    private TextField PasswordField;
    @FXML
    private Text MotPassIncorrect;


    public void Login() throws IOException {
        String email = EmailField.getText();
        Stage stage = (Stage) EmailField.getScene().getWindow();


        if (CheckLogin(EmailField.getText(), PasswordField.getText())) {
            if (CheckIsAdmin(EmailField.getText())) {

                ChangeView(stage,"Admin.fxml");

            } else {

                PersonneModel personneModel = GetPersonne(email);
                assert personneModel != null;
                ToUser(stage, personneModel);



            }
        } else {
            System.out.println("Error");
            MotPassIncorrect.setVisible(true);
            PasswordField.clear();
        }

    }

    public void Signup() throws IOException {
        Stage stage = (Stage) EmailField.getScene().getWindow();
        ChangeView(stage, "Signup.fxml");
    }

}

