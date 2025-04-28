package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    // Remplacez par votre nom de base de données, utilisateur et mot de passe
    private static final String URL = "jdbc:mysql://localhost:3306/attractions";  // Remplacez "attractions" par le nom de votre base de données
    private static final String USER = "root";  // Remplacez "root" par votre nom d'utilisateur MySQL
    private static final String PASSWORD = "";  // Remplacez "" par votre mot de passe MySQL, s'il y en a un

    public static Connection getConnection() {
        try {
            // Charger le driver JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;  // Retourne null si la connexion échoue
        }
    }
}
