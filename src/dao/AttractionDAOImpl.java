package dao;

import model.*;
import utils.DBConnection;
import java.sql.*;
import java.util.*;

// ----- AttractionDAO -----
public class AttractionDAOImpl implements AttractionDAO {

    // Méthode pour récupérer toutes les attractions
    @Override
    public List<Attraction> getAllAttractions() {
        List<Attraction> list = new ArrayList<>();
        String sql = "SELECT * FROM attractions";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                // Ajoute chaque attraction à la liste
                list.add(new Attraction(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("parc"),
                        rs.getDouble("prix_base"),
                        rs.getString("type")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Méthode pour récupérer une attraction par son ID
    @Override
    public Attraction getAttractionById(int id) {
        String sql = "SELECT * FROM attractions WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Attraction(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("parc"),
                        rs.getDouble("prix_base"),
                        rs.getString("type")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Si l'attraction n'est pas trouvée
    }

    // Méthode pour ajouter une attraction
    @Override
    public void addAttraction(Attraction a) {
        String sql = "INSERT INTO attractions (nom, parc, prix_base, type) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, a.getNom());
            stmt.setString(2, a.getParc());
            stmt.setDouble(3, a.getPrixBase());
            stmt.setString(4, a.getType());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Méthode pour mettre à jour une attraction existante
    @Override
    public void updateAttraction(Attraction a) {
        String sql = "UPDATE attractions SET nom = ?, parc = ?, prix_base = ?, type = ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, a.getNom());
            stmt.setString(2, a.getParc());
            stmt.setDouble(3, a.getPrixBase());
            stmt.setString(4, a.getType());
            stmt.setInt(5, a.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Méthode pour supprimer une attraction
    @Override
    public void deleteAttraction(int id) {
        String sql = "DELETE FROM attractions WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
