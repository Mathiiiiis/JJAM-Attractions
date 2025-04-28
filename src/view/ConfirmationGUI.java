package view;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

import model.Client;
import view.ConfirmationGUI.Ticket;

public class ConfirmationGUI extends JFrame {

    public static class Ticket {
        private final model.Attraction attraction;
        private final model.Profil profil;
        private final double prix;
        private final boolean fastPass;

        public Ticket(model.Attraction attraction, model.Profil profil, double prix, boolean fastPass) {
            this.attraction = attraction;
            this.profil = profil;
            this.prix = prix;
            this.fastPass = fastPass;
        }

        public model.Attraction getAttraction() {
            return attraction;
        }

        public model.Profil getProfil() {
            return profil;
        }

        public double getPrix() {
            return prix;
        }

        public boolean isFastPass() {
            return fastPass;
        }
    }

    private final List<Ticket> tickets;
    private final JLabel totalLabel = new JLabel();
    private final DefaultListModel<String> ticketListModel = new DefaultListModel<>();
    private final JList<String> ticketList = new JList<>(ticketListModel);
    private final Client client;
    private final LocalDate dateChoisie;

    public ConfirmationGUI(Client client, List<Ticket> tickets, double prixTotal, LocalDate dateChoisie) {
        super("Confirmation de Réservation");
        this.client = client;
        this.tickets = new ArrayList<>(tickets);
        this.dateChoisie = dateChoisie;

        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Confirmation de Réservation", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);

        // Zone centrale avec la liste des tickets
        JPanel centerPanel = new JPanel(new BorderLayout());

        majTicketList();

        ticketList.setFont(new Font("SansSerif", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(ticketList);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);

        // Bas avec total + boutons
        JPanel bottomPanel = new JPanel(new GridLayout(2, 1));

        totalLabel.setHorizontalAlignment(SwingConstants.CENTER);
        totalLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        majTotal();
        bottomPanel.add(totalLabel);

        JPanel buttonPanel = new JPanel();

        JButton supprimerBtn = new JButton("❌ Supprimer le billet sélectionné");
        supprimerBtn.addActionListener(e -> supprimerTicket());

        JButton viderBtn = new JButton("🗑️ Vider le panier");
        viderBtn.addActionListener(e -> viderPanier());

        JButton continuerBtn = new JButton("⏎ Continuer réservation");
        continuerBtn.addActionListener(e -> {
            new ReservationGUI(client, tickets, dateChoisie).setVisible(true);
            dispose();
        });

        JButton retourBtn = new JButton("← Retour à l'accueil");
        retourBtn.addActionListener(e -> {
            new MenuPrincipal().setVisible(true);
            dispose();
        });

        buttonPanel.add(supprimerBtn);
        buttonPanel.add(viderBtn);
        buttonPanel.add(continuerBtn);
        buttonPanel.add(retourBtn);

        bottomPanel.add(buttonPanel);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void majTicketList() {
        ticketListModel.clear();
        for (Ticket ticket : tickets) {
            String text = "🎟️ " + ticket.getAttraction().getNom() + " - " +
                    ticket.getProfil() + " - " + String.format("%.2f", ticket.getPrix()) + " €";
            if (ticket.isFastPass()) {
                text += " [FastPass]";
            }
            text += " | 📅 " + dateChoisie.toString();
            ticketListModel.addElement(text);
        }
    }

    private void majTotal() {
        double total = tickets.stream().mapToDouble(Ticket::getPrix).sum();
        totalLabel.setText("Total Panier : " + String.format("%.2f", total) + " €");
    }

    private void supprimerTicket() {
        int index = ticketList.getSelectedIndex();
        if (index >= 0) {
            tickets.remove(index);
            majTicketList();
            majTotal();
        } else {
            JOptionPane.showMessageDialog(this, "Sélectionnez un billet à supprimer !");
        }
    }

    private void viderPanier() {
        tickets.clear();
        majTicketList();
        majTotal();
    }
}
