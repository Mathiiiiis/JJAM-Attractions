package model;
//ce fichier va nous être utile pour:
public class ClientEnfant extends Client {

    public ClientEnfant(Client client) {
        // Copier les données du client existant vers ce ClientEnfant
        setId(client.getId());
        setNom(client.getNom());
        setEmail(client.getEmail());
        setMotDePasse(client.getMotDePasse());
        setProfil(client.getProfil());    // 🔥 Utiliser setProfil() pour accéder correctement
        setHasFastPass(client.hasFastPass());
        setNbReservations(client.getNbReservations());
        setAbonnement(client.getAbonnement());
    }
    @Override
    public double calculerReduction() {
        return 0.5; // Réduction de 30% pour les seniors
    }
}
