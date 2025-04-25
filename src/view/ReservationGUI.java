package view;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import dao.AttractionDAOImpl;
import model.Attraction;
import model.Client;
import model.Profil;
import view.ConfirmationGUI.Ticket;
import controller.ReservationController;


public class ReservationGUI extends JFrame {
    private final List<Ticket> panier = new ArrayList<>();
    private final JLabel prixLabel = new JLabel("Total Panier : 0.00 €");
    private final List<Attraction> attractions;

    public ReservationGUI(Client client, List<Ticket> panier) {
        super("Réservation d'Attractions");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        this.panier.addAll(panier);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(240, 248, 255));

        // Panneau du haut pour le bouton de voir/modifier son profil
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topPanel.setOpaque(false);

        // Bouton Voir/Modifier Profil
        JButton profileBtn = new JButton("Voir/Modifier mon Profil");
        profileBtn.setBackground(new Color(200, 200, 0));
        profileBtn.setFont(new Font("SansSerif", Font.PLAIN, 14));
        profileBtn.addActionListener(e -> {
            // Ouvre la fenêtre du profil
            new ProfileGUI(client).setVisible(true);
        });

        // Bouton Voir le Panier
        JButton voirPanierBtn = new JButton("📦 Voir le Panier");
        voirPanierBtn.setBackground(new Color(0, 150, 100));
        voirPanierBtn.setFont(new Font("SansSerif", Font.PLAIN, 14));
        voirPanierBtn.addActionListener(e -> {
            double prixTotal = panier.stream()
                    .mapToDouble(t -> new ReservationController().calculerPrix(t.getAttraction(), t.getProfil(), java.time.LocalDate.now(), false))
                    .sum();
            new ConfirmationGUI(client, panier, prixTotal).setVisible(true);
            dispose();  // Fermer la page actuelle
        });

        topPanel.add(profileBtn);
        topPanel.add(voirPanierBtn);
        mainPanel.add(topPanel, BorderLayout.NORTH);

        // Charger les attractions depuis la base de données
        AttractionDAOImpl attractionDAO = new AttractionDAOImpl();  // Créez une instance de AttractionDAOImpl
        attractions = attractionDAO.getAllAttractions();  // Appelez la méthode sur l'instance

        // Panneau de grille pour les attractions
        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(0, 1, 10, 10));
        gridPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        gridPanel.setBackground(new Color(240, 248, 255));

        if (attractions.isEmpty()) {
            JLabel noAttractionLabel = new JLabel("Aucune attraction disponible.");
            noAttractionLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));
            noAttractionLabel.setForeground(Color.RED);
            mainPanel.add(noAttractionLabel, BorderLayout.CENTER);
        } else {
            // Pour chaque attraction, crée une carte d'affichage
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

                // Calcul des prix pour chaque profil
                double prixBase = attraction.getPrixBase();
                double prixAdulte = prixBase * (1 - client.calculerReduction());
                double prixEnfant = prixBase * (1 - client.calculerReduction()) * 0.5;
                double prixSenior = prixBase * (1 - client.calculerReduction()) * 0.7;

                selectionPanel.add(new JLabel("Prix Adulte: " + String.format("%.2f", prixAdulte) + " €"));
                selectionPanel.add(new JLabel("Prix Enfant: " + String.format("%.2f", prixEnfant) + " €"));
                selectionPanel.add(new JLabel("Prix Senior: " + String.format("%.2f", prixSenior) + " €"));

                // Spinners pour les quantités
                JSpinner spinnerAdulte = new JSpinner(new SpinnerNumberModel(0, 0, 10, 1));
                selectionPanel.add(new JLabel("Adultes : "));
                selectionPanel.add(spinnerAdulte);

                JSpinner spinnerEnfant = new JSpinner(new SpinnerNumberModel(0, 0, 10, 1));
                selectionPanel.add(new JLabel("Enfants : "));
                selectionPanel.add(spinnerEnfant);

                JSpinner spinnerSenior = new JSpinner(new SpinnerNumberModel(0, 0, 10, 1));
                selectionPanel.add(new JLabel("Seniors : "));
                selectionPanel.add(spinnerSenior);

                // Checkbox pour Fast Pass
                JCheckBox fastPassCheckBox = new JCheckBox("Ajouter Fast Pass");
                fastPassCheckBox.setBackground(Color.WHITE);
                selectionPanel.add(fastPassCheckBox);

                // Bouton d'ajout au panier
                JButton addBtn = new JButton("Ajouter au panier");
                addBtn.setBackground(new Color(0, 150, 100));
                addBtn.setForeground(Color.WHITE);

                addBtn.addActionListener(e -> {
                    boolean fastPassSelected = fastPassCheckBox.isSelected();
                    double prixFinal = client.calculerPrix(attraction.getPrixBase());  // Calcul du prix de base

                    if (fastPassSelected) {
                        prixFinal += prixFinal * 0.6;  // 60% de supplément pour Fast Pass
                    }

                    // Ajouter les tickets au panier
                    for (int i = 0; i < (int) spinnerAdulte.getValue(); i++) {
                        panier.add(new Ticket(attraction, Profil.REGULIER, prixFinal, fastPassSelected));
                    }
                    for (int i = 0; i < (int) spinnerEnfant.getValue(); i++) {
                        panier.add(new Ticket(attraction, Profil.ENFANT, prixFinal, fastPassSelected));
                    }
                    for (int i = 0; i < (int) spinnerSenior.getValue(); i++) {
                        panier.add(new Ticket(attraction, Profil.SENIOR, prixFinal, fastPassSelected));
                    }
                });

                // Ajouter le panneau de sélection et le bouton au sud de la carte
                JPanel southPanel = new JPanel(new BorderLayout());
                southPanel.setBackground(Color.WHITE);
                southPanel.add(selectionPanel, BorderLayout.CENTER);
                southPanel.add(addBtn, BorderLayout.EAST);

                // Ajouter la carte complète au panneau principal
                card.add(title, BorderLayout.NORTH);
                card.add(desc, BorderLayout.CENTER);
                card.add(southPanel, BorderLayout.SOUTH);
                gridPanel.add(card);
            }
        }

        JPanel bottom = new JPanel();
        bottom.setBackground(new Color(240, 248, 255));

        prixLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        bottom.add(prixLabel);

        mainPanel.add(gridPanel, BorderLayout.CENTER);
        mainPanel.add(bottom, BorderLayout.SOUTH);

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
