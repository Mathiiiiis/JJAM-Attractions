package view;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import dao.AttractionDAOImpl;
import model.Attraction;
import model.Client;
import model.Profil;
import java.util.ArrayList;

public class ReservationGUI extends JFrame {

    public ReservationGUI(Client client, List<ConfirmationGUI.Ticket> panier) {
        super("Réservation d'Attractions");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panneau principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(240, 248, 255));

        // Panneau du haut pour le bouton de voir le panier et mon profil
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topPanel.setOpaque(false);

        JButton voirPanierBtn = new JButton("📦 Voir le panier");
        voirPanierBtn.setBackground(new Color(200, 200, 0));
        voirPanierBtn.setFont(new Font("SansSerif", Font.PLAIN, 14));
        // Action associée au bouton de voir le panier
        voirPanierBtn.addActionListener(e -> {
            // Logique pour afficher le panier ou passer à la page suivante
            double prixTotal = panier.stream()
                    .mapToDouble(t -> t.getPrixFinal())
                    .sum();
            new ConfirmationGUI(client, panier, prixTotal).setVisible(true); // Appel correct avec panier et prix total
            dispose(); // Fermer la page actuelle
        });

        // Bouton pour accéder au profil si le client n'est pas null
        JButton profilBtn = new JButton("Mon Profil");
        profilBtn.setBackground(new Color(0, 120, 215));
        profilBtn.setForeground(Color.WHITE);
        profilBtn.setFont(new Font("SansSerif", Font.BOLD, 16));
        profilBtn.addActionListener(e -> {
            if (client != null) {
                new ProfileGUI(client).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Vous devez être connecté pour accéder à votre profil.");
            }
        });

        topPanel.add(voirPanierBtn);
        topPanel.add(profilBtn); // Ajouter le bouton pour voir le profil
        mainPanel.add(topPanel, BorderLayout.NORTH);

        // Panneau pour afficher les attractions et ajouter au panier
        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(0, 1, 10, 10));
        gridPanel.setBackground(new Color(240, 248, 255));

        // Charger les attractions depuis la base de données
        List<Attraction> attractions = new AttractionDAOImpl().getAllAttractions();

        for (Attraction attraction : attractions) {
            JPanel card = new JPanel();
            card.setLayout(new BorderLayout());
            card.setBackground(Color.WHITE);

            JLabel title = new JLabel("🎢 " + attraction.getNom() + " - Parc: " + attraction.getParc());
            title.setFont(new Font("SansSerif", Font.BOLD, 14));

            // Description de l'attraction
            JTextArea desc = new JTextArea(attraction.getDescription() != null ? attraction.getDescription() : "Description non disponible.");
            desc.setEditable(false);
            desc.setLineWrap(true);
            desc.setWrapStyleWord(true);
            desc.setBackground(Color.WHITE);

            JPanel selectionPanel = new JPanel();
            selectionPanel.setBackground(Color.WHITE);

            // Calculer les prix pour chaque profil
            double prixBase = attraction.getPrixBase();
            double prixAdulte = prixBase * (1 - client.calculerReduction()); // Prix pour les adultes
            double prixEnfant = prixBase * (1 - client.calculerReduction()) * 0.5; // Réduction pour enfants
            double prixSenior = prixBase * (1 - client.calculerReduction()) * 0.7; // Réduction pour seniors

            // Affichage des prix directement à côté des menus déroulants
            selectionPanel.add(new JLabel("Prix Adulte: " + String.format("%.2f", prixAdulte) + " €"));
            selectionPanel.add(new JLabel("Prix Enfant: " + String.format("%.2f", prixEnfant) + " €"));
            selectionPanel.add(new JLabel("Prix Senior: " + String.format("%.2f", prixSenior) + " €"));

            // Champs de sélection des quantités pour chaque profil
            selectionPanel.add(new JLabel("Adultes : "));
            JSpinner spinnerAdulte = new JSpinner(new SpinnerNumberModel(0, 0, 10, 1)); // Adultes
            selectionPanel.add(spinnerAdulte);

            selectionPanel.add(new JLabel("Enfants : "));
            JSpinner spinnerEnfant = new JSpinner(new SpinnerNumberModel(0, 0, 10, 1)); // Enfants
            selectionPanel.add(spinnerEnfant);

            selectionPanel.add(new JLabel("Seniors : "));
            JSpinner spinnerSenior = new JSpinner(new SpinnerNumberModel(0, 0, 10, 1)); // Seniors
            selectionPanel.add(spinnerSenior);

            // Ajout d'un JCheckBox pour le Fast Pass
            JCheckBox fastPassCheckBox = new JCheckBox("Ajouter Fast Pass");
            fastPassCheckBox.setBackground(Color.WHITE);
            selectionPanel.add(fastPassCheckBox); // Ajouter le JCheckBox au panneau

            JButton addBtn = new JButton("Ajouter au panier");
            addBtn.setBackground(new Color(0, 150, 100));
            addBtn.setForeground(Color.WHITE);

            // Ajout au panier
            addBtn.addActionListener(e -> {
                // Vérifier si Fast Pass est sélectionné
                boolean fastPassSelected = fastPassCheckBox.isSelected();
                client.setHasFastPass(fastPassSelected);  // Appliquer l'option Fast Pass au client

                // Calculer le prix de l'attraction avec réduction et option Fast Pass
                double prixFinal = client.calculerPrix(attraction.getPrixBase());  // Calculer le prix avec réduction et Fast Pass si sélectionné

                // Si Fast Pass est sélectionné, ajouter 60% de supplément
                if (fastPassSelected) {
                    prixFinal += prixFinal * 0.6;  // Ajouter 60% de supplément pour Fast Pass
                }

                // Ajouter le ticket au panier avec le prix calculé
                for (int i = 0; i < (int) spinnerAdulte.getValue(); i++)
                    panier.add(new ConfirmationGUI.Ticket(attraction, Profil.REGULIER, prixFinal, fastPassSelected));
                for (int i = 0; i < (int) spinnerEnfant.getValue(); i++)
                    panier.add(new ConfirmationGUI.Ticket(attraction, Profil.ENFANT, prixFinal, fastPassSelected));
                for (int i = 0; i < (int) spinnerSenior.getValue(); i++)
                    panier.add(new ConfirmationGUI.Ticket(attraction, Profil.SENIOR, prixFinal, fastPassSelected));
            });

            JPanel southPanel = new JPanel(new BorderLayout());
            southPanel.setBackground(Color.WHITE);
            southPanel.add(selectionPanel, BorderLayout.CENTER);
            southPanel.add(addBtn, BorderLayout.EAST);

            card.add(title, BorderLayout.NORTH);
            card.add(desc, BorderLayout.CENTER);
            card.add(southPanel, BorderLayout.SOUTH);
            gridPanel.add(card);
        }

        // Ajout du panneau principal et du contenu
        mainPanel.add(gridPanel, BorderLayout.CENTER);
        add(mainPanel);
    }

    public static void main(String[] args) {
        // Pour tester avec un client fictif
        Client testClient = new Client();
        testClient.setNom("John Doe");
        testClient.setEmail("john.doe@example.com");
        testClient.setProfil(Profil.REGULIER); // Exemple de profil
        SwingUtilities.invokeLater(() -> new ReservationGUI(testClient, new ArrayList<>()).setVisible(true));
    }
}
