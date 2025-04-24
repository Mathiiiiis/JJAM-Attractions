package model;

import java.util.Date;

public class Reservation {

    private int id;
    private Attraction attraction;  // Vous pouvez utiliser l'objet Attraction ici pour associer l'attraction directement.
    private int attractionId;  // Ajoutez un champ pour l'attractionId si vous en avez besoin directement.
    private Client client;
    private Date date;
    private double prix;

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Attraction getAttraction() {
        return attraction;
    }

    public void setAttraction(Attraction attraction) {
        this.attraction = attraction;
    }

    public int getAttractionId() {
        return attractionId;
    }

    public void setAttractionId(int attractionId) {
        this.attractionId = attractionId;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }
}
