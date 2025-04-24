package view;

import javax.swing.*;
import java.awt.*;
import model.Client;
import java.util.ArrayList;

public class AccueilGUI extends JFrame {

    public AccueilGUI() {
        super("Accueil");

        // Taille maximisée de la fenêtre
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);  // Centrer la fenêtre

        // Panneau principal
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Création des boutons
        JButton register = new JButton("S'inscrire");
        JButton guest = new JButton("Continuer en tant qu'invité");
        JButton login = new JButton("Se connecter");
        JButton admin = new JButton("Accès Administrateur");

        // Action pour l'inscription
        register.addActionListener(e -> {
            new InscriptionGUI().setVisible(true);
            dispose();
        });

        // Action pour continuer en tant qu'invité
        guest.addActionListener(e -> {
            // Passer un client null (s'il s'agit d'un invité) et un panier vide
            new ReservationGUI(null, new ArrayList<>()).setVisible(true);
            dispose();
        });

        // Action pour la connexion
        login.addActionListener(e -> {
            // Afficher la fenêtre de connexion
            new ConnexionGUI().setVisible(true);
            dispose();
        });

        // Action pour l'accès administrateur
        admin.addActionListener(e -> {
            // Vérification d'un rôle administrateur (peut être basé sur un login ou une condition)
            String password = JOptionPane.showInputDialog(this, "Entrez le mot de passe administrateur :");
            if ("admin".equals(password)) {  // Juste un exemple simple
                new AdminGUI().setVisible(true);  // Ouvrir l'interface administrateur
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Mot de passe incorrect.");
            }
        });

        // Ajouter les boutons au panneau
        panel.add(register);
        panel.add(login);
        panel.add(admin);  // Ajouter le bouton "Accès Administrateur"
        panel.add(guest);
        panel.add(Box.createVerticalStrut(40));  // Espace entre les boutons

        // Ajouter le panneau à la fenêtre
        add(panel);
    }
}
