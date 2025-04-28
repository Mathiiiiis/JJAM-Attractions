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

        // Requête SQL corrigée
        String query = "SELECT r.id, a.nom, r.date FROM reservations r " +
                "JOIN attractions a ON r.attractionId = a.id " +
                "WHERE r.clientId = ?";

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/attractions", "root", "");
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, client.getId());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getDate("date")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        table.setModel(model);
        JScrollPane scrollPane = new JScrollPane(table);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(mainPanel);
    }

    public static void main(String[] args) {
        Client testClient = new Client();
        testClient.setId(9);
        testClient.setNom("QQ");

        SwingUtilities.invokeLater(() -> new ReservationHistoryGUI(testClient).setVisible(true));
    }
}
