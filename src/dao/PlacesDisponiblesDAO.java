package dao;

import java.sql.*;
import java.time.LocalDate;

public class PlacesDisponiblesDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/attractions";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static int getPlacesDisponibles(int idAttraction, LocalDate date) {
        int places = -1;
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "SELECT places_disponibles FROM places_disponibles WHERE id_attraction = ? AND date = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, idAttraction);
                stmt.setDate(2, Date.valueOf(date));
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        places = rs.getInt("places_disponibles");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return places;
    }
}
