package dao;

import model.Attraction;
import utils.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AttractionDAOImpl {

    // Méthode pour récupérer toutes les attractions
    public List<Attraction> getAllAttractions() {
        List<Attraction> list = new ArrayList<>();
        String sql = "SELECT * FROM attractions";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                // Ajoute chaque attraction à la liste
                list.add(new Attraction(
                        rs.getString("nom"),
                        rs.getString("parc"),
                        rs.getDouble("prix_base"),
                        rs.getString("description")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Méthode pour ajouter une attraction
    public void addAttraction(Attraction a) {
        String sql = "INSERT INTO attractions (nom, parc, prix_base, description) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, a.getNom());
            stmt.setString(2, a.getParc());
            stmt.setDouble(3, a.getPrixBase());
            stmt.setString(4, a.getDescription());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Méthode pour récupérer une attraction par son ID
    public Attraction getAttractionById(int id) {
        String sql = "SELECT * FROM attractions WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Attraction(
                        rs.getString("nom"),
                        rs.getString("parc"),
                        rs.getDouble("prix_base"),
                        rs.getString("description")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Si l'attraction n'est pas trouvée
    }
}
