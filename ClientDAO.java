package dao;

import model.Client;
import model.Profil;
import utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientDAO {

    // Connexion d'un client existant
    public Client login(String email, String password) {
        String sql = "SELECT * FROM clients WHERE email = ? AND motDePasse = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Client client = new Client();
                client.setId(rs.getInt("id"));
                client.setNom(rs.getString("nom"));
                client.setEmail(rs.getString("email"));
                client.setMotDePasse(rs.getString("motDePasse"));

                String profilString = rs.getString("profil");
                if (profilString != null) {
                    client.setProfil(Enum.valueOf(Profil.class, profilString));
                }
                client.setHasFastPass(rs.getBoolean("hasFastPass"));
                client.setAbonnement(rs.getString("abonnement"));
                return client;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // Inscription d'un nouveau client
    public boolean inscrireClient(String email, String motDePasse, String nom, Profil profil) {
        String sql = "INSERT INTO clients (email, motDePasse, nom, profil, hasFastPass, abonnement) VALUES (?, ?, ?, ?, ?, NULL)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setString(2, motDePasse);
            stmt.setString(3, nom);
            stmt.setString(4, profil.name());
            stmt.setBoolean(5, false); // Par défaut sans FastPass
            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Mise à jour des informations d'un client existant
    public boolean updateClient(Client client) {
        String sql = "UPDATE clients SET nom = ?, email = ?, motDePasse = ?, profil = ?, hasFastPass = ?, abonnement = ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, client.getNom());
            stmt.setString(2, client.getEmail());
            stmt.setString(3, client.getMotDePasse());
            stmt.setString(4, client.getProfil().name());
            stmt.setBoolean(5, client.hasFastPass());
            stmt.setString(6, client.getAbonnement());
            stmt.setInt(7, client.getId());

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Mise à jour uniquement de l'abonnement
    public void updateAbonnement(Client client) {
        String sql = "UPDATE clients SET abonnement = ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, client.getAbonnement());
            stmt.setInt(2, client.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Récupération de tous les clients (pour l'admin)
    public List<Client> getAllClients() {
        List<Client> clients = new ArrayList<>();
        String sql = "SELECT * FROM clients";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Client client = new Client();
                client.setId(rs.getInt("id"));
                client.setNom(rs.getString("nom"));
                client.setEmail(rs.getString("email"));
                client.setMotDePasse(rs.getString("motDePasse"));

                String profilString = rs.getString("profil");
                if (profilString != null) {
                    client.setProfil(Enum.valueOf(Profil.class, profilString));
                }
                client.setHasFastPass(rs.getBoolean("hasFastPass"));
                client.setAbonnement(rs.getString("abonnement"));

                clients.add(client);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clients;
    }
}