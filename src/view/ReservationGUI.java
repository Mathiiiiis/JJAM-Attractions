package view;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import dao.AttractionDAOImpl;
import model.Attraction;
import model.Client;
import controller.ReservationController;
import model.Profil;
import view.ConfirmationGUI.Ticket;
import view.ConfirmationGUI;

public class ReservationGUI extends JFrame {
    private final List<ConfirmationGUI.Ticket> panier = new ArrayList<>();
    private final JLabel prixLabel = new JLabel("Total Panier : 0.00 €");
    private final List<Attraction> attractions;

    public ReservationGUI(Client client, List<ConfirmationGUI.Ticket> panier) {
        super("Réservation d'Attractions");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        this.panier.addAll(panier);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(240, 248, 255));

        // Panneau du haut pour le bouton de voir le panier
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topPanel.setOpaque(false);

        JButton voirPanierBtn = new JButton("📦 Voir le panier");
        voirPanierBtn.setBackground(new Color(200, 200, 0));
        voirPanierBtn.setFont(new Font("SansSerif", Font.PLAIN, 14));
        voirPanierBtn.addActionListener(e -> {
            double prixTotal = panier.stream()
                    .mapToDouble(t -> new ReservationController().calculerPrix(t.getAttraction(), t.getProfil(), java.time.LocalDate.now(), false))
                    .sum();
            new ConfirmationGUI(client, panier, prixTotal).setVisible(true);
            dispose();  // Fermer la page actuelle
        });

        topPanel.add(voirPanierBtn);
        mainPanel.add(topPanel, BorderLayout.NORTH);

        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(0, 1, 10, 10));
        gridPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        gridPanel.setBackground(new Color(240, 248, 255));

        // Charger les attractions depuis la base de données
        AttractionDAOImpl attractionDAO = new AttractionDAOImpl();
        attractions = attractionDAO.getAllAttractions();

        // Affichage des attractions avec possibilité de les ajouter au panier
        for (Attraction attraction : attractions) {
            JPanel card = new JPanel();
            card.setLayout(new BorderLayout());
            card.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
            card.setBackground(Color.WHITE);

            JLabel title = new JLabel("🎢 " + attraction.getNom() + " - Parc: " + attraction.getParc());
            title.setFont(new Font("SansSerif", Font.BOLD, 14));

            JTextArea desc = new JTextArea(attraction.getDescription() != null ? attraction.getDescription() : "Description non disponible.");
            desc.setEditable(false);
            desc.setLineWrap(true);
            desc.setWrapStyleWord(true);
            desc.setBackground(Color.WHITE);

            JLabel prix = new JLabel("Prix adulte : " + attraction.getPrixBase() + "€ | Enfant : " + (attraction.getPrixBase() * 0.5) + "€ | Senior : " + (attraction.getPrixBase() * 0.7) + "€");

            JPanel selectionPanel = new JPanel();
            selectionPanel.setBackground(Color.WHITE);

            JSpinner spinnerAdulte = new JSpinner(new SpinnerNumberModel(0, 0, 10, 1));
            JSpinner spinnerEnfant = new JSpinner(new SpinnerNumberModel(0, 0, 10, 1));
            JSpinner spinnerSenior = new JSpinner(new SpinnerNumberModel(0, 0, 10, 1));

            selectionPanel.add(new JLabel("Adultes : "));
            selectionPanel.add(spinnerAdulte);
            selectionPanel.add(new JLabel("Enfants : "));
            selectionPanel.add(spinnerEnfant);
            selectionPanel.add(new JLabel("Seniors : "));
            selectionPanel.add(spinnerSenior);

            JButton addBtn = new JButton("Ajouter au panier");
            addBtn.setBackground(new Color(0, 150, 100));
            addBtn.setForeground(Color.WHITE);
            addBtn.addActionListener(e -> {
                for (int i = 0; i < (int) spinnerAdulte.getValue(); i++)
                    panier.add(new ConfirmationGUI.Ticket(attraction, Profil.REGULIER));
                for (int i = 0; i < (int) spinnerEnfant.getValue(); i++)
                    panier.add(new ConfirmationGUI.Ticket(attraction, Profil.ENFANT));
                for (int i = 0; i < (int) spinnerSenior.getValue(); i++)
                    panier.add(new ConfirmationGUI.Ticket(attraction, Profil.SENIOR));
                updatePrixTotal(client);
            });

            JPanel southPanel = new JPanel(new BorderLayout());
            southPanel.setBackground(Color.WHITE);
            southPanel.add(prix, BorderLayout.NORTH);
            southPanel.add(selectionPanel, BorderLayout.CENTER);
            southPanel.add(addBtn, BorderLayout.EAST);

            card.add(title, BorderLayout.NORTH);
            card.add(desc, BorderLayout.CENTER);
            card.add(southPanel, BorderLayout.SOUTH);
            gridPanel.add(card);
        }

        JPanel bottom = new JPanel();
        bottom.setBackground(new Color(240, 248, 255));
        prixLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        bottom.add(prixLabel);

        mainPanel.add(gridPanel, BorderLayout.CENTER);
        mainPanel.add(bottom, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void updatePrixTotal(Client client) {
        double total = panier.stream()
                .mapToDouble(t -> new ReservationController().calculerPrix(t.getAttraction(), t.getProfil(), java.time.LocalDate.now(), false))
                .sum();
        prixLabel.setText("Total Panier : " + String.format("%.2f", total) + " €");
    }
}
