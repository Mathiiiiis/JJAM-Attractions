package model;

import java.time.*;

public class Client {
    public int id;
    public String nom;
    public String email;
    public String motDePasse;
    public Profil profil; // ENFANT, SENIOR, REGULIER

    // Implémentation de la méthode calculerReduction dans la classe Client
    // Par défaut, les réductions seront définies dans les sous-classes
    public double calculerReduction() {
        switch (profil) {
            case ENFANT:
                return 0.5;  // Réduction de 50% pour les enfants
            case SENIOR:
                return 0.7;  // Réduction de 30% pour les seniors
            case REGULIER:
            default:
                return 0.0;  // Pas de réduction pour les adultes
        }
    }

    // Getters et setters pour chaque attribut
    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public String getNom() {
        return nom;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public Profil getProfil() {
        return profil;
    }

    public void setProfil(Profil profil) {
        this.profil = profil;
    }
}
