package model;
//ce fichier va nous être utile pour:
public class ClientRegulier extends Client {

    public ClientRegulier(Client client) {
        // Copier les données du client existant vers ce ClientRegulier
        setId(client.getId());
        setNom(client.getNom());
        setEmail(client.getEmail());
        setMotDePasse(client.getMotDePasse());
        setProfil(Profil.REGULIER); // 🔥 Utilisation du setter
        setHasFastPass(client.hasFastPass());
        setNbReservations(client.getNbReservations());
        setAbonnement(client.getAbonnement());
    }

    @Override
    public double calculerReduction() {
        return 0.0; // Pas de réduction pour les réguliers
    }
}
