package com.example.gestionstage.Controllers;
import com.example.gestionstage.Models.DbModel;
import com.example.gestionstage.Models.OfferModel;
import com.example.gestionstage.Models.PersonneModel;
import com.example.gestionstage.Views.MainView;
import com.example.gestionstage.Views.UserView;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.sql.*;

import static com.example.gestionstage.Views.UserView.ChangePass;
import static com.example.gestionstage.Views.UserView.ShowInformations;


public class UserController{
    ObservableList data = FXCollections.observableArrayList();
    ObservableList data1 = FXCollections.observableArrayList();
    ObservableList data3 = FXCollections.observableArrayList();

    @FXML
    public Text UserName;

    @FXML
    public Text NomPrenom;

    @FXML
    public Text Email;
    @FXML
    public Text Telephone;
    @FXML
    public Text MotPassChange;

    @FXML
    public TextField MotPasse;

    @FXML
    public TableView<OfferModel> TableOffres;
    @FXML
    public TableView TableApplications;

    @FXML
    public  TableColumn<OfferModel,String> titre;

    @FXML
    public  TableColumn<OfferModel,String> description;
    @FXML
    public  TableColumn<OfferModel, String> date;


    public void Deconnextion() throws IOException {
        Stage stage = (Stage) UserName.getScene().getWindow();
        MainView.ChangeView(stage, "Login.fxml");
    }

    public void MesInformations() throws IOException {
        PersonneModel personneModel = DbModel.GetPersonne(UserView.Email);
        Stage stage = (Stage) UserName.getScene().getWindow();
        ShowInformations(personneModel);
    }

    public void ChangerMotPasse() {
        int i;
        i = DbModel.ChangePass(UserView.Email, MotPasse.getText());
        ChangePass(i);
    }

    public void ShowUserOffers() throws IOException {
        Stage stage = (Stage) UserName.getScene().getWindow();
        PersonneModel personneModel = DbModel.GetPersonne(UserView.Email);
        UserView.ShowUserOffers(stage,personneModel);
    }
    public void InsertCandidat() throws SQLException {

        PersonneModel personneModel = DbModel.GetPersonne(UserView.Email);
        Connection c = DbModel.getConnexion();
        String SQL1 = "Select * from candidat where idpersonne = ?";
        PreparedStatement statement = c.prepareStatement(SQL1);
        statement.setInt(1, personneModel.getId());
        ResultSet resultSet = statement.executeQuery();

        if(resultSet.next()){

        }
        else{

        String SQL = "INSERT into candidat (idpersonne, datenaissance, etablissement, specialite) values (?,?,?,?)";
        PreparedStatement preparedStatement = c.prepareStatement(SQL);
        preparedStatement.setInt(1, personneModel.getId());
        preparedStatement.setString(2, "2020-05-05");
        preparedStatement.setString(3,"test");
        preparedStatement.setString(4, "test");

        int rowsInserted = preparedStatement.executeUpdate();
        if (rowsInserted > 0) {
            System.out.println("candidat sucess");
        }}
    }

    public void postuler(MouseEvent mouseEvent) throws SQLException {
        InsertCandidat();
        data1 = (ObservableList) TableOffres.getSelectionModel().getSelectedItem();
        int idoffre = Integer.parseInt(data1.get(0).toString());
        System.out.println(idoffre);
        Connection c = DbModel.getConnexion();
        PersonneModel personneModel = DbModel.GetPersonne(UserView.Email);
        PersonneModel personneModel1 = new PersonneModel();
        String SQL2 = "Select idcandidat from candidat where idpersonne = ?";
        PreparedStatement statement1 = c.prepareStatement(SQL2);
        statement1.setInt(1, personneModel.getId());
        ResultSet resultSet = statement1.executeQuery();
        if(resultSet.next()){
            personneModel1.setId(resultSet.getInt("idcandidat"));
        }

        String sql1 = "INSERT into postuler (idoffre,idcandidat)  values (?,?)";
        PreparedStatement statement = c.prepareStatement(sql1);
        statement.setInt(1, idoffre);
        statement.setInt(2, personneModel1.getId());
        int rowsInserted1 = statement.executeUpdate();
        if (rowsInserted1 > 0) {
            System.out.println("postuler success");
        }


        String sql2 = "INSERT into demande (idcandidat,daterecp,status)  values (?,?,?)";
        PreparedStatement statement2 = c.prepareStatement(sql2);
        statement2.setInt(1, personneModel1.getId());
        statement2.setString(2, String.valueOf(java.time.LocalDate.now()));
        statement2.setString(3, "En cours");

        int rowsInserted2 = statement2.executeUpdate();
        if (rowsInserted2 > 0) {
            System.out.println("demande success");
        }

    }

     public void buildData(){
            Connection c;
            data = FXCollections.observableArrayList();

            //ResultSet
            try {
                c = DbModel.getConnexion();
                String SQL = "SELECT * from offre";
                ResultSet rs = c.createStatement().executeQuery(SQL);
               for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                    final int j = i;
                    TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
                    col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList,
                            String>, ObservableValue<String>>() {
                        public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                            return new SimpleStringProperty(param.getValue().get(j).toString());
                        }
                    });

                    TableOffres.getColumns().addAll(col);
                }
                while(rs.next()){
                    //Iterate Row
                    ObservableList<String> row = FXCollections.observableArrayList();
                    for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++){
                        //Iterate Column
                        row.add(rs.getString(i));
                    }
                    data.add(row);

                }

                TableOffres.setItems(data);




            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    public void toApplication() throws IOException {
        Stage stage = (Stage) UserName.getScene().getWindow();
        PersonneModel personneModel = DbModel.GetPersonne(UserView.Email);
        UserView.ShowUserApplications(stage,personneModel);
    }
    public void buildApplicationData(){
        Connection c;
        data3 = FXCollections.observableArrayList();

        //ResultSet
        try {
            c = DbModel.getConnexion();
            String SQL = "SELECT titre,description,date_pub,status from offre,demande,postuler where offre.idoffre = postuler.idoffre and postuler.idcandidat = demande.idcandidat GROUP BY offre.idoffre";

            ResultSet rs = c.createStatement().executeQuery(SQL);
            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList,
                        String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });

                TableApplications.getColumns().addAll(col);
            }
            while(rs.next()){
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++){
                    //Iterate Column
                    row.add(rs.getString(i));
                }
                data3.add(row);

            }

            TableApplications.setItems(data3);




        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
