package view;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.HashMap;

public class CalendarGUI extends JFrame {

    private final JPanel calendarPanel;
    private final Map<String, String> attractionAvailability;

    public CalendarGUI() {
        super("Calendrier de Disponibilité");

        // Configuration de la fenêtre principale
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panneau du calendrier
        calendarPanel = new JPanel(new GridLayout(0, 7)); // 7 colonnes pour les jours de la semaine
        calendarPanel.setBackground(new Color(240, 240, 240));

        // Initialisation des données de disponibilité des attractions
        attractionAvailability = new HashMap<>();
        attractionAvailability.put("2025-04-25", "Montagne Russe");
        attractionAvailability.put("2025-04-26", "Parcours Aventure");

        // Ajouter les jours de la semaine
        String[] daysOfWeek = {"Dim", "Lun", "Mar", "Mer", "Jeu", "Ven", "Sam"};
        for (String day : daysOfWeek) {
            JLabel label = new JLabel(day, SwingConstants.CENTER);
            label.setFont(new Font("SansSerif", Font.BOLD, 14));
            label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            calendarPanel.add(label);
        }

        // Générer les jours du mois (exemple pour avril 2025)
        LocalDate startDate = LocalDate.of(2025, 4, 1);
        int daysInMonth = startDate.lengthOfMonth();
        int startDayOfWeek = startDate.getDayOfWeek().getValue();

        // Ajouter les cases vides avant le premier jour du mois
        for (int i = 0; i < startDayOfWeek - 1; i++) {
            calendarPanel.add(new JLabel(" "));
        }

        // Ajouter les jours du mois
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        for (int day = 1; day <= daysInMonth; day++) {
            LocalDate date = LocalDate.of(2025, 4, day);
            String formattedDate = date.format(formatter);

            JButton dayButton = new JButton(String.valueOf(day));
            dayButton.setFont(new Font("SansSerif", Font.PLAIN, 12));
            dayButton.setBackground(Color.WHITE);
            dayButton.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

            // Vérifier la disponibilité de l'attraction pour cette date
            if (attractionAvailability.containsKey(formattedDate)) {
                dayButton.setBackground(Color.GREEN);
                dayButton.addActionListener(e -> {
                    String attraction = attractionAvailability.get(formattedDate);
                    JOptionPane.showMessageDialog(this, "Attraction disponible: " + attraction);
                });
            } else {
                dayButton.addActionListener(e -> {
                    JOptionPane.showMessageDialog(this, "Aucune attraction disponible.");
                });
            }

            calendarPanel.add(dayButton);
        }

        add(calendarPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new CalendarGUI().setVisible(true);
        });
    }
}
