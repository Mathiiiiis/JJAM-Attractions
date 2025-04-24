package model;

public class ClientRegulier extends Client {

    public ClientRegulier() {
        this.profil = Profil.REGULIER;  // Attribuer le profil spécifique
    }

    @Override
    public double calculerReduction() {
        return 0.0; // Pas de réduction pour les réguliers
    }
}
