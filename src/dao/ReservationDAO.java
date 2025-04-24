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
            stmt.setInt(1, client.getId());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Reservation reservation = new Reservation();
                reservation.setId(rs.getInt("id"));
                reservation.setAttractionId(rs.getInt("attraction_id"));
                reservation.setClient(client);
                reservation.setDate(rs.getDate("date"));
                reservation.setPrix(rs.getDouble("prix"));
                reservations.add(reservation);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reservations;  // Retourne la liste des réservations pour ce client
    }
}
