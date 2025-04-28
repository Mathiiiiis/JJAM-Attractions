package view;

import controller.ReservationController;
import model.Client;
import model.Parc;
import model.Profil;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ChoixBilletsGUI extends JFrame {
    private List<Parc> parcsChoisis;
    private JPanel billetPanelContainer;
    private List<TicketPanel> billets = new ArrayList<>();
    private Client clientConnecte;

    public ChoixBilletsGUI(List<Parc> parcsChoisis, Client clientConnecte) {
        this.parcsChoisis = parcsChoisis;
        this.clientConnecte = clientConnecte;

        setTitle("Sélection des Billets");
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        JLabel titre = new JLabel("<html><center>Vous avez choisi : " + parcsChoisis.size() + " parc(s)<br>Ajoutez vos billets !</center></html>", SwingConstants.CENTER);
        titre.setFont(new Font("SansSerif", Font.BOLD, 20));
        add(titre, BorderLayout.NORTH);

        billetPanelContainer = new JPanel();
        billetPanelContainer.setLayout(new GridLayout(0, 3, 10, 10));
        JScrollPane scrollPane = new JScrollPane(billetPanelContainer);
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));

        JButton retourBtn = new JButton("⬅️ Retour"); // 🆕 Bouton Retour
        JButton ajouterBilletBtn = new JButton("➕ Ajouter un billet");
        JButton validerBtn = new JButton("Continuer ➡️");

        // ➔ Actions des boutons
        retourBtn.addActionListener(e -> {
            new ChoixParcGUI(clientConnecte).setVisible(true);
            dispose();
        });

        ajouterBilletBtn.addActionListener(e -> ouvrirAjoutBillet());

        validerBtn.addActionListener(e -> {
            if (billets.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ajoutez au moins un billet !");
            } else {
                new PageCalendrier(parcsChoisis, billets.size(), billets, clientConnecte,new HistoriqueGUI(clientConnecte)).setVisible(true);
                dispose();
            }
        });

        bottomPanel.add(retourBtn);
        bottomPanel.add(ajouterBilletBtn);
        bottomPanel.add(validerBtn);

        add(bottomPanel, BorderLayout.SOUTH);

        ajouterBilletPrincipal();
        setVisible(true);
    }

    private void ajouterBilletPrincipal() {
        if (clientConnecte != null) {
            String nom = clientConnecte.getNom() != null ? clientConnecte.getNom() : "Client";
            String profil = clientConnecte.getProfil() != null ? clientConnecte.getProfil().name() : "REGULIER";

            double base = ReservationController.calculerPrixDeBase(parcsChoisis);
            double prixFinal = base;

            TicketPanel billetClient = new TicketPanel(nom, profil, prixFinal, false, clientConnecte);
            billets.add(billetClient);
            billetPanelContainer.add(billetClient);
            billetPanelContainer.revalidate();
            billetPanelContainer.repaint();
        }
    }

    private void ouvrirAjoutBillet() {
        AjoutBilletDialog dialog = new AjoutBilletDialog(this);
        dialog.setVisible(true);

        if (dialog.isValidInput()) {
            String nom;
            Profil profil;
            Client clientSupp = dialog.getClientConnecteSupp();

            if (clientSupp != null) {
                // 🟢 Client connecté secondaire
                nom = clientSupp.getNom();
                profil = clientSupp.getProfil();
            } else {
                // 🎟️ Invité classique
                nom = dialog.getNom() != null && !dialog.getNom().isEmpty() ? dialog.getNom() : "Invité";
                profil = dialog.getProfil() != null ? dialog.getProfil() : Profil.REGULIER;
            }

            double base = ReservationController.calculerPrixDeBase(parcsChoisis);
            double prixFinal = base;

            TicketPanel billet = new TicketPanel(nom, profil.name(), prixFinal, false, clientSupp);

            if (clientSupp != null) {
                billet.setClient(clientSupp);
            }

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
