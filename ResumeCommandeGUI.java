package view;

import dao.ReservationDAO;
import model.Client;
import model.Parc;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class ResumeCommandeGUI extends JFrame {
    private Client client;
    private List<TicketPanel> billets;
    private List<Parc> parcsChoisis;
    private LocalDate dateChoisie;

    public ResumeCommandeGUI(Client client, List<TicketPanel> billets, List<Parc> parcsChoisis, LocalDate dateChoisie) {
        this.client = client;
        this.billets = billets;
        this.parcsChoisis = parcsChoisis;
        this.dateChoisie = dateChoisie;

        setTitle("Résumé de la Commande");
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initUI();
        setVisible(true);
    }

    private void initUI() {
        JPanel mainPanel = new JPanel(new GridLayout(0, 3, 10, 10));
        mainPanel.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        add(scrollPane, BorderLayout.CENTER);

        double totalGlobal = 0;

        for (TicketPanel billet : billets) {
            JPanel ticket = creerTicketCaisse(billet);
            mainPanel.add(ticket);
            totalGlobal += calculerPrixBillet(billet);
        }

        JLabel totalLabel = new JLabel("\uD83D\uDCB3 TOTAL À PAYER : " + String.format("%.2f", totalGlobal) + " €", SwingConstants.CENTER);
        totalLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        totalLabel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        add(totalLabel, BorderLayout.NORTH);

        JButton paiementBtn = new JButton("Accéder au paiement ➡️");
        paiementBtn.setFont(new Font("SansSerif", Font.BOLD, 18));
        paiementBtn.setBackground(new Color(0, 153, 0));
        paiementBtn.setForeground(Color.WHITE);
        paiementBtn.setFocusPainted(false);

        final double totalFinal = totalGlobal;

        paiementBtn.addActionListener(e -> {
            new EcranPaiementGUI(() -> {
                new PageConfirmationReservationGUI(client, billets, parcsChoisis, totalFinal, dateChoisie).setVisible(true);
            }, totalFinal).setVisible(true);
            dispose();
        });

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.add(paiementBtn);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private JPanel creerTicketCaisse(TicketPanel billet) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panel.setBackground(Color.WHITE);
        panel.setPreferredSize(new Dimension(280, 350));

        JLabel nomLabel = new JLabel("\uD83D\uDC64 " + billet.getNom() + " - Profil : " + billet.getProfil());
        nomLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        panel.add(nomLabel);

        panel.add(new JLabel(" "));

        double prixTotalParcs = 0;
        panel.add(new JLabel("\uD83D\uDEB9 Parcs choisis :"));
        for (Parc parc : parcsChoisis) {
            panel.add(new JLabel("- " + parc.getNom() + " : " + parc.getPrixEntree() + "€"));
            prixTotalParcs += parc.getPrixEntree();
        }

        panel.add(new JLabel(" "));

        int nombreParcs = parcsChoisis.size();
        double reductionMultiParcs = 0;
        if (nombreParcs == 2) reductionMultiParcs = 0.10;
        else if (nombreParcs == 3) reductionMultiParcs = 0.30;
        else if (nombreParcs >= 4) reductionMultiParcs = 0.40;

        double prixApresMulti = prixTotalParcs * (1 - reductionMultiParcs);

        double reductionProfil = 0;
        if (billet.getProfil().equalsIgnoreCase("ENFANT")) {
            reductionProfil = 0.5;
        } else if (billet.getProfil().equalsIgnoreCase("SENIOR")) {
            reductionProfil = 0.3;
        }

        double prixApresProfil = prixApresMulti * (1 - reductionProfil);

        boolean fastPass = billet.isFastPassSelected();
        double fastPassSupplement = fastPass ? prixApresProfil * 0.6 : 0;
        double prixAvantFidelite = prixApresProfil + fastPassSupplement;

        double reductionFidelitePourcentage = 0;
        if (billet.getClient() != null) {
            ReservationDAO reservationDAO = new ReservationDAO();
            int nbCommandes = reservationDAO.getNombreCommandesByClientId(billet.getClient().getId()) + 1;

            if (nbCommandes >= 1 && nbCommandes < 5) reductionFidelitePourcentage = 0.05;
            else if (nbCommandes >= 5 && nbCommandes < 10) reductionFidelitePourcentage = 0.10;
            else if (nbCommandes >= 10) reductionFidelitePourcentage = 0.15;
        }

        double reductionFideliteMontant = prixAvantFidelite * reductionFidelitePourcentage;
        double prixApresFidelite = prixAvantFidelite - reductionFideliteMontant;

        double prixFinal = prixApresFidelite;

        if (billet.getClient() != null && billet.getClient().getAbonnement() != null) {
            String abonnement = billet.getClient().getAbonnement();
            if (abonnement.equals("PASS_ANNUEL")) {
                prixFinal = 0;
            } else if (abonnement.equals("VIP")) {
                prixFinal /= 2;
            } else if (abonnement.equals("SUPER_VITESSE")) {
                prixFinal -= fastPassSupplement;
            }
        }

        panel.add(new JLabel("💸 Prix total parcs : " + String.format("%.2f", prixTotalParcs) + "€"));
        panel.add(new JLabel("➖ Réduction multi-parcs (" + (int)(reductionMultiParcs * 100) + "%) : -" + String.format("%.2f", prixTotalParcs * reductionMultiParcs) + "€"));
        panel.add(new JLabel("➖ Réduction profil (" + (int)(reductionProfil * 100) + "%) : -" + String.format("%.2f", prixApresMulti * reductionProfil) + "€"));

        if (fastPass)
            panel.add(new JLabel("🚀 Fast Pass (+60%) : +" + String.format("%.2f", fastPassSupplement) + "€"));
        else
            panel.add(new JLabel("🚀 Fast Pass : Non ajouté"));

        if (reductionFidelitePourcentage > 0) {
            panel.add(new JLabel("🎟️ Réduction fidélité (" + (int)(reductionFidelitePourcentage * 100) + "%) : -" + String.format("%.2f", reductionFideliteMontant) + "€"));
        }

        if (billet.getClient() != null && billet.getClient().getAbonnement() != null) {
            String abonnement = billet.getClient().getAbonnement();
            if (abonnement.equals("PASS_ANNUEL")) {
                panel.add(new JLabel("🏆 PASS_ANNUEL : Billet gratuit !"));
            } else if (abonnement.equals("VIP")) {
                panel.add(new JLabel("🏆 VIP : Prix divisé par 2"));
            } else if (abonnement.equals("SUPER_VITESSE")) {
                panel.add(new JLabel("🏆 SUPER_VITESSE : Suppression Fast Pass"));
            }
        }

        panel.add(new JLabel(" "));
        panel.add(new JLabel("💳 TOTAL billet : " + String.format("%.2f", prixFinal) + "€"));
        panel.add(new JLabel(" "));

        return panel;
    }

    private double calculerPrixBillet(TicketPanel billet) {
        double prixTotalParcs = 0;
        for (Parc parc : parcsChoisis) {
            prixTotalParcs += parc.getPrixEntree();
        }

        int nombreParcs = parcsChoisis.size();
        double reductionMultiParcs = 0;
        if (nombreParcs == 2) reductionMultiParcs = 0.10;
        else if (nombreParcs == 3) reductionMultiParcs = 0.30;
        else if (nombreParcs >= 4) reductionMultiParcs = 0.40;

        double prixApresMulti = prixTotalParcs * (1 - reductionMultiParcs);

        double reductionProfil = 0;
        if (billet.getProfil().equalsIgnoreCase("ENFANT")) reductionProfil = 0.5;
        else if (billet.getProfil().equalsIgnoreCase("SENIOR")) reductionProfil = 0.3;

        double prixApresProfil = prixApresMulti * (1 - reductionProfil);

        boolean fastPass = billet.isFastPassSelected();
        double fastPassSupplement = fastPass ? prixApresProfil * 0.6 : 0;

        double prixAvantFidelite = prixApresProfil + fastPassSupplement;

        double reductionFidelitePourcentage = 0;
        if (billet.getClient() != null) {
            ReservationDAO reservationDAO = new ReservationDAO();
            int nbCommandes = reservationDAO.getNombreCommandesByClientId(billet.getClient().getId()) + 1;

            if (nbCommandes >= 1 && nbCommandes < 5) reductionFidelitePourcentage = 0.05;
            else if (nbCommandes >= 5 && nbCommandes < 10) reductionFidelitePourcentage = 0.10;
            else if (nbCommandes >= 10) reductionFidelitePourcentage = 0.15;
        }

        prixAvantFidelite *= (1 - reductionFidelitePourcentage);

        if (billet.getClient() != null && billet.getClient().getAbonnement() != null) {
            String abonnement = billet.getClient().getAbonnement();
            if (abonnement.equals("PASS_ANNUEL")) return 0;
            if (abonnement.equals("VIP")) return prixAvantFidelite / 2;
            if (abonnement.equals("SUPER_VITESSE")) return prixAvantFidelite - fastPassSupplement;
        }

        return prixAvantFidelite;
    }

    public LocalDate getDateChoisie() {
        return dateChoisie;
    }
}
