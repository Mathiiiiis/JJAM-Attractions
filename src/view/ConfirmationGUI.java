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
        private final double prixFinal;
        private final boolean hasFastPass;  // Champ ajouté pour le Fast Pass

        // Constructeur modifié pour accepter Fast Pass
        public Ticket(Attraction attraction, Profil profil, double prixFinal, boolean hasFastPass) {
            this.attraction = attraction;
            this.profil = profil;
            this.prixFinal = prixFinal;
            this.hasFastPass = hasFastPass;
        }

        public double getPrixFinal() {
            return prixFinal;
        }

        public Attraction getAttraction() {
            return attraction;
        }

        public Profil getProfil() {
            return profil;
        }

        public boolean hasFastPass() {
            return hasFastPass;  // Retourne si Fast Pass est activé pour ce ticket
        }
    }


    // Constructeur modifié pour accepter un client, un panier et le prix total
    public ConfirmationGUI(Client client, List<Ticket> panier, double prixTotal) {
        super("Confirmation de Réservation");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Panneau de confirmation avec les détails de la réservation
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(245, 250, 255));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        // Titre de la réservation
        JLabel titre = new JLabel("✔️ Réservation confirmée");
        titre.setFont(new Font("SansSerif", Font.BOLD, 20));
        titre.setForeground(new Color(0, 102, 204));
        titre.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Affichage du nom du client ou "Invité"
        JLabel clientLabel = new JLabel("Client : " + (client != null ? client.getNom() : "Invité"));
        clientLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Sous-titre pour afficher les attractions réservées
        JLabel sousTitre = new JLabel("Attractions réservées :");
        sousTitre.setFont(new Font("SansSerif", Font.BOLD, 16));
        sousTitre.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(titre);
        panel.add(Box.createVerticalStrut(10));
        panel.add(clientLabel);
        panel.add(Box.createVerticalStrut(20));
        panel.add(sousTitre);
        panel.add(Box.createVerticalStrut(10));

        // Regroupement des tickets par attraction et profil
        Map<String, Map<Profil, Long>> regroupement = panier.stream()
                .collect(Collectors.groupingBy(
                        t -> t.getAttraction().getNom() + " (" + t.getAttraction().getParc() + ")",
                        Collectors.groupingBy(Ticket::getProfil, Collectors.counting())
                ));

        // Affichage des tickets
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

            // Affichage de chaque ticket avec prix et Fast Pass
            for (Map.Entry<Profil, Long> entry : regroupement.get(nomAttraction).entrySet()) {
                String libelle = switch (entry.getKey()) {
                    case ENFANT -> "Enfant";
                    case SENIOR -> "Senior";
                    case REGULIER -> "Adulte";
                };

                // Récupérer le prix final de l'attraction pour chaque profil
                double priceWithFastPass = panier.stream()
                        .filter(ticket -> ticket.getAttraction().getNom().equals(nomAttraction) && ticket.getProfil() == entry.getKey())
                        .map(Ticket::getPrixFinal)
                        .findFirst()
                        .orElse(0.0); // Si aucun prix trouvé, 0

                JLabel ticketInfo = new JLabel("- " + entry.getValue() + " x " + libelle + " - Prix: " + String.format("%.2f", priceWithFastPass) + " €");

                // Afficher si le Fast Pass est sélectionné
                if (panier.stream()
                        .anyMatch(ticket -> ticket.getAttraction().getNom().equals(nomAttraction) && ticket.getProfil() == entry.getKey() && ticket.hasFastPass())) {
                    ticketInfo.setText(ticketInfo.getText() + " (Fast Pass)");
                }
                details.add(ticketInfo);
            }

            // Ajouter un bouton pour supprimer les billets du panier
            JButton removeBtn = new JButton("Supprimer");
            removeBtn.addActionListener(e -> {
                // Supprimer les tickets pour l'attraction actuelle du panier
                panier.removeIf(ticket -> ticket.getAttraction().getNom().equals(nomAttraction));
                this.setVisible(false); // Actualiser la vue de confirmation
                new ConfirmationGUI(client, panier, prixTotal).setVisible(true); // Réouvrir avec le panier mis à jour
            });

            JPanel southPanel = new JPanel(new BorderLayout());
            southPanel.setBackground(Color.WHITE);
            southPanel.add(details, BorderLayout.CENTER);
            southPanel.add(removeBtn, BorderLayout.EAST);  // Ajouter le bouton de suppression

            bloc.add(southPanel, BorderLayout.SOUTH);
            panel.add(bloc);
        }

        panel.add(Box.createVerticalStrut(20));

        // Affichage du prix total
        JLabel total = new JLabel("💶 Prix total à payer : " + String.format("%.2f", prixTotal) + " €");
        total.setFont(new Font("SansSerif", Font.BOLD, 16));
        total.setForeground(new Color(0, 128, 0));
        total.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(total);
        panel.add(Box.createVerticalStrut(30));

        JPanel boutons = new JPanel();
        boutons.setBackground(new Color(245, 250, 255));

        // Bouton Retour à l'accueil
        JButton retour = new JButton("← Retour à l'accueil");
        retour.addActionListener(e -> {
            new AccueilGUI().setVisible(true);
            dispose();
        });

        // Bouton Continuer la réservation
        JButton continuer = new JButton("↻ Continuer la réservation");
        continuer.addActionListener(e -> {
            new ReservationGUI(client, panier).setVisible(true);  // Passer le panier avec les tickets
            dispose();
        });

        boutons.add(continuer);
        boutons.add(retour);

        panel.add(boutons);
        add(panel);
    }
}
