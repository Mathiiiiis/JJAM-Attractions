package model;

public class ClientEnfant extends Client { 

    public ClientEnfant() {
        this.profil = Profil.ENFANT;  // Attribuer le profil spécifique
    }

    @Override
    public double calculerReduction() {
        return 0.5; // Réduction de 50% pour les enfants
    }
}
