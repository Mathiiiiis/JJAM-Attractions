package dao;

import model.Client;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import utils.DBConnection;
import model.Profil;

public class ClientDAO {

    // Méthode pour récupérer tous les clients
    public List<Client> getAllClients() {
        List<Client> clients = new ArrayList<>();
        String sql = "SELECT * FROM clients";  // Assurez-vous que la table "clients" contient des données

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Client client = new Client();
                client.setId(rs.getInt("id"));
                client.setNom(rs.getString("nom"));
                client.setEmail(rs.getString("email"));
                client.setMotDePasse(rs.getString("mot_de_passe"));
                client.setProfil(Profil.valueOf(rs.getString("profil")));  // Correspond à la colonne 'profil'
                clients.add(client);
            }

            // Affichage dans la console pour débogage
            System.out.println("Clients récupérés : " + clients.size());
            for (Client client : clients) {
                System.out.println(client.getNom() + " - " + client.getEmail() + " - " + client.getProfil());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clients;  // Retourner la liste des clients
    }

    // Inscrire un client
    public boolean inscrireClient(String nom, String email, String motDePasse, Profil profil) {
        String sql = "INSERT INTO clients (nom, email, mot_de_passe, profil) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nom);
            stmt.setString(2, email);
            stmt.setString(3, motDePasse);
            stmt.setString(4, profil.name());  // Utilisation de Profil.name() pour l'insert
            int rowsAffected = stmt.executeUpdate();
            System.out.println("Client inscrit : " + nom + " (" + email + ")");
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Récupérer un client par email et mot de passe
    public Client getClientByEmailAndPassword(String email, String motDePasse) {
        String sql = "SELECT * FROM clients WHERE email = ? AND mot_de_passe = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, motDePasse);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Client client = new Client();
                client.setId(rs.getInt("id"));
                client.setNom(rs.getString("nom"));
                client.setEmail(rs.getString("email"));
                client.setMotDePasse(rs.getString("mot_de_passe"));
                client.setProfil(Profil.valueOf(rs.getString("profil")));  // Assurez-vous que Profil est bien défini comme enum
                System.out.println("Client trouvé : " + client.getNom() + " - " + client.getEmail());
                return client;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Mettre à jour un client
    public boolean updateClient(Client client) {
        String sql = "UPDATE clients SET email = ?, mot_de_passe = ?, profil = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, client.getEmail());
            stmt.setString(2, client.getMotDePasse());
            stmt.setString(3, client.getProfil().name());  // Utiliser le Profil en tant qu'énumération
            stmt.setInt(4, client.getId());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;  // Si la mise à jour est réussie, retourne true
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;  // Si l'erreur se produit, retourne false
    }
}
