package model;

public class Ticket {
    private String nom;
    private String profil;
    private double prix;
    private boolean fastPass;
    private Client client; // ✅ On ajoute le client associé (optionnel)

    public Ticket(String nom, String profil, double prix, boolean fastPass) {
        this.nom = nom;
        this.profil = profil;
        this.prix = prix;
        this.fastPass = fastPass;
    }

    // Nouveau constructeur si besoin d'assigner un Client directement
    public Ticket(String nom, String profil, double prix, boolean fastPass, Client client) {
        this.nom = nom;
        this.profil = profil;
        this.prix = prix;
        this.fastPass = fastPass;
        this.client = client;
    }

    public String getNom() {
        return nom;
    }

    public String getProfil() {
        return profil;
    }

    public double getPrix() {
        return prix;
    }

    public boolean isFastPass() {
        return fastPass;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setProfil(String profil) {
        this.profil = profil;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public void setFastPass(boolean fastPass) {
        this.fastPass = fastPass;
    }
}
