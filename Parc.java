package model;

import java.util.List;

public class Parc {
    private int id;
    private String nom;
    private double prixEntree;
    private int capacite; // ✅ Ajouté
    private List<Attraction> attractions; // ✅ Si tu utilises getAllParcsWithAttractions()

    // Getters/Setters

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

    public double getPrixEntree() {
        return prixEntree;
    }

    public void setPrixEntree(double prixEntree) {
        this.prixEntree = prixEntree;
    }

    public int getCapacite() {
        return capacite;
    }

    public void setCapacite(int capacite) {
        this.capacite = capacite;
    }

    public List<Attraction> getAttractions() {
        return attractions;
    }

    public void setAttractions(List<Attraction> attractions) {
        this.attractions = attractions;
    }
}
