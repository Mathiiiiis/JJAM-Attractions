package view;

import model.Client;

import javax.swing.*;
import java.awt.*;

public class ClientDashboardGUI extends JFrame {
    private Client client;
    private CardLayout cardLayout;
    private JPanel cardPanel;

    public ClientDashboardGUI(Client client) {
        this.client = client;
        initUI();
    }

    private void initUI() {
        setTitle("Espace Client - Bienvenue " + client.getNom());
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());

        // 📚 Barre de menu latérale gauche
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setBackground(new Color(230, 240, 255));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        JButton reservationBtn = createStyledButton("🎟️ Réserver");
        JButton abonnementBtn = createStyledButton("📜 Mes Abonnements");
        JButton profilBtn = createStyledButton("👤 Mon Profil");
        JButton historiqueBtn = createStyledButton("📅 Mes Réservations");
        JButton goldenRoueBtn = createStyledButton("🎡 Golden Roue");
        JButton logoutBtn = createStyledButton("🚪 Déconnexion");

        menuPanel.add(reservationBtn);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        menuPanel.add(abonnementBtn);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        menuPanel.add(profilBtn);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        menuPanel.add(historiqueBtn);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        menuPanel.add(goldenRoueBtn);
        menuPanel.add(Box.createVerticalGlue());
        menuPanel.add(logoutBtn);

        // 📜 CardLayout pour changer d'écran au centre
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // 🌟 Ecran d'accueil par défaut
        JPanel accueilPanel = new JPanel();
        accueilPanel.setLayout(new BoxLayout(accueilPanel, BoxLayout.Y_AXIS));
        accueilPanel.setBackground(Color.WHITE);
        accueilPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        JLabel bienvenueLabel = new JLabel("✨ Bienvenue, " + client.getNom() + " ✨", SwingConstants.CENTER);
        bienvenueLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        bienvenueLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel messageLabel = new JLabel("Profitez de nos attractions exceptionnelles toute l'année !", SwingConstants.CENTER);
        messageLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel infoPanel = new JPanel(new GridLayout(1, 3, 20, 20));
        infoPanel.setMaximumSize(new Dimension(800, 150));
        infoPanel.setBackground(Color.WHITE);

        infoPanel.add(createInfoCard("🔥 Promotions", "Découvrez nos offres exclusives !"));
        infoPanel.add(createInfoCard("🎢 Attractions phares", "Vivez des sensations fortes inoubliables."));
        infoPanel.add(createInfoCard("🏆 Statut fidélité", "Cumulez des avantages en réservant !"));

        accueilPanel.add(bienvenueLabel);
        accueilPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        accueilPanel.add(messageLabel);
        accueilPanel.add(Box.createRigidArea(new Dimension(0, 40)));
        accueilPanel.add(infoPanel);

        cardPanel.add(accueilPanel, "Accueil");
        cardPanel.add(new MesAbonnementsPanel(client), "Abonnements");
        cardPanel.add(new ProfileGUI(client), "Profil");
        cardPanel.add(new HistoriqueGUI(client), "Historique");
        cardPanel.add(new GoldenRoueGUI(client), "GoldenRoue");

        mainPanel.add(menuPanel, BorderLayout.WEST);
        mainPanel.add(cardPanel, BorderLayout.CENTER);

        add(mainPanel);

        // 🎯 Actions
        reservationBtn.addActionListener(e -> new ChoixParcGUI(client).setVisible(true));
        abonnementBtn.addActionListener(e -> cardLayout.show(cardPanel, "Abonnements"));
        profilBtn.addActionListener(e -> cardLayout.show(cardPanel, "Profil"));
        historiqueBtn.addActionListener(e -> cardLayout.show(cardPanel, "Historique"));
        goldenRoueBtn.addActionListener(e -> cardLayout.show(cardPanel, "GoldenRoue"));
        logoutBtn.addActionListener(e -> {
            new AccueilGUI().setVisible(true);
            dispose();
        });

        // Affiche l'accueil au démarrage
        cardLayout.show(cardPanel, "Accueil");

        setVisible(true);
    }

    private JPanel createInfoCard(String title, String description) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(240, 248, 255));
        panel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 2));
        panel.setPreferredSize(new Dimension(200, 150));

        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel descLabel = new JLabel("<html><center>" + description + "</center></html>", SwingConstants.CENTER);
        descLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        descLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(titleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(descLabel);

        return panel;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setBackground(new Color(0, 120, 215));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("SansSerif", Font.BOLD, 16));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(200, 40));
        return button;
    }
}
