package dao;

import model.Attraction;
import utils.DBConnection;

import java.sql.*;//ce fichier va nous être utile pour:
import java.util.ArrayList;
import java.util.List;

public class AttractionDAOImpl {

    // Ajouter une attraction
    public void addAttraction(Attraction attraction) {
        String sql = "INSERT INTO attractions (nom, parc, prix_base, type, description) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, attraction.getNom());
            stmt.setString(2, attraction.getParc());
            stmt.setDouble(3, attraction.getPrix_base());
            stmt.setString(4, attraction.getType());
            stmt.setString(5, attraction.getDescription());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout de l'attraction : " + e.getMessage());
        }
    }

    // Modifier une attraction
    public void updateAttraction(Attraction attraction) {
        String sql = "UPDATE attractions SET parc = ?, type = ?, description = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, attraction.getParc());
            stmt.setString(2, attraction.getType());
            stmt.setString(3, attraction.getDescription());
            stmt.setInt(4, attraction.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Supprimer une attraction
    public void deleteAttraction(int attractionId) {
        String sql = "DELETE FROM attractions WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, attractionId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Récupérer toutes les attractions
    public List<Attraction> getAllAttractions() {
        List<Attraction> attractions = new ArrayList<>();
        String sql = "SELECT id, nom, parc, prix_base, type, description FROM attractions";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Attraction attraction = new Attraction();
                attraction.setId(rs.getInt("id"));
                attraction.setNom(rs.getString("nom"));
                attraction.setParc(rs.getString("parc"));
                attraction.setPrix_base(rs.getDouble("prix_base"));
                attraction.setType(rs.getString("type"));
                attraction.setDescription(rs.getString("description"));

                attractions.add(attraction);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return attractions;
    }

    // Récupérer une attraction par son nom
    public Attraction getAttractionByName(String nom) {
        String sql = "SELECT id, nom, parc, prix_base, type, description FROM attractions WHERE nom = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nom);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Attraction attraction = new Attraction();
                    attraction.setId(rs.getInt("id"));
                    attraction.setNom(rs.getString("nom"));
                    attraction.setParc(rs.getString("parc"));
                    attraction.setPrix_base(rs.getDouble("prix_base"));
                    attraction.setType(rs.getString("type"));
                    attraction.setDescription(rs.getString("description"));

                    return attraction;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // Mettre à jour uniquement le type et la description
    public void updateTypeAndDescription(Attraction attraction) {
        String sql = "UPDATE attractions SET type = ?, description = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, attraction.getType());
            stmt.setString(2, attraction.getDescription());
            stmt.setInt(3, attraction.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
