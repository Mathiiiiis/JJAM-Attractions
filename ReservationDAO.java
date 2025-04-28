package dao;

import model.Client;
import model.Reservation;//ce fichier va nous être utile pour:
import model.Ticket;
import model.Attraction;
import utils.DBConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReservationDAO {

    // 🔥 Vérification disponibilité pour le CALENDRIER (résa par parc)
    public boolean verifierDisponibilite(List<Attraction> parcsChoisis, LocalDate date, int nbBillets) {
        try (Connection conn = DBConnection.getConnection()) {
            for (Attraction parc : parcsChoisis) {
                String sqlCapacite = "SELECT capacite FROM parcs WHERE nom = ?";
                try (PreparedStatement stmtCapacite = conn.prepareStatement(sqlCapacite)) {
                    stmtCapacite.setString(1, parc.getParc());
                    try (ResultSet rsCapacite = stmtCapacite.executeQuery()) {
                        if (rsCapacite.next()) {
                            int capacite = rsCapacite.getInt("capacite");

                            String sqlReservations = "SELECT SUM(nombre_tickets) AS total FROM reservations WHERE parc_nom = ? AND date_reservation = ?";
                            try (PreparedStatement stmtReservations = conn.prepareStatement(sqlReservations)) {
                                stmtReservations.setString(1, parc.getParc());
                                stmtReservations.setDate(2, java.sql.Date.valueOf(date));
                                try (ResultSet rsReservations = stmtReservations.executeQuery()) {
                                    if (rsReservations.next()) {
                                        int totalReservations = rsReservations.getInt("total");
                                        if ((totalReservations + nbBillets) > capacite) {
                                            return false;
                                        }
                                    }
                                }
                            }
                        } else {
                            return false;
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    // 🔥 Enregistrer une réservation globale pour bloquer la date (réservations)
    public void enregistrerReservation(List<Ticket> tickets, LocalDate date, List<Attraction> parcs) {
        String sql = "INSERT INTO reservations (date_reservation, parc_nom, nombre_tickets) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection()) {
            for (Attraction parc : parcs) {
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setDate(1, java.sql.Date.valueOf(date));
                    stmt.setString(2, parc.getNom());
                    stmt.setInt(3, tickets.size());
                    stmt.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 🔥 Nouvelle méthode pour récupérer l'historique par client connecté (commandes_client)
    public List<Reservation> getCommandesByClientId(int clientId) {
        List<Reservation> commandes = new ArrayList<>();
        String sql = "SELECT date_reservation, parc_nom, nombre_tickets FROM commandes_client WHERE client_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, clientId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Reservation reservation = new Reservation();
                    reservation.setDate(rs.getDate("date_reservation").toLocalDate());
                    reservation.setParcNom(rs.getString("parc_nom"));
                    reservation.setNombreTickets(rs.getInt("nombre_tickets"));
                    commandes.add(reservation);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return commandes;
    }
    public List<Integer> getAllClientIDsWithCommandes() {
        List<Integer> clientIds = new ArrayList<>();
        String sql = "SELECT DISTINCT client_id FROM commandes_client";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                clientIds.add(rs.getInt("client_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clientIds;
    }

    // 🔥 Récupérer juste le nombre de commandes par client connecté
    public int getNombreCommandesByClientId(int clientId) {
        String sql = "SELECT COUNT(*) AS total FROM commandes_client WHERE client_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, clientId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // 🔥 Utilisé pour le calendrier : Combien de tickets sur une date pour un parc
    public int getReservationsPourDateEtParc(LocalDate date, String parcNom) {
        String sql = "SELECT SUM(nombre_tickets) AS total FROM reservations WHERE parc_nom = ? AND date_reservation = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, parcNom);
            stmt.setDate(2, java.sql.Date.valueOf(date));

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // 🔥 Ajouter une nouvelle commande dans commandes_client
    public void ajouterCommandeClient(int clientId, LocalDate date, String parcNom) {
        String sql = "INSERT INTO commandes_client (client_id, date_reservation, parc_nom, nombre_tickets) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, clientId);
            stmt.setDate(2, java.sql.Date.valueOf(date));
            stmt.setString(3, parcNom);
            stmt.setInt(4, 1); // on considère que c'est 1 ticket par réservation

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 🔥 Ajouter une nouvelle réservation dans la table "reservations"
    public void ajouterReservation(String parcNom, LocalDate date, int nombreTickets) {
        String sql = "INSERT INTO reservations (parc_nom, date_reservation, nombre_tickets) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, parcNom);
            stmt.setDate(2, java.sql.Date.valueOf(date));
            stmt.setInt(3, nombreTickets);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 🔥 Méthode pour récupérer tous les clients
    public List<Client> getAllClients() {
        List<Client> clients = new ArrayList<>();
        String sql = "SELECT id, nom FROM clients";  // Remplace cette requête par la requête correspondant à ta base de données

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Client client = new Client();
                client.setId(rs.getInt("id"));
                client.setNom(rs.getString("nom"));
                clients.add(client);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clients;
    }
}
