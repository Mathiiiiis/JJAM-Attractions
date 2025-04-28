package model;

import java.util.Date;

public class Commande {

    private int id;
    private int clientId;
    private Date dateReservation;
    private String parcNom;
    private int nombreTickets;

    // Constructeur
    public Commande(int id, int clientId, Date dateReservation, String parcNom, int nombreTickets) {
        this.id = id;
        this.clientId = clientId;
        this.dateReservation = dateReservation;
        this.parcNom = parcNom;
        this.nombreTickets = nombreTickets;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public Date getDateReservation() {
        return dateReservation;
    }

    public void setDateReservation(Date dateReservation) {
        this.dateReservation = dateReservation;
    }

    public String getParcNom() {
        return parcNom;
    }

    public void setParcNom(String parcNom) {
        this.parcNom = parcNom;
    }

    public int getNombreTickets() {
        return nombreTickets;
    }

    public void setNombreTickets(int nombreTickets) {
        this.nombreTickets = nombreTickets;
    }

    // Méthode toString() pour afficher les informations de la commande
    @Override
    public String toString() {
        return "Commande{" +
                "id=" + id +
                ", clientId=" + clientId +
                ", dateReservation=" + dateReservation +
                ", parcNom='" + parcNom + '\'' +
                ", nombreTickets=" + nombreTickets +
                '}';
    }
}
