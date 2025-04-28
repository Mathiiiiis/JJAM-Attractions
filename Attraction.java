package model;
//ce fichier va nous être utile pour:

public class Attraction {
    private int id;
    private String nom;
    private String parc;
    private double prix_base;
    private int places;
    private String type;
    private String description;

    // 🔵 Constructeurs

    // Constructeur vide
    public Attraction() {}

    // Constructeur pour 4 paramètres (nom, parc, prix, description) ➔ pour PageCalendrier
    public Attraction(String nom, String parc, double prix_base, String description) {
        this.nom = nom;
        this.parc = parc;
        this.prix_base = prix_base;
        this.description = description;
    }

    // Constructeur pour création rapide (nom, parc, prix, description, places)
    public Attraction(String nom, String parc, double prix_base, String description, int places) {
        this.nom = nom;
        this.parc = parc;
        this.prix_base = prix_base;
        this.description = description;
        this.places = places;
    }

    // Constructeur complet (id, nom, parc, prix, type, description, places)
    public Attraction(int id, String nom, String parc, double prix_base, String type, String description, int places) {
        this.id = id;
        this.nom = nom;
        this.parc = parc;
        this.prix_base = prix_base;
        this.type = type;
        this.description = description;
        this.places = places;
    }

    // 🔵 Getters

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getParc() {
        return parc;
    }

    public double getPrix_base() {
        return prix_base;
    }

    public double getPrixBase() {
        return prix_base;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public int getPlaces() {
        return places;
    }

    // 🔵 Setters

    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setParc(String parc) {
        this.parc = parc;
    }

    public void setPrix_base(double prix_base) {
        this.prix_base = prix_base;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPlaces(int places) {
        this.places = places;
    }

    // 🔵 ➡️ Ajout du toString pour affichage correct dans JComboBox
    @Override
    public String toString() {
        return nom; // Affichera juste le nom de l'attraction dans les listes déroulantes
    }
}
