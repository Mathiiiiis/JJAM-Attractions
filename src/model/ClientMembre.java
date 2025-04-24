package model;

public class ClientMembre extends Client {
    private boolean abonnementAnnuel;
    private int nombreVisites;

    public ClientMembre(boolean abonnementAnnuel, int nombreVisites) {
        this.abonnementAnnuel = abonnementAnnuel;
        this.nombreVisites = nombreVisites;
    }

    @Override
    public double calculerReduction() {
        double reduction = 0.0;
        if (profil == Profil.ENFANT) reduction += 0.5;
        if (profil == Profil.SENIOR) reduction += 0.3;
        if (abonnementAnnuel) reduction += 0.2;
        if (nombreVisites > 10) reduction += 0.1;
        return Math.min(reduction, 0.8);
    }
}