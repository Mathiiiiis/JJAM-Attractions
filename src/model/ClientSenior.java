package model;

public class ClientSenior extends Client {

    public ClientSenior() {
        this.profil = Profil.SENIOR;  // Attribuer le profil spécifique
    }

    @Override
    public double calculerReduction() {
        return 0.3; // Réduction de 30% pour les seniors
    }
}
