package model;

public class Attraction {

    private int id;
    private String nom;
    private String parc;
    private double prixBase;
    private String description;

    // Constructeur sans paramètres
    public Attraction() {
    }

    // Constructeur avec paramètres pour initialiser l'attraction
    public Attraction(int id, String nom, String parc, double prixBase, String description) {
        this.id = id;
        this.nom = nom;
        this.parc = parc;
        this.prixBase = prixBase;
        this.description = description;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getParc() {
        return parc;
    }

    public void setParc(String parc) {
        this.parc = parc;
    }

    public double getPrixBase() {
        return prixBase;
    }

    public void setPrixBase(double prixBase) {
        this.prixBase = prixBase;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
