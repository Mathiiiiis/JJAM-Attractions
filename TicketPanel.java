package view;

import model.Client;

import javax.swing.*;
import java.awt.*;

public class TicketPanel extends JPanel {
    private JLabel nomLabel;
    private JLabel profilLabel;
    private JLabel prixLabel;
    private JLabel typeBilletLabel; // 🟢 Nouveau label pour "Billet connecté" ou "Billet invité"
    private JCheckBox fastPassCheckbox;
    private double basePrix;
    private Client client;

    public TicketPanel(String nom, String profil, double prix) {
        this(nom, profil, prix, false, null);
    }

    public TicketPanel(String nom, String profil, double prix, boolean fastPassSelected) {
        this(nom, profil, prix, fastPassSelected, null);
    }

    public TicketPanel(String nom, String profil, double prix, boolean fastPassSelected, Client client) {
        this.basePrix = prix;
        this.client = client;

        setLayout(new GridLayout(5, 1, 5, 5)); // ➔ 5 lignes maintenant
        setBorder(BorderFactory.createLineBorder(Color.GRAY));
        setPreferredSize(new Dimension(200, 180)); // ➔ Un peu plus grand pour accueillir le nouveau label

        nomLabel = new JLabel("👤 " + nom);
        profilLabel = new JLabel("🎫 Profil : " + profil);
        prixLabel = new JLabel("💰 Prix : " + String.format("%.2f", prix) + " €");
        fastPassCheckbox = new JCheckBox("🚀 FastPass (+60%)");
        fastPassCheckbox.setSelected(fastPassSelected);

        // 🔥 Nouveau label de type de billet
        if (client != null) {
            typeBilletLabel = new JLabel("✅ Billet connecté", SwingConstants.CENTER);
            typeBilletLabel.setForeground(new Color(0, 128, 0)); // Vert
        } else {
            typeBilletLabel = new JLabel("🎟️ Billet invité", SwingConstants.CENTER);
            typeBilletLabel.setForeground(Color.GRAY);
        }

        fastPassCheckbox.addActionListener(e -> updatePrix());

        add(nomLabel);
        add(profilLabel);
        add(prixLabel);
        add(fastPassCheckbox);
        add(typeBilletLabel);
    }

    private void updatePrix() {
        double prix = basePrix;
        if (fastPassCheckbox.isSelected()) {
            prix += prix * 0.6;
        }
        prixLabel.setText("💰 Prix : " + String.format("%.2f", prix) + " €");
    }

    public boolean isFastPassSelected() {
        return fastPassCheckbox.isSelected();
    }

    public double getPrixFinal() {
        return basePrix * (fastPassCheckbox.isSelected() ? 1.6 : 1);
    }

    public String getNom() {
        return nomLabel.getText().replace("👤 ", "").trim();
    }

    public String getProfil() {
        return profilLabel.getText().replace("🎫 Profil : ", "").trim();
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
        updateTypeBillet(); // Mise à jour dynamique si besoin
    }

    // 🔥 Méthode pour mettre à jour l'étiquette si jamais on assigne un Client après
    private void updateTypeBillet() {
        if (typeBilletLabel != null) {
            if (client != null) {
                typeBilletLabel.setText("✅ Billet connecté");
                typeBilletLabel.setForeground(new Color(0, 128, 0));
            } else {
                typeBilletLabel.setText("🎟️ Billet invité");
                typeBilletLabel.setForeground(Color.GRAY);
            }
        }
    }
}
