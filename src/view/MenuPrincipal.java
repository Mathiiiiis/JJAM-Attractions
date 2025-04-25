
package view;

import javax.swing.*;
import java.awt.*;

public class MenuPrincipal extends JFrame {
    public MenuPrincipal() {
        super("Accueil Parc Attractions");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 1, 10, 10));

        JButton btnConnexion = new JButton("Se connecter");
        JButton btnInscription = new JButton("S'inscrire");
        JButton btnInvite = new JButton("Continuer en tant qu'invité");
        JButton quitter = new JButton("Quitter");

        btnConnexion.addActionListener(e -> {
            new ConnexionGUI().setVisible(true);
            dispose();
        });

        btnInscription.addActionListener(e -> {
            new InscriptionGUI().setVisible(true);
            dispose();
        });

        btnInvite.addActionListener(e -> {
            PageCalendrier calendrierInvite = new PageCalendrier(null);
            calendrierInvite.setTitle("Mode Invité");
            calendrierInvite.setVisible(true);
            dispose();
        });

        quitter.addActionListener(e -> System.exit(0));

        add(btnConnexion);
        add(btnInscription);
        add(btnInvite);
        add(quitter);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MenuPrincipal().setVisible(true));
    }
}
