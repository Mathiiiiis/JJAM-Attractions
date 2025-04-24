package model;

import java.time.*;

public class Client {
    public int id;
    public String nom;
    public String email;
    public String motDePasse;
    public Profil profil; // ENFANT, SENIOR, REGULIER
    private boolean hasFastPass;  // Nouveau champ pour l'option Fast Pass

    // Méthode pour calculer la réduction selon le profil
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

    // Méthode pour calculer le prix d'une attraction avec ou sans Fast Pass
    public double calculerPrix(double prixBase) {
        double prixFinal = prixBase;

        // Appliquer le supplément de 60% si Fast Pass est sélectionné
        if (hasFastPass) {
            prixFinal *= 1.60;  // Ajouter 60% au prix de base
        }

        // Appliquer la réduction en fonction du profil
        double reduction = calculerReduction();
        prixFinal -= prixFinal * reduction;

        return prixFinal;
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

    // Getter et setter pour Fast Pass
    public boolean hasFastPass() {
        return hasFastPass;
    }

    public void setHasFastPass(boolean hasFastPass) {
        this.hasFastPass = hasFastPass;
    }
}
