package dao;

import utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

public class ReservationStatsDAO {

    // ✅ Nouvelle méthode : nombre total de tickets vendus par parc (y compris 0)
    public Map<String, Integer> getNombreTicketsParParc() {
        Map<String, Integer> ticketsParParc = new LinkedHashMap<>();

        String sql = "SELECT p.nom AS parc_nom, COALESCE(SUM(r.nombre_tickets), 0) AS total_tickets " +
                "FROM parcs p " +
                "LEFT JOIN reservations r ON p.nom = r.parc_nom " +
                "GROUP BY p.nom " +
                "ORDER BY p.nom ASC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String parcNom = rs.getString("parc_nom");
                int totalTickets = rs.getInt("total_tickets");
                ticketsParParc.put(parcNom, totalTickets);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ticketsParParc;
    }

    // ✅ Cette méthode reste inchangée (tickets par date)
    public Map<String, Integer> getNombreTicketsParDate() {
        Map<String, Integer> ticketsParDate = new LinkedHashMap<>();

        String sql = "SELECT date_reservation, SUM(nombre_tickets) AS total_tickets " +
                "FROM reservations " +
                "GROUP BY date_reservation " +
                "ORDER BY date_reservation ASC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String date = rs.getString("date_reservation");
                int totalTickets = rs.getInt("total_tickets");
                ticketsParDate.put(date, totalTickets);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ticketsParDate;
    }
}
