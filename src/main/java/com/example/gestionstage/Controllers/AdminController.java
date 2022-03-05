package com.example.gestionstage.Controllers;

import com.example.gestionstage.HelloApplication;
import com.example.gestionstage.Models.DbModel;
import com.example.gestionstage.Models.PersonneModel;
import com.example.gestionstage.Views.MainView;
import com.example.gestionstage.Views.UserView;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.example.gestionstage.Views.AdminView.*;

public class AdminController {
    @FXML
    public TableView tableStagaires;
    @FXML
    public TableView tableOffres;
    @FXML
    private Text Title;
    ObservableList data = FXCollections.observableArrayList();
    ObservableList data1 = FXCollections.observableArrayList();

    @FXML
    private TextField titre;
    @FXML
    private TextField description;
    @FXML
    private TextField date;

    public void ShowStatistiques(ActionEvent event) {
    }

    public void Deconnextion() throws IOException {
        Stage stage = (Stage) Title.getScene().getWindow();
        MainView.ChangeView(stage, "Login.fxml");
    }


    public void stagaireData() {
        Connection c;
        data = FXCollections.observableArrayList();

        //ResultSet
        try {
            c = DbModel.getConnexion();
            String SQL = "SELECT nom,prenom,telephone,email,status,daterecp,demande.idcandidat,titre,description from personne,demande,candidat,offre,postuler where " +
                    "personne.idpersonne = candidat.idpersonne and demande.idcandidat=candidat.idcandidat and postuler.idoffre=offre.idoffre and postuler.idcandidat=candidat.idcandidat group by offre.idoffre";
            ResultSet rs = c.createStatement().executeQuery(SQL);
            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                //We are using non property style for making dynamic table
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList,
                        String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });

                tableStagaires.getColumns().addAll(col);
            }
            while (rs.next()) {
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    //Iterate Column
                    row.add(rs.getString(i));
                }
                data.add(row);

            }

            tableStagaires.setItems(data);


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void AdminStagaires() throws IOException {
        Stage stage = (Stage) Title.getScene().getWindow();
        PersonneModel personneModel = DbModel.GetPersonne(UserView.Email);
        ShowStagaires(stage, personneModel);

    }

    public void Accepter(MouseEvent mouseEvent) throws SQLException {
        data1 = (ObservableList) tableStagaires.getSelectionModel().getSelectedItem();
        int status = Integer.parseInt(data1.get(6).toString());
        Connection c = DbModel.getConnexion();
        String sql = "update demande set status=? where idcandidat = ?";

        PreparedStatement statement1 = c.prepareStatement(sql);
        statement1.setString(1, "Accepte");
        statement1.setInt(2, status);
        int j = statement1.executeUpdate();
        if (j > 0) {
            System.out.println("Candidature Accepte");
        }

        tableStagaires.getColumns().clear();
        stagaireData();


    }

    public void Refuser(MouseEvent mouseEvent) throws SQLException {
        data1 = (ObservableList) tableStagaires.getSelectionModel().getSelectedItem();
        int status = Integer.parseInt(data1.get(6).toString());
        Connection c = DbModel.getConnexion();
        String sql = "update demande set status=? where idcandidat = ?";

        PreparedStatement statement1 = c.prepareStatement(sql);
        statement1.setString(1, "Refuse");
        statement1.setInt(2, status);
        int j = statement1.executeUpdate();
        if (j > 0) {
            System.out.println("Candidature Refuse");
        }

        tableStagaires.getColumns().clear();
        stagaireData();
    }

    public void toOffre() throws IOException {
        Stage stage = (Stage) Title.getScene().getWindow();
        PersonneModel personneModel = DbModel.GetPersonne(UserView.Email);
        ShowUserOffers(stage, personneModel);

    }

    public void offreData() {
        Connection c;
        data1 = FXCollections.observableArrayList();

        //ResultSet
        try {
            c = DbModel.getConnexion();
            String SQL = "select * from offre";
            ResultSet rs = c.createStatement().executeQuery(SQL);
            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                //We are using non property style for making dynamic table
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList,
                        String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });

                tableOffres.getColumns().addAll(col);
            }
            while (rs.next()) {
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    //Iterate Column
                    row.add(rs.getString(i));
                }
                data1.add(row);

            }

            tableOffres.setItems(data1);


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void Ajouter() throws IOException {
        PersonneModel personneModel = DbModel.GetPersonne(UserView.Email);
        Stage stage = (Stage) Title.getScene().getWindow();
        AjouterOffre();


    }

    public void AjouterOffreAdmin() throws SQLException, IOException {
        Connection connection = DbModel.getConnexion();
        String sql = "insert into offre(idadmin,titre,description,date_pub) values (?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, 1);
        statement.setString(2, titre.getText());
        statement.setString(3, description.getText());
        statement.setString(4, date.getText());
        int rowsInserted = statement.executeUpdate();
        if (rowsInserted > 0) {
            titre.clear();
            description.clear();
            data.clear();

        }


    }

    public void Remove() throws SQLException {
        data1 = (ObservableList) tableOffres.getSelectionModel().getSelectedItem();
        int idoffre = Integer.parseInt(data1.get(0).toString());
        Connection connection = DbModel.getConnexion();

        String sql = "delete from offre where idoffre = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, idoffre);
        int resultSet = statement.executeUpdate();
        tableOffres.getColumns().clear();
        offreData();


    }

    public void Actualiser() throws IOException {
        toOffre();
    }
}
