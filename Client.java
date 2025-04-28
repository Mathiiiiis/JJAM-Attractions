package model;

import java.time.*;

public class Client {
    private int id;
    private String nom;
    private String email;
    private String motDePasse;
    private Profil profil; // ENFANT, SENIOR, REGULIER
    private boolean hasFastPass;  // Option Fast Pass
    private int nbReservations = 0;
    private String abonnement; // 🔥 Nouveau champ pour gérer l'abonnement (VIP, PASS_ANNUEL, etc.)

    // Méthode pour calculer la réduction selon le profil
    public double calculerReduction() {
        switch (profil) {
            case ENFANT:
                return 0.5;  // 50% de réduction pour enfant
            case SENIOR:
                return 0.7;  // 30% de réduction pour senior
            case REGULIER:
            default:
                return 0.0;  // Pas de réduction spéciale
        }
    }

    // Méthode pour calculer le prix en tenant compte du FastPass
    public double calculerPrix(double prixBase) {
        double prixFinal = prixBase;

        if (hasFastPass) {
            prixFinal *= 1.60;  // +60% si FastPass activé
        }

        double reduction = calculerReduction();
        prixFinal -= prixFinal * reduction;

        return prixFinal;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMotDePasse() {
        return motDePasse;
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

    public boolean hasFastPass() {
        return hasFastPass;
    }

    public void setHasFastPass(boolean hasFastPass) {
        this.hasFastPass = hasFastPass;
    }

    public int getNbReservations() {
        return nbReservations;
    }

    public void setNbReservations(int nbReservations) {
        this.nbReservations = nbReservations;
    }

    // 🔥 Getter et Setter pour l'abonnement
    public String getAbonnement() {
        return abonnement;
    }

    public void setAbonnement(String abonnement) {
        this.abonnement = abonnement;
    }
}
