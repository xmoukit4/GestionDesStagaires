package com.example.gestionstage.Models;

import java.sql.*;
import java.util.Objects;

public class DbModel {

    public static ResultSet isAdmin;
    private static Connection conn = null;

    public static Connection getConnexion() {
        if (conn == null) {
            try {
                Connection cx = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/gestion",
                        "root",
                        "moukit55"
                );
                System.out.println("Connecté avec succès");
                conn = cx;
                return conn;
            } catch (SQLException ex) {
                System.out.println("Une erreur s'est produite");
                ex.printStackTrace();
            }

        }
        return conn;
    }

    public static boolean CheckLogin(String email, String password) {
        Connection connection = getConnexion();
        boolean isAdmin;
        String sql = "Select * From personne where email = ?";


        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, email);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                PersonneModel personne = new PersonneModel();
                personne.setId(result.getInt("idpersonne"));
                personne.setEmail(result.getString("email"));
                personne.setPassword(result.getString("password"));

                if (Objects.equals(personne.getPassword(), password)) {
                    return true;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean InsertPersonne(PersonneModel personneModel) {
        Connection connection = getConnexion();
        String sql = "INSERT INTO personne (nom, prenom, telephone, email,password,cin) VALUES (?,?,?,?,?,?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, personneModel.getNom());
            statement.setString(2, personneModel.getPrenom());
            statement.setString(3, personneModel.getTelephone());
            statement.setString(4, personneModel.getEmail());
            statement.setString(5, personneModel.getPassword());
            statement.setString(6, personneModel.getCin());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Un nouveau Personne a ete insere avec s");
            }
            return true;
        } catch (SQLException e) {
            System.out.println("False");

        }
        return false;
    }

    public static boolean CheckIsAdmin(String Email) {
        Connection connection = getConnexion();
        String sql = "select * from admin, personne where email = ? and personne.idpersonne = admin.idpersonne";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, Email);

            isAdmin = statement.executeQuery();

            if (isAdmin.next()) {
                return true;
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static PersonneModel GetPersonne(String email) {
        Connection connection = getConnexion();
        String sql = "select * from personne where email = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, email);

            ResultSet result = statement.executeQuery();
            PersonneModel personne = new PersonneModel();

            if (result.next()) {
                personne.setId(result.getInt("idpersonne"));
                personne.setNom(result.getString("nom"));
                personne.setPrenom(result.getString("prenom"));
                personne.setTelephone(result.getString("telephone"));
                personne.setEmail(result.getString("email"));
                personne.setPassword(result.getString("password"));
            }
            return personne;


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int ChangePass(String email, String Password) {
        Connection connection = getConnexion();
        String sql = "UPDATE personne set password = ? where email = ? ";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, Password);
            statement.setString(2, email);
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("mot de pass change");
                return rowsInserted;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}