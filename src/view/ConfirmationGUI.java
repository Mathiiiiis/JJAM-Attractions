package view;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import model.Client;
import model.Profil;
import model.Attraction;

public class ConfirmationGUI extends JFrame {

    public static class Ticket {
        private final Attraction attraction;
        private final Profil profil;

        public Ticket(Attraction attraction, Profil profil) {
            this.attraction = attraction;
            this.profil = profil;
        }

        public Attraction getAttraction() {
            return attraction;
        }

        public Profil getProfil() {
            return profil;
        }
    }

    public ConfirmationGUI(Client client, List<Ticket> tickets, double prixTotal) {
        super("Confirmation de Réservation");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(245, 250, 255));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JLabel titre = new JLabel("✔️ Réservation confirmée");
        titre.setFont(new Font("SansSerif", Font.BOLD, 20));
        titre.setForeground(new Color(0, 102, 204));
        titre.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel clientLabel = new JLabel("Client : " + (client != null ? client.getNom() : "Invité"));
        clientLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel sousTitre = new JLabel("Attractions réservées :");
        sousTitre.setFont(new Font("SansSerif", Font.BOLD, 16));
        sousTitre.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(titre);
        panel.add(Box.createVerticalStrut(10));
        panel.add(clientLabel);
        panel.add(Box.createVerticalStrut(20));
        panel.add(sousTitre);
        panel.add(Box.createVerticalStrut(10));

        Map<String, Map<Profil, Long>> regroupement = tickets.stream()
                .collect(Collectors.groupingBy(
                        t -> t.getAttraction().getNom() + " (" + t.getAttraction().getParc() + ")",
                        Collectors.groupingBy(Ticket::getProfil, Collectors.counting())
                ));

        for (String nomAttraction : regroupement.keySet()) {
            JPanel bloc = new JPanel(new BorderLayout());
            bloc.setBackground(Color.WHITE);
            bloc.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

            JLabel nom = new JLabel("🎢 " + nomAttraction);
            nom.setFont(new Font("SansSerif", Font.BOLD, 14));
            bloc.add(nom, BorderLayout.NORTH);

            JPanel details = new JPanel();
            details.setLayout(new BoxLayout(details, BoxLayout.Y_AXIS));
            details.setBackground(Color.WHITE);

            for (Map.Entry<Profil, Long> entry : regroupement.get(nomAttraction).entrySet()) {
                String libelle = switch (entry.getKey()) {
                    case ENFANT -> "Enfant";
                    case SENIOR -> "Senior";
                    case REGULIER -> "Adulte";
                };
                JLabel ticketInfo = new JLabel("- " + entry.getValue() + " x " + libelle);
                details.add(ticketInfo);
            }

            bloc.add(details, BorderLayout.CENTER);
            panel.add(bloc);
        }

        panel.add(Box.createVerticalStrut(20));

        JLabel total = new JLabel("💶 Prix total à payer : " + String.format("%.2f", prixTotal) + " €");
        total.setFont(new Font("SansSerif", Font.BOLD, 16));
        total.setForeground(new Color(0, 128, 0));
        total.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(total);
        panel.add(Box.createVerticalStrut(30));

        JPanel boutons = new JPanel();
        boutons.setBackground(new Color(245, 250, 255));

        JButton retour = new JButton("← Retour à l'accueil");
        retour.addActionListener(e -> {
            new AccueilGUI().setVisible(true);
            dispose();
        });

        JButton continuer = new JButton("↻ Continuer la réservation");
        continuer.addActionListener(e -> {
            new ReservationGUI(client, tickets).setVisible(true);  // Passer le panier avec les tickets
            dispose();
        });

        boutons.add(continuer);
        boutons.add(retour);

        panel.add(boutons);
        add(panel);
    }
}
