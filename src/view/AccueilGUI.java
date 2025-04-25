
package view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.util.ArrayList;

public class AccueilGUI extends JFrame {
    public AccueilGUI() {
        super("Accueil Parc Attractions");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new java.awt.GridLayout(4, 1, 10, 10));

        JButton register = new JButton("S'inscrire");
        JButton guest = new JButton("Continuer en tant qu'invité");
        JButton login = new JButton("Se connecter");
        JButton admin = new JButton("Accès administrateur");

        register.addActionListener((ActionEvent e) -> {
            new InscriptionGUI().setVisible(true);
            dispose();
        });

        guest.addActionListener((ActionEvent e) -> {
            new ReservationGUI(null, new ArrayList<>(), LocalDate.now()).setVisible(true);
            dispose();
        });

        login.addActionListener((ActionEvent e) -> {
            new ConnexionGUI().setVisible(true);
            dispose();
        });

        admin.addActionListener((ActionEvent e) -> {
            new AdminGUI().setVisible(true);
            dispose();
        });

        add(register);
        add(guest);
        add(login);
        add(admin);
    }
}
