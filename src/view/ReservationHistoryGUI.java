package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import model.Client;

public class ReservationHistoryGUI extends JFrame {

    public ReservationHistoryGUI(Client client) {
        super("Historique des Réservations");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panneau principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(240, 248, 255));

        // Titre
        JLabel title = new JLabel("Historique des Réservations");
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(title, BorderLayout.NORTH);

        // Tableau des réservations
        JTable table = new JTable();
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Attraction");
        model.addColumn("Date");

        // Requête pour récupérer les réservations du client
        String query = "SELECT r.id, a.nom, r.date_reservation FROM reservations r " +
                "JOIN attractions a ON r.attraction_id = a.id " +
                "WHERE r.client_id = ?";

        // Connexion à la base de données et exécution de la requête
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/attractions", "root", "");
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, client.getId());  // On passe l'ID du client dans la requête
            ResultSet rs = stmt.executeQuery();

            // Remplir la JTable avec les données récupérées
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id"),             // ID de la réservation
                        rs.getString("nom"),         // Nom de l'attraction
                        rs.getDate("date_reservation") // Date de réservation
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Appliquer le modèle à la JTable
        table.setModel(model);
        JScrollPane scrollPane = new JScrollPane(table);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Ajouter le panneau principal à la fenêtre
        add(mainPanel);
    }

    public static void main(String[] args) {
        // Test avec un client fictif ayant l'ID 1
        Client testClient = new Client();
        testClient.setId(9);  // Exemple d'ID, mettez l'ID du client qui existe dans votre base de données
        testClient.setNom("QQ");

        SwingUtilities.invokeLater(() -> new ReservationHistoryGUI(testClient).setVisible(true));
    }
}
