package view;

import dao.ParcDAOInterface;
import dao.ParcDAOImpl;
import model.Client;
import model.Parc;
import utils.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class ReservationStartGUI extends JFrame {
    private Client client;
    private List<Parc> tousLesParcs = new ArrayList<>();
    private List<JCheckBox> checkBoxes = new ArrayList<>();
    private JLabel prixLabel;
    private JLabel messagePromoLabel;

    public ReservationStartGUI(Client client) {
        this.client = client;
        initWindow();
    }

    private void initWindow() {
        setTitle("Choix des Parcs");
        setSize(800, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // 🆕 Panneau du haut avec explication
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));

        JLabel intro = new JLabel("<html><center><b>Sélectionnez 1 ou plusieurs parcs.</b><br>Plus vous choisissez de parcs, plus vous bénéficiez d'une remise sur le <b>prix total du ticket</b> !</center></html>", SwingConstants.CENTER);
        intro.setFont(new Font("SansSerif", Font.PLAIN, 16));
        intro.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel explication = new JLabel("<html><center>🎟️ <b>Tarifs spéciaux :</b><br>- 2 parcs = 10% de réduction sur le prix du ticket<br>- 3 parcs = 30% de réduction sur le prix du ticket<br>- 4 parcs = 40% de réduction sur le prix du ticket</center></html>", SwingConstants.CENTER);
        explication.setFont(new Font("SansSerif", Font.PLAIN, 14));
        explication.setAlignmentX(Component.CENTER_ALIGNMENT);

        topPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        topPanel.add(intro);
        topPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        topPanel.add(explication);
        topPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        add(topPanel, BorderLayout.NORTH);

        // 📋 Partie centrale : liste des parcs
        JPanel parcsPanel = new JPanel(new GridLayout(0, 2, 10, 10));

        try (Connection conn = DBConnection.getConnection()) {
            ParcDAOInterface parcDAO = new ParcDAOImpl(conn);
            tousLesParcs = parcDAO.getAllParcs();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur lors du chargement des parcs", "Erreur", JOptionPane.ERROR_MESSAGE);
        }

        for (Parc parc : tousLesParcs) {
            JCheckBox box = new JCheckBox(parc.getNom() + " (" + parc.getPrixEntree() + " €)");
            checkBoxes.add(box);
            parcsPanel.add(box);

            box.addItemListener(e -> updatePrixEtMessage());
        }

        JScrollPane scrollPane = new JScrollPane(parcsPanel);
        add(scrollPane, BorderLayout.CENTER);

        // 🎯 Partie basse : prix total, message promo, bouton suivant
        JPanel bottom = new JPanel(new GridLayout(3, 1));

        prixLabel = new JLabel("Prix Total: 0€", SwingConstants.CENTER);
        prixLabel.setFont(new Font("SansSerif", Font.BOLD, 16));

        messagePromoLabel = new JLabel("Sélectionnez vos parcs pour voir les offres spéciales sur le prix du ticket !", SwingConstants.CENTER);
        messagePromoLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        messagePromoLabel.setForeground(new Color(0, 102, 204)); // Bleu doux

        JButton suivant = new JButton("Suivant ➡️");
        suivant.addActionListener(e -> {
            List<Parc> selection = new ArrayList<>();
            for (int i = 0; i < checkBoxes.size(); i++) {
                if (checkBoxes.get(i).isSelected()) {
                    selection.add(tousLesParcs.get(i));
                }
            }
            if (selection.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner au moins un parc !");
            } else {
                new ChoixBilletsGUI(selection, client);
                dispose();
            }
        });

        bottom.add(prixLabel);
        bottom.add(messagePromoLabel);
        bottom.add(suivant);

        add(bottom, BorderLayout.SOUTH);

        updatePrixEtMessage();
        setVisible(true);
    }

    private void updatePrixEtMessage() {
        double total = 0;
        int count = 0;

        for (int i = 0; i < checkBoxes.size(); i++) {
            if (checkBoxes.get(i).isSelected()) {
                total += tousLesParcs.get(i).getPrixEntree();
                count++;
            }
        }

        // ✅ Application des remises
        if (count == 2) total *= 0.9;
        if (count == 3) total *= 0.7;
        if (count == 4) total *= 0.6;

        prixLabel.setText("Prix Total: " + String.format("%.2f", total) + " €");

        // ✅ Message dynamique
        switch (count) {
            case 1 -> messagePromoLabel.setText("Ajoutez 1 parc et obtenez 10% de remise sur le prix du ticket !");
            case 2 -> messagePromoLabel.setText("Ajoutez 1 parc et obtenez 30% de remise sur le prix du ticket !");
            case 3 -> messagePromoLabel.setText("Ajoutez 1 parc et obtenez 40% de remise sur le prix du ticket !");
            default -> messagePromoLabel.setText("Sélectionnez vos parcs pour voir les offres spéciales sur le prix du ticket !");
        }
    }
}
