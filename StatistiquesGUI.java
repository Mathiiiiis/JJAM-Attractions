package view;
//ce fichier va nous être utile pour:
import dao.ReservationStatsDAO;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class StatistiquesGUI extends JFrame {

    public StatistiquesGUI() {
        setTitle("Statistiques des Réservations");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        ReservationStatsDAO statsDAO = new ReservationStatsDAO();
        Map<String, Integer> ticketsParParc = statsDAO.getNombreTicketsParParc();
        Map<String, Integer> ticketsParDate = statsDAO.getNombreTicketsParDate();

        // Création des datasets pour les graphiques
        DefaultCategoryDataset parcDataset = new DefaultCategoryDataset();
        for (Map.Entry<String, Integer> entry : ticketsParParc.entrySet()) {
            parcDataset.addValue(entry.getValue(), "Nombre de Tickets", entry.getKey());
        }

        DefaultCategoryDataset dateDataset = new DefaultCategoryDataset();
        for (Map.Entry<String, Integer> entry : ticketsParDate.entrySet()) {
            dateDataset.addValue(entry.getValue(), "Nombre de Tickets", entry.getKey());
        }

        // Création des graphiques
        JFreeChart parcChart = ChartFactory.createBarChart(
                "Nombre de Tickets par Parc",
                "Parc",
                "Nombre de Tickets",
                parcDataset
        );

        JFreeChart dateChart = ChartFactory.createBarChart(
                "Nombre de Tickets par Date",
                "Date",
                "Nombre de Tickets",
                dateDataset
        );

        // Création des panneaux pour les graphiques
        ChartPanel parcChartPanel = new ChartPanel(parcChart);
        parcChartPanel.setPreferredSize(new Dimension(800, 600));

        ChartPanel dateChartPanel = new ChartPanel(dateChart);
        dateChartPanel.setPreferredSize(new Dimension(800, 600));

        // Organisation des graphiques dans la fenêtre
        setLayout(new GridLayout(2, 1));
        add(parcChartPanel);
        add(dateChartPanel);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StatistiquesGUI().setVisible(true));
    }
}
