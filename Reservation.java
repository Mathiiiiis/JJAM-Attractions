package model;
//ce fichier va nous être utile pour:
import java.time.LocalDate;

public class Reservation {
    private int id;
    private LocalDate date;
    private String parcNom;
    private int nombreTickets;

    // Constructeurs
    public Reservation() {}

    public Reservation(int id, LocalDate date, String parcNom, int nombreTickets) {
        this.id = id;
        this.date = date;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
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
}
