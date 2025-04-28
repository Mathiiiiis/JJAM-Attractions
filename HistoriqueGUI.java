package view;

import dao.ReservationDAO;
import model.Client;
import model.Reservation;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class HistoriqueGUI extends JPanel {
    private Client client;
    private ReservationDAO reservationDAO;
    private JPanel commandesPanel;

    public HistoriqueGUI(Client client) {
        this.client = client;
        this.reservationDAO = new ReservationDAO();

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        initUI();
    }

    private void initUI() {
        JLabel titre = new JLabel("\uD83D\uDCD6 Historique de mes réservations", SwingConstants.CENTER);
        titre.setFont(new Font("SansSerif", Font.BOLD, 24));
        add(titre, BorderLayout.NORTH);

        commandesPanel = new JPanel();
        commandesPanel.setLayout(new BoxLayout(commandesPanel, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(commandesPanel);
        add(scrollPane, BorderLayout.CENTER);

        chargerHistorique();
    }

    public void chargerHistorique() {
        commandesPanel.removeAll();

        List<Reservation> commandes = reservationDAO.getCommandesByClientId(client.getId());

        if (commandes.isEmpty()) {
            JLabel aucunLabel = new JLabel("Aucune réservation trouvée.");
            aucunLabel.setFont(new Font("SansSerif", Font.ITALIC, 16));
            aucunLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            commandesPanel.add(aucunLabel);
        } else {
            for (Reservation reservation : commandes) {
                JPanel carte = new JPanel(new GridLayout(2, 2));
                carte.setBorder(BorderFactory.createTitledBorder(reservation.getParcNom()));
                carte.add(new JLabel("\uD83D\uDCC5 Date: "+ reservation.getDate()));
                carte.add(new JLabel("\uD83C\uDF9F\uFE0F Parc: "+ reservation.getParcNom()));
                carte.add(new JLabel("\uD83D\uDC64 Tickets: "+ reservation.getNombreTickets()));
                commandesPanel.add(carte);
            }
        }

        commandesPanel.revalidate();
        commandesPanel.repaint();
    }

    public void rechargerHistorique() {
        chargerHistorique();
    }
}
