package view;

import model.Parc;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CalendrierReservationGUI extends JFrame {
    private List<Parc> parcsSelectionnes;
    private int nombreBillets;

    public CalendrierReservationGUI(List<Parc> parcsSelectionnes, int nombreBillets) {
        this.parcsSelectionnes = parcsSelectionnes;
        this.nombreBillets = nombreBillets;

        setTitle("Calendrier de Réservation");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        JLabel titre = new JLabel("Choisissez votre date de visite", SwingConstants.CENTER);
        titre.setFont(new Font("SansSerif", Font.BOLD, 20));
        add(titre, BorderLayout.NORTH);

        // Exemple basique : calendrier fictif
        JPanel calendarPanel = new JPanel();
        calendarPanel.add(new JLabel("Ici votre composant de calendrier..."));
        add(calendarPanel, BorderLayout.CENTER);

        JButton reserverBtn = new JButton("Réserver");
        reserverBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Réservation confirmée pour " + nombreBillets + " billet(s) !");
            dispose();
        });

        add(reserverBtn, BorderLayout.SOUTH);
    }
}
