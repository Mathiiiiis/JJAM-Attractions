package view;

import dao.ReservationDAO;
import model.Attraction;
import model.Ticket;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class RecapitulatifGUI extends JFrame {
    public RecapitulatifGUI(List<Attraction> parcs, List<Ticket> tickets, LocalDate date) {
        setTitle("Récapitulatif");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLayout(new BorderLayout());

        JTextArea recap = new JTextArea();
        recap.setEditable(false);
        recap.setFont(new Font("Monospaced", Font.PLAIN, 14));

        StringBuilder sb = new StringBuilder();
        sb.append("Votre Commande:\n\n");

        for (Ticket t : tickets) {
            sb.append("- ").append(t.getProfil()).append("\n");
        }
        sb.append("\nDate: ").append(date).append("\n");
        sb.append("Parcs: \n");
        for (Attraction parc : parcs) {
            sb.append("• ").append(parc.getNom()).append("\n");
        }

        recap.setText(sb.toString());

        JScrollPane scrollPane = new JScrollPane(recap);
        add(scrollPane, BorderLayout.CENTER);

        JButton confirmer = new JButton("Payer et Finaliser");
        add(confirmer, BorderLayout.SOUTH);

        confirmer.addActionListener(e -> {
            ReservationDAO reservationDAO = new ReservationDAO();
            reservationDAO.enregistrerReservation(tickets, date, parcs);

            JOptionPane.showMessageDialog(this, "Réservation Confirmée !");
            new MenuPrincipal().setVisible(true);
            dispose();
        });

        setVisible(true);
    }
}
