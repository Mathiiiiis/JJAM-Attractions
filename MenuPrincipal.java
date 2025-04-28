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

        // Création des boutons
        JButton btnConnexion = new JButton("Se connecter");
        JButton btnInscription = new JButton("S'inscrire");
        JButton btnInvite = new JButton("Continuer en tant qu'invité");
        JButton quitter = new JButton("Quitter");

        // Action sur le bouton "Se connecter"
        btnConnexion.addActionListener(e -> {
            new ConnexionGUI().setVisible(true);
            dispose(); // Fermeture de la fenêtre principale
        });

        // Action sur le bouton "S'inscrire"
        btnInscription.addActionListener(e -> {
            new InscriptionGUI().setVisible(true);
            dispose(); // Fermeture de la fenêtre principale
        });

        // Action sur le bouton "Continuer en tant qu'invité"
        btnInvite.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Le mode invité n'est pas encore disponible.", "Info", JOptionPane.INFORMATION_MESSAGE);
        });

        // Action sur le bouton "Quitter"
        quitter.addActionListener(e -> System.exit(0)); // Quitter l'application

        // Ajout des boutons au panel
        add(btnConnexion);
        add(btnInscription);
        add(btnInvite);
        add(quitter);
    }

    public static void main(String[] args) {
        // Lance la fenêtre principale
        SwingUtilities.invokeLater(() -> new MenuPrincipal().setVisible(true));
    }
}
