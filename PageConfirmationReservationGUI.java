package view;

import dao.ReservationDAO;
import model.Client;
import model.Parc;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class PageConfirmationReservationGUI extends JFrame {
    private Client client;
    private List<TicketPanel> billets;
    private List<Parc> parcsChoisis;
    private double totalFinal;
    private LocalDate dateChoisie;

    public PageConfirmationReservationGUI(Client client, List<TicketPanel> billets, List<Parc> parcsChoisis, double totalFinal, LocalDate dateChoisie) {
        this.client = client;
        this.billets = billets;
        this.parcsChoisis = parcsChoisis;
        this.totalFinal = totalFinal;
        this.dateChoisie = dateChoisie;

        setTitle("Confirmation Réservation");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        enregistrerReservations();
        initUI();

        // 🔥 Quand on ferme la fenêtre => retour à l'accueil proprement
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                dispose(); // ferme cette fenêtre
                new AccueilGUI().setVisible(true); // ouvre l'accueil
            }
        });

        setVisible(true);
    }

    private void enregistrerReservations() {
        ReservationDAO reservationDAO = new ReservationDAO();

        for (Parc parc : parcsChoisis) {
            reservationDAO.ajouterReservation(parc.getNom(), dateChoisie, billets.size());
        }

        for (TicketPanel billet : billets) {
            if (billet.getClient() != null) {
                reservationDAO.ajouterCommandeClient(billet.getClient().getId(), dateChoisie, parcsChoisis.get(0).getNom());
            }
        }
    }

    private void initUI() {
        setLayout(new BorderLayout());

        JLabel confirmationLabel = new JLabel("<html><center>🎉 Votre réservation est confirmée !<br>Merci pour votre confiance.</center></html>", SwingConstants.CENTER);
        confirmationLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        confirmationLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        add(confirmationLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));

        JButton factureButton = new JButton("📄 Télécharger la facture");
        JButton ticketsButton = new JButton("🎟️ Télécharger les billets");

        factureButton.addActionListener(e -> {
            PDFGenerator.generateFacturePDF(client, billets, parcsChoisis, totalFinal);
            JOptionPane.showMessageDialog(this, "Facture téléchargée !");
        });

        ticketsButton.addActionListener(e -> {
            PDFGenerator.generateBilletsPDF(client, billets, parcsChoisis);
            JOptionPane.showMessageDialog(this, "Billets téléchargés !");
        });

        buttonPanel.add(factureButton);
        buttonPanel.add(ticketsButton);

        add(buttonPanel, BorderLayout.CENTER);
    }
}
