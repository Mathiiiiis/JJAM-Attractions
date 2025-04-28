package dao;

import model.Attraction;
import model.Parc;
import utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ParcDAOImpl implements ParcDAOInterface {
    private Connection connection;

    public ParcDAOImpl(Connection connection) {
        this.connection = connection;
    }

    // ✅ Récupérer uniquement les parcs (nom, prix, capacité)
    @Override
    public List<Parc> getAllParcs() {
        List<Parc> parcs = new ArrayList<>();
        String sql = "SELECT id, nom, prix_entree, capacite FROM parcs";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Parc parc = new Parc();
                parc.setId(rs.getInt("id"));
                parc.setNom(rs.getString("nom"));
                parc.setPrixEntree(rs.getDouble("prix_entree"));
                parc.setCapacite(rs.getInt("capacite"));

                parcs.add(parc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return parcs;
    }

    // ✅ Récupérer les parcs avec toutes leurs attractions associées
    public List<Parc> getAllParcsWithAttractions() {
        List<Parc> parcs = new ArrayList<>();
        String sql = "SELECT p.id AS parc_id, p.nom AS parc_nom, p.prix_entree, p.capacite, " +
                "a.id AS attraction_id, a.nom AS attraction_nom, a.prix_base, a.type, a.description " +
                "FROM parcs p " +
                "LEFT JOIN attractions a ON p.nom = a.parc " +
                "ORDER BY p.id";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            Parc currentParc = null;
            int lastParcId = -1;

            while (rs.next()) {
                int parcId = rs.getInt("parc_id");

                if (parcId != lastParcId) {
                    currentParc = new Parc();
                    currentParc.setId(parcId);
                    currentParc.setNom(rs.getString("parc_nom"));
                    currentParc.setPrixEntree(rs.getDouble("prix_entree"));
                    currentParc.setCapacite(rs.getInt("capacite"));
                    currentParc.setAttractions(new ArrayList<>());

                    parcs.add(currentParc);
                    lastParcId = parcId;
                }

                int attractionId = rs.getInt("attraction_id");
                if (attractionId != 0) {
                    Attraction attraction = new Attraction();
                    attraction.setId(attractionId);
                    attraction.setNom(rs.getString("attraction_nom"));
                    attraction.setPrix_base(rs.getDouble("prix_base"));
                    attraction.setType(rs.getString("type"));
                    attraction.setDescription(rs.getString("description"));
                    attraction.setParc(currentParc.getNom());

                    currentParc.getAttractions().add(attraction);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return parcs;
    }
}
