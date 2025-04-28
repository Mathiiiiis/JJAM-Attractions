
package dao;
//ce fichier va nous être utile pour:
import model.DateEvenement;

import java.sql.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class CalendrierDAO {

    private final String URL = "jdbc:mysql://localhost:3306/attractions";
    private final String USER = "root";
    private final String PASSWORD = ""; // à adapter

    public Set<DateEvenement> chargerDates() {
        Set<DateEvenement> resultats = new HashSet<>();
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM dates_specifiques")) {

            while (rs.next()) {
                LocalDate date = rs.getDate("date_evenement").toLocalDate();
                String type = rs.getString("type");
                String desc = rs.getString("description");
                resultats.add(new DateEvenement(date, type, desc));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultats;
    }
}
