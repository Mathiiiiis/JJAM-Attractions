/*package view;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;
import dao.DateSpecifiqueDAO;
import model.DateSpecifique;
import model.Client;
import model.Ticket;
import java.util.ArrayList;

public class CalendarGUI extends JFrame {

    private List<DateSpecifique> datesSpecifiques;

    public CalendarGUI() {
        setTitle("Sélectionner une date");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Récupérer les dates spécifiques depuis la base de données
        DateSpecifiqueDAO dateSpecifiqueDAO = new DateSpecifiqueDAO();
        datesSpecifiques = dateSpecifiqueDAO.getAllDatesSpecifiques();

        // Création du JCalendar
        JCalendar calendar = new JCalendar();
        calendar.setWeekOfYearVisible(false);

        // Ajouter des dates spéciales
        calendar.getDayChooser().addDateEvaluator(date -> {
            // Colorier les lundis en gris avec "Parc fermé"
            if (date.getDayOfWeek().getValue() == 1) {
                return new java.awt.Color(192, 192, 192); // Couleur gris
            }
            // Colorier les weekends (samedi et dimanche) en bleu
            else if (date.getDayOfWeek().getValue() == 6 || date.getDayOfWeek().getValue() == 7) {
                return new java.awt.Color(173, 216, 230); // Bleu clair pour les weekends
            }

            // Vérifier si c'est un jour spécial
            for (DateSpecifique dateSpecifique : datesSpecifiques) {
                if (dateSpecifique.getDate().equals(date)) {
                    // Changer la couleur en rouge pour les jours spéciaux
                    return new java.awt.Color(255, 99, 71);
                }
            }

            return null;
        });

        // Ajouter l'action lors du clic sur une date
        calendar.getDayChooser().addPropertyChangeListener(evt -> {
            if ("day".equals(evt.getPropertyName())) {
                LocalDate currentDate = LocalDate.of(calendar.getYear(), calendar.getMonth() + 1, calendar.getDay());

                // Créer un client fictif ou utiliser un client existant
                Client client = new Client();
                client.setNom("Client Exemple");
                client.setEmail("client@example.com");
                // Ajouter des informations supplémentaires si nécessaire

                // Créer une liste de tickets vide (ou ajouter les tickets sélectionnés)
                List<Ticket> panier = new ArrayList<>();

                // Rediriger vers l'écran de réservation avec la date sélectionnée
                new ReservationGUI(client, panier, currentDate).setVisible(true);
                dispose(); // Fermer le calendrier après sélection
            }
        });

        // Ajouter le calendrier dans un panel
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(calendar, BorderLayout.CENTER);

        // Ajouter un bouton pour fermer le calendrier
        JButton closeBtn = new JButton("Fermer");
        closeBtn.addActionListener(e -> dispose());
        panel.add(closeBtn, BorderLayout.SOUTH);

        add(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CalendarGUI().setVisible(true));
    }
}
*/