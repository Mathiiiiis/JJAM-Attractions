package view;

import dao.ReservationDAO;
import model.Attraction;
import model.Ticket;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class CalendrierGUI extends JFrame {
    private List<Attraction> parcsChoisis;
    private List<Ticket> tickets;

    public CalendrierGUI(List<Attraction> parcsChoisis, List<Ticket> tickets) {
        this.parcsChoisis = parcsChoisis;
        this.tickets = tickets;

        setTitle("Choix de la Date");
        setSize(500, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLayout(new BorderLayout());

        JLabel titre = new JLabel("Choisissez votre date", SwingConstants.CENTER);
        titre.setFont(new Font("SansSerif", Font.BOLD, 18));
        add(titre, BorderLayout.NORTH);

        JPanel center = new JPanel();
        SpinnerDateModel model = new SpinnerDateModel();
        JSpinner spinner = new JSpinner(model);
        spinner.setEditor(new JSpinner.DateEditor(spinner, "yyyy-MM-dd"));
        center.add(spinner);
        add(center, BorderLayout.CENTER);

        JButton confirmer = new JButton("Confirmer");
        add(confirmer, BorderLayout.SOUTH);
//
        confirmer.addActionListener(e -> {
            LocalDate date = LocalDate.parse(((JSpinner.DefaultEditor) spinner.getEditor()).getTextField().getText());
            new RecapitulatifGUI(parcsChoisis, tickets, date);
            dispose();
        });

        setVisible(true);
    }
}
