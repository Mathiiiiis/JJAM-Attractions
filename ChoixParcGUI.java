package view;

import controller.ReservationController;
import dao.ParcDAOInterface;
import dao.ParcDAOImpl;
import model.Parc;
import model.Attraction;
import model.Client;
import utils.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class ChoixParcGUI extends JFrame {
    private JPanel mainPanel;
    private JButton continuerButton;
    private JButton retourButton; // 🆕 Nouveau bouton retour
    private JPanel parcsPanel;
    private List<Parc> parcsSelectionnes = new ArrayList<>();
    private JLabel messagePromoLabel;

    private Client clientConnecte;

    public ChoixParcGUI(Client client) {
        this.clientConnecte = client;
        setTitle("Sélection du Parc");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 700);
        setLocationRelativeTo(null);

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 245, 245));

        // Partie du haut : explication
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBackground(new Color(245, 245, 245));

        JLabel introLabel = new JLabel("<html><center><b>Sélectionnez 1 ou plusieurs parcs.</b><br>Plus vous choisissez de parcs, plus vous bénéficiez d'une remise :</center></html>", SwingConstants.CENTER);
        introLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        introLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        messagePromoLabel = new JLabel("Sélectionnez vos parcs pour profiter des offres spéciales !", SwingConstants.CENTER);
        messagePromoLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        messagePromoLabel.setForeground(new Color(0, 102, 204));
        messagePromoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        topPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        topPanel.add(introLabel);
        topPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        topPanel.add(messagePromoLabel);
        topPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        mainPanel.add(topPanel, BorderLayout.NORTH);

        parcsPanel = new JPanel();
        parcsPanel.setLayout(new BoxLayout(parcsPanel, BoxLayout.Y_AXIS));
        parcsPanel.setBackground(new Color(245, 245, 245));
        mainPanel.add(new JScrollPane(parcsPanel), BorderLayout.CENTER);

        // Panel pour les boutons "Continuer" et "Retour"
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonsPanel.setBackground(new Color(245, 245, 245));

        continuerButton = new JButton("Continuer");
        continuerButton.setEnabled(false);

        retourButton = new JButton("Retour"); // 🆕 Bouton retour

        buttonsPanel.add(retourButton);
        buttonsPanel.add(continuerButton);

        mainPanel.add(buttonsPanel, BorderLayout.SOUTH);

        add(mainPanel);

        loadParcs();

        continuerButton.addActionListener(e -> {
            if (!parcsSelectionnes.isEmpty()) {
                new ChoixBilletsGUI(parcsSelectionnes, clientConnecte);
                dispose();
            }
        });

        retourButton.addActionListener(e -> {
            new ClientDashboardGUI(clientConnecte).setVisible(true);
            dispose();
        });

        setVisible(true);
    }

    private void loadParcs() {
        try (Connection conn = DBConnection.getConnection()) {
            ParcDAOInterface parcDAO = new ParcDAOImpl(conn);
            List<Parc> parcs = parcDAO.getAllParcsWithAttractions();

            for (Parc parc : parcs) {
                JPanel parcPanel = createParcPanel(parc);
                parcsPanel.add(parcPanel);
                parcsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            }

            revalidate();
            repaint();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur lors du chargement des parcs", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JPanel createParcPanel(Parc parc) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        panel.setMaximumSize(new Dimension(700, 120));
        panel.setBackground(Color.WHITE);

        JCheckBox checkBox = new JCheckBox(parc.getNom() + " - Prix d'entrée : " + parc.getPrixEntree() + " €");
        checkBox.setBackground(Color.WHITE);
        panel.add(checkBox, BorderLayout.NORTH);

        JTextArea attractionsArea = new JTextArea();
        attractionsArea.setEditable(false);
        attractionsArea.setBackground(Color.WHITE);
        StringBuilder sb = new StringBuilder();
        for (Attraction attraction : parc.getAttractions()) {
            sb.append("- ").append(attraction.getNom()).append("\n");
        }
        attractionsArea.setText(sb.toString());
        JScrollPane scrollPane = new JScrollPane(attractionsArea);
        scrollPane.setBackground(Color.WHITE);
        panel.add(scrollPane, BorderLayout.CENTER);

        checkBox.addActionListener(e -> {
            if (checkBox.isSelected()) {
                parcsSelectionnes.add(parc);
            } else {
                parcsSelectionnes.remove(parc);
            }
            continuerButton.setEnabled(!parcsSelectionnes.isEmpty());
            updateSelectionVisual();
            updateMessagePromo();
        });

        return panel;
    }

    private void updateSelectionVisual() {
        for (Component comp : parcsPanel.getComponents()) {
            if (comp instanceof JPanel panel) {
                boolean isSelected = false;
                for (Component inner : panel.getComponents()) {
                    if (inner instanceof JCheckBox checkBox) {
                        isSelected = checkBox.isSelected();
                        break;
                    }
                }
                if (isSelected) {
                    panel.setBackground(new Color(200, 230, 255)); // Bleu clair sélection
                    panel.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 204), 3));
                } else {
                    panel.setBackground(Color.WHITE);
                    panel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
                }
                for (Component inner : panel.getComponents()) {
                    inner.setBackground(panel.getBackground());
                }
            }
        }
    }

    private void updateMessagePromo() {
        int count = parcsSelectionnes.size();
        switch (count) {
            case 1 -> messagePromoLabel.setText("Ajoutez 1 parc et obtenez 10% de remise !");
            case 2 -> messagePromoLabel.setText("Ajoutez 1 parc et obtenez 30% de remise !");
            case 3 -> messagePromoLabel.setText("Ajoutez 1 parc et obtenez 40% de remise !");
            case 4, 5, 6 -> messagePromoLabel.setText("Vous économisez 40% sur le prix !");
            default -> messagePromoLabel.setText("Sélectionnez vos parcs pour profiter des offres spéciales !");
        }
    }
}
