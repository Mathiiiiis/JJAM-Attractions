
package view;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

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

    public ConfirmationGUI(Client client, List<Ticket> tickets, double prixTotal, LocalDate dateChoisie) {
        super("Confirmation de Réservation");

        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());
        JLabel title = new JLabel("Confirmation", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(title, BorderLayout.NORTH);

        JTextArea ticketArea = new JTextArea();
        ticketArea.setEditable(false);
        tickets.forEach(ticket -> {
            ticketArea.append("🎟️ " + ticket.getAttraction().getNom() + " - " +
                    ticket.getProfil() + " - " + ticket.getPrix() + " €");
            if (ticket.isFastPass()) {
                ticketArea.append(" [FastPass]");
            }
            ticketArea.append("\n");
        });

        panel.add(new JScrollPane(ticketArea), BorderLayout.CENTER);

        JPanel boutons = new JPanel();

        JButton continuer = new JButton("⏎ Continuer la réservation");
        continuer.addActionListener(e -> {
            new ReservationGUI(client, tickets, dateChoisie).setVisible(true);
            dispose();
        });

        JButton retour = new JButton("← Retour à l'accueil");
        retour.addActionListener(e -> {
            new MenuPrincipal().setVisible(true);
            dispose();
        });

        boutons.add(continuer);
        boutons.add(retour);

        panel.add(boutons, BorderLayout.SOUTH);
        add(panel);
    }
}
