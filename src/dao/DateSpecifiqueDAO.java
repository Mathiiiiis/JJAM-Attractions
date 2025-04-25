package dao;


import model.DateSpecifique;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import utils.DBConnection;

public class DateSpecifiqueDAO {

    public List<DateSpecifique> getAllDatesSpecifiques() {
        List<DateSpecifique> dates = new ArrayList<>();
        String sql = "SELECT * FROM dates_specifiques";  // Requête pour récupérer toutes les dates

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                DateSpecifique date = new DateSpecifique();
                date.setDate(rs.getDate("date").toLocalDate());
                date.setType(rs.getString("type"));
                date.setDescription(rs.getString("description"));
                dates.add(date);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dates;  // Retourne la liste des dates spécifiques
    }
}