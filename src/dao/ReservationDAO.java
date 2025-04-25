package dao;

import model.Client;
import model.Reservation;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import utils.DBConnection;

public class ReservationDAO {

    // Méthode pour récupérer toutes les réservations d'un client
    public List<Reservation> getReservationsByClient(Client client) {
        List<Reservation> reservations = new ArrayList<>();
        String sql = "SELECT * FROM reservations WHERE client_id = ?";  // Assurez-vous que la table "reservations" contient une colonne client_id

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Assurez-vous que l'ID du client est bien renseigné
            if (client == null || client.getId() == 0) {
                throw new IllegalArgumentException("Client ID est invalide.");
            }

            // Paramétrer la requête avec l'ID du client
            stmt.setInt(1, client.getId());

            // Exécuter la requête et récupérer les résultats
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    // Créer et remplir l'objet Reservation
                    Reservation reservation = new Reservation();
                    reservation.setId(rs.getInt("id"));
                    reservation.setAttractionId(rs.getInt("attraction_id"));
                    reservation.setClient(client);  // Associer le client à la réservation
                    reservation.setDate(rs.getDate("date"));
                    reservation.setPrix(rs.getDouble("prix"));
                    reservations.add(reservation);  // Ajouter la réservation à la liste
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur SQL : " + e.getMessage());
            e.printStackTrace();  // Vous pouvez aussi utiliser un logger ici
        } catch (IllegalArgumentException e) {
            System.err.println("Erreur : " + e.getMessage());
        }

        return reservations;  // Retourne la liste des réservations pour ce client
    }
}
