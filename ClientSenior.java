package model;

public class ClientSenior extends Client {

    public ClientSenior(Client client) {
        // Copier les données du client existant vers ce ClientSenior
        setId(client.getId());
        setNom(client.getNom());
        setEmail(client.getEmail());
        setMotDePasse(client.getMotDePasse());
        setProfil(Profil.SENIOR);   // 🔥 Correctement avec le setter
        setHasFastPass(client.hasFastPass());
        setNbReservations(client.getNbReservations());
        setAbonnement(client.getAbonnement());
    }

    @Override
    public double calculerReduction() {
        return 0.3; // Réduction de 30% pour les seniors
    }
}
