package model;
//ce fichier va nous être utile pour:
public class ClientMembre extends Client {

    public ClientMembre(Client client) {
        // Copier les données du client existant vers ce ClientMembre
        setId(client.getId());
        setNom(client.getNom());
        setEmail(client.getEmail());
        setMotDePasse(client.getMotDePasse());
        setProfil(client.getProfil());    // 🔥 Correction ici (utiliser setProfil())
        setHasFastPass(client.hasFastPass());
        setNbReservations(client.getNbReservations());
        setAbonnement(client.getAbonnement());
    }

    // Tu peux ajouter ici des méthodes spécifiques aux membres si besoin (par ex. bénéficier d'avantages spéciaux)
}
