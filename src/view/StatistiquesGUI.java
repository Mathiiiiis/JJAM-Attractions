package view;

import dao.AttractionDAOImpl;
import dao.ReservationDAO;
import model.Attraction;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;


public class StatistiquesGUI extends JFrame {
    public StatistiquesGUI() {
        setTitle("Statistiques d'Occupation des Attractions");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        String[] columnNames = {"Attraction", "Taux d'Occupation (%)"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(model);

        AttractionDAOImpl attractionDAO = new AttractionDAOImpl();
        ReservationDAO reservationDAO = new ReservationDAO();

        List<Attraction> attractions = attractionDAO.getAllAttractions();

        for (Attraction attraction : attractions) {
            int reservations = reservationDAO.countReservationsByAttractionId(attraction.getId());
            int capacite = attraction.getCapacite();
            double tauxOccupation = (capacite > 0) ? (reservations * 100.0) / capacite : 0;
            model.addRow(new Object[]{attraction.getNom(), String.format("%.2f", tauxOccupation)});
        }

        add(new JScrollPane(table));
    }
}
