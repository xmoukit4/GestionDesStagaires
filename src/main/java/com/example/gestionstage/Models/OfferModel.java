package com.example.gestionstage.Models;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.sql.Date;

public class OfferModel {
   public int idoffre;
    public SimpleIntegerProperty idadmin = new SimpleIntegerProperty();

    public SimpleStringProperty titre = new SimpleStringProperty();
    public SimpleStringProperty description = new SimpleStringProperty();
    public ObjectProperty<Date> date_pub = new SimpleObjectProperty<Date>();


    public  int getIdoffre() {
        return idoffre;
    }

    public void setIdoffre(int idoffre) {
        this.idoffre = idoffre;
    }

    public int getIdadmin() {
        return idadmin.get();
    }

    public SimpleIntegerProperty idadminProperty() {
        return idadmin;
    }

    public void setIdadmin(int idadmin) {
        this.idadmin.set(idadmin);
    }

    public String getTitre() {
        return titre.get();
    }

    public SimpleStringProperty titreProperty() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre.set(titre);
    }

    public String getDescription() {
        return description.get();
    }

    public SimpleStringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public Date getDate_pub() {
        return date_pub.get();
    }

    public ObjectProperty<Date> date_pubProperty() {
        return date_pub;
    }

    public void setDate_pub(Date date) {
        this.date_pub.set(date);
    }
}
