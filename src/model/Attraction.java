package model;

public class Attraction {

    private int id;
    private String nom;
    private String parc;
    private double prixBase;
    private String description;
    private int capacite; // ✅ Nouveau champ capacité ajouté

    // Constructeur sans paramètres
    public Attraction() {
    }

    // Constructeur avec paramètres pour initialiser l'attraction
    public Attraction(String nom, String parc, double prixBase, String description) {
        this.nom = nom;
        this.parc = parc;
        this.prixBase = prixBase;
        this.description = description;
        this.capacite = 100; // Valeur par défaut
    }

    // Constructeur complet avec capacité
    public Attraction(String nom, String parc, double prixBase, String description, int capacite) {
        this.nom = nom;
        this.parc = parc;
        this.prixBase = prixBase;
        this.description = description;
        this.capacite = capacite;
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

    public int getCapacite() {
        return capacite;
    }

    public void setCapacite(int capacite) {
        this.capacite = capacite;
    }

    // Méthodes nécessaires pour AdminGUI
    public double getPrix_base() {
        return getPrixBase();
    }

    public void setPrix_base(double prixBase) {
        setPrixBase(prixBase);
    }

    public int getPlaces() {
        return getCapacite();
    }

    public void setPlaces(int capacite) {
        setCapacite(capacite);
    }
}
