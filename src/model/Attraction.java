package model;

public class Attraction {
    private int id;
    private String nom;
    private String parc;
    private double prixBase;
    private String type;
    private String description;


    public Attraction(int id, String nom, String parc, double prixBase, String type) {
        this.id = id;
        this.nom = nom;
        this.parc = parc;
        this.prixBase = prixBase;
        this.type = type;
    }

    public int getId() { return id; }
    public String getNom() { return nom; }
    public String getParc() { return parc; }
    public double getPrixBase() { return prixBase; }
    public String getType() { return type; }
    public Attraction(String nom, String parc, double prixBase, String description) {
        this.nom = nom;
        this.parc = parc;
        this.prixBase = prixBase;
        this.description = description;
    }
    public String getDescription() {
        return description;
    }


}
