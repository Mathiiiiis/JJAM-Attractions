package view;

import dao.ClientDAO;
import model.Client;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class MesAbonnementsPanel extends JPanel {
    private Client client;
    private ClientDAO clientDAO;
    private JLabel abonnementActuelLabel;

    private class Offre {
        String nom;
        String prix;
        double prixMontant;
        List<String> avantages;

        Offre(String nom, String prix, double prixMontant, List<String> avantages) {
            this.nom = nom;
            this.prix = prix;
            this.prixMontant = prixMontant;
            this.avantages = avantages;
        }
    }

    private List<Offre> offres = Arrays.asList(
            new Offre("VIP", "79.99€/an", 79.99, Arrays.asList(
                    "Prix divisé par 2 sur toutes les réservations",
                    "Visite des backrooms offerte",
                    "Accès aux salons lounge VIP"
            )),
            new Offre("SUPER_VITESSE", "49.99€/an", 49.99, Arrays.asList(
                    "Accès direct aux attractions",
                    "Fast Pass gratuit pour toutes les réservations"
            )),
            new Offre("PASS_ANNUEL", "249.99€/an", 249.99, Arrays.asList(
                    "Accès illimité aux parcs toute l'année",
                    "Billets gratuits pour toutes les réservations",
                    "Accès aux salons lounge VIP"
            ))
    );

    public MesAbonnementsPanel(Client client) {
        this.client = client;
        this.clientDAO = new ClientDAO();

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        initUI();
    }

    private void initUI() {
        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));

        JLabel titre = new JLabel("🎟️ Mon abonnement actuel :", SwingConstants.CENTER);
        titre.setFont(new Font("SansSerif", Font.BOLD, 22));
        titre.setAlignmentX(Component.CENTER_ALIGNMENT);
        header.add(titre);

        abonnementActuelLabel = new JLabel();
        abonnementActuelLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        abonnementActuelLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        abonnementActuelLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        updateAbonnementLabel();
        header.add(abonnementActuelLabel);

        add(header, BorderLayout.NORTH);

        JPanel offresPanel = new JPanel();
        offresPanel.setLayout(new GridLayout(0, 1, 15, 15));

        for (Offre offre : offres) {
            offresPanel.add(creerCarteOffre(offre));
        }

        JScrollPane scrollPane = new JScrollPane(offresPanel);
        scrollPane.setBorder(null);
        add(scrollPane, BorderLayout.CENTER);
    }

    private JPanel creerCarteOffre(Offre offre) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                offre.nom,
                TitledBorder.CENTER,
                TitledBorder.TOP,
                new Font("SansSerif", Font.BOLD, 18)
        ));

        // ➔ Mise en valeur visuelle de l'abonnement actif
        if (offre.nom.equals(client.getAbonnement())) {
            card.setBackground(new Color(200, 230, 255)); // Bleu très léger pour surligner
        } else {
            card.setBackground(Color.WHITE);
        }
        card.setOpaque(true);

        JLabel prixLabel = new JLabel(offre.prix, SwingConstants.CENTER);
        prixLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        prixLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(prixLabel);

        card.add(Box.createRigidArea(new Dimension(0, 10)));

        for (String avantage : offre.avantages) {
            JLabel avantageLabel = new JLabel("• " + avantage);
            avantageLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
            card.add(avantageLabel);
        }

        card.add(Box.createRigidArea(new Dimension(0, 10)));

        JButton souscrireBtn = new JButton("Souscrire à " + offre.nom);
        souscrireBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        souscrireBtn.addActionListener(e -> lancerPaiementEtSouscrire(offre.nom, offre.prixMontant));
        card.add(souscrireBtn);

        return card;
    }

    private void updateAbonnementLabel() {
        if (client.getAbonnement() == null) {
            abonnementActuelLabel.setText("📜 Aucun abonnement en cours.");
        } else {
            abonnementActuelLabel.setText("📜 " + client.getAbonnement());
        }
    }

    private void lancerPaiementEtSouscrire(String offre, double montant) {
        if (offre.equals(client.getAbonnement())) {
            JOptionPane.showMessageDialog(this, "⚠️ Vous avez déjà souscrit à cet abonnement.", "Abonnement existant", JOptionPane.WARNING_MESSAGE);
            return;
        }

        new EcranPaiementGUI(() -> {
            client.setAbonnement(offre);
            clientDAO.updateAbonnement(client);
            updateAbonnementLabel();
            // Refresh automatique visuel après souscription
            removeAll();
            initUI();
            revalidate();
            repaint();
            JOptionPane.showMessageDialog(this, "✅ Abonnement " + offre + " activé avec succès !");
        }, montant).setVisible(true);
    }
}
