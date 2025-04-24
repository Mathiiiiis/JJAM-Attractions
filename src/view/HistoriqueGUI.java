package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import dao.ClientDAO;
import dao.ReservationDAO;  // Ajoutez cette ligne pour importer ReservationDAO
import model.Client;
import model.Profil;
import model.Reservation; // Assurez-vous d'importer la classe Reservation
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class HistoriqueGUI extends JFrame {

    public HistoriqueGUI(Client client) {
        super("Historique des Réservations");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(240, 250, 255));
        panel.setBorder(BorderFactory.createEmptyBorder(60, 150, 60, 150));

        JLabel title = new JLabel("Historique des Réservations");
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Récupérer l'historique des réservations
        ReservationDAO reservationDAO = new ReservationDAO();
        List<Reservation> reservations = reservationDAO.getReservationsByClient(client);  // Appel correct

        String[] columnNames = {"ID", "Attraction", "Date", "Prix"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        for (Reservation reservation : reservations) {
            model.addRow(new Object[]{
                    reservation.getId(),
                    reservation.getAttraction().getNom(),  // Assurez-vous que cette méthode retourne correctement le nom de l'attraction
                    reservation.getDate(),
                    reservation.getPrix()
            });
        }

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        panel.add(title);
        panel.add(scrollPane);

        add(panel);
    }
}
