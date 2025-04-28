package view;

import dao.AttractionDAOImpl;
import model.Attraction;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GuestReservationGUI extends JPanel {

    public GuestReservationGUI() {
        setLayout(new BorderLayout());

        JLabel title = new JLabel("🎡 Mode Invité - Attractions Disponibles", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 26));
        title.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        add(title, BorderLayout.NORTH);

        JPanel attractionPanel = new JPanel(new GridLayout(0, 1, 10, 10));
        attractionPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        try {
            AttractionDAOImpl attractionDAO = new AttractionDAOImpl();
            List<Attraction> attractions = attractionDAO.getAllAttractions();

            if (attractions.isEmpty()) {
                JLabel noData = new JLabel("Aucune attraction disponible actuellement.", SwingConstants.CENTER);
                noData.setFont(new Font("SansSerif", Font.PLAIN, 18));
                attractionPanel.add(noData);
            } else {
                for (Attraction attraction : attractions) {
                    JPanel card = new JPanel(new BorderLayout());
                    card.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
                    card.setBackground(new Color(245, 245, 245));

                    JLabel name = new JLabel("🎢 " + attraction.getNom() + " - Parc: " + attraction.getParc());
                    name.setFont(new Font("SansSerif", Font.BOLD, 18));
                    card.add(name, BorderLayout.NORTH);

                    JTextArea description = new JTextArea(attraction.getDescription() != null ? attraction.getDescription() : "Pas de description disponible.");
                    description.setWrapStyleWord(true);
                    description.setLineWrap(true);
                    description.setEditable(false);
                    description.setBackground(new Color(245, 245, 245));
                    card.add(description, BorderLayout.CENTER);

                    JLabel prix = new JLabel("Prix de base : " + String.format("%.2f", attraction.getPrixBase()) + " €");
                    prix.setFont(new Font("SansSerif", Font.PLAIN, 16));
                    card.add(prix, BorderLayout.SOUTH);

                    attractionPanel.add(card);
                }
            }
        } catch (Exception e) {
            JLabel errorLabel = new JLabel("Erreur lors du chargement des attractions.", SwingConstants.CENTER);
            errorLabel.setForeground(Color.RED);
            attractionPanel.add(errorLabel);
            e.printStackTrace();
        }

        JScrollPane scrollPane = new JScrollPane(attractionPanel);
        add(scrollPane, BorderLayout.CENTER);

        // Bouton pour inciter à se connecter
        JButton loginButton = new JButton("🔐 Se connecter pour réserver");
        loginButton.setFont(new Font("SansSerif", Font.BOLD, 18));
        loginButton.setBackground(new Color(0, 150, 136));
        loginButton.setForeground(Color.WHITE);
        loginButton.addActionListener(e -> {
            new ConnexionGUI().setVisible(true);
            SwingUtilities.getWindowAncestor(this).dispose();
        });

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(loginButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }
}
