package view;

import model.Parc;
import model.Profil;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ChoixBilletsInviteGUI extends JFrame {
    private List<Parc> parcsSelectionnes;
    private JPanel billetPanelContainer;
    private List<TicketPanel> billets = new ArrayList<>();

    public ChoixBilletsInviteGUI(List<Parc> parcsSelectionnes) {
        this.parcsSelectionnes = parcsSelectionnes;

        setTitle("Mode Invité - Choix des Billets");
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        JLabel titre = new JLabel("<html><center>Vous avez choisi : " + parcsSelectionnes.size() + " parc(s)<br>Ajoutez vos billets invités !</center></html>", SwingConstants.CENTER);
        titre.setFont(new Font("SansSerif", Font.BOLD, 20));
        add(titre, BorderLayout.NORTH);

        billetPanelContainer = new JPanel();
        billetPanelContainer.setLayout(new GridLayout(0, 3, 10, 10));
        JScrollPane scrollPane = new JScrollPane(billetPanelContainer);
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));

        JButton ajouterBilletBtn = new JButton("➕ Ajouter un billet");
        ajouterBilletBtn.addActionListener(e -> ouvrirAjoutBillet());

        JButton validerBtn = new JButton("Continuer ➡️");
        validerBtn.addActionListener(e -> {
            if (billets.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ajoutez au moins un billet !");
            } else {
                new PageCalendrier(parcsSelectionnes, billets.size(), billets, null, null).setVisible(true);
                dispose();
            }
        });

        JButton retourBtn = new JButton("⬅️ Retour");
        retourBtn.addActionListener(e -> {
            new ChoixParcInviteGUI().setVisible(true);
            dispose();
        });

        bottomPanel.add(retourBtn);
        bottomPanel.add(ajouterBilletBtn);
        bottomPanel.add(validerBtn);

        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void ouvrirAjoutBillet() {
        JTextField nomField = new JTextField();
        String[] profils = {"REGULIER", "ENFANT", "SENIOR"};
        JComboBox<String> profilBox = new JComboBox<>(profils);

        Object[] fields = {
                "Nom de l'invité:", nomField,
                "Profil:", profilBox
        };

        int result = JOptionPane.showConfirmDialog(this, fields, "Ajouter un Billet Invité", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            String nom = nomField.getText();
            if (nom.isEmpty()) {
                nom = "Invité";
            }
            String profil = (String) profilBox.getSelectedItem();

            double base = 0;
            for (Parc parc : parcsSelectionnes) {
                base += parc.getPrixEntree();
            }

            TicketPanel billet = new TicketPanel(nom, profil, base, false);

            billets.add(billet);
            billetPanelContainer.add(billet);
            billetPanelContainer.revalidate();
            billetPanelContainer.repaint();
        }
    }

    public List<TicketPanel> getBillets() {
        return billets;
    }
}
