package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import dao.ClientDAO;
import model.Client;
import model.Profil;
import java.util.ArrayList;
import dao.ClientDAO;

public class ConnexionGUI extends JFrame {
    public ConnexionGUI() {
        super("Connexion Client");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(245, 245, 255));
        panel.setBorder(BorderFactory.createEmptyBorder(60, 150, 60, 150));

        JLabel title = new JLabel("Connexion Client");
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField emailField = new JTextField(20);
        JPasswordField passField = new JPasswordField(20);
        JButton loginBtn = new JButton("Se connecter");

        loginBtn.setBackground(new Color(0, 120, 215));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFont(new Font("SansSerif", Font.BOLD, 16));
        loginBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton forgotPasswordBtn = new JButton("Mot de passe oublié ?");
        forgotPasswordBtn.setBackground(new Color(255, 51, 51));
        forgotPasswordBtn.setForeground(Color.WHITE);
        forgotPasswordBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
        forgotPasswordBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        loginBtn.addActionListener(e -> {
            ClientDAO clientDAO = new ClientDAO();
            // Récupérer le client en fonction de l'email et du mot de passe
            Client client = clientDAO.getClientByEmailAndPassword(
                    emailField.getText(),
                    new String(passField.getPassword())
            );

            if (client != null) {
                JOptionPane.showMessageDialog(this, "Connexion réussie !");
                new ReservationGUI(client, new ArrayList<>()).setVisible(true); // Passer le client et un panier vide
                dispose(); // Fermer la page de connexion
            } else {
                JOptionPane.showMessageDialog(this, "Identifiants incorrects !");
            }
        });

        // Action pour "Mot de passe oublié"
        forgotPasswordBtn.addActionListener(e -> {
            String email = JOptionPane.showInputDialog(this, "Entrez votre adresse e-mail pour réinitialiser le mot de passe");
            if (email != null && !email.isEmpty()) {
                // Simuler la réinitialisation du mot de passe (vous devez implémenter la logique réelle ici)
                JOptionPane.showMessageDialog(this, "Un e-mail de réinitialisation a été envoyé à : " + email);
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez entrer un email valide.");
            }
        });

        panel.add(title);
        panel.add(Box.createVerticalStrut(20));
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Mot de passe:"));
        panel.add(passField);
        panel.add(Box.createVerticalStrut(20));
        panel.add(loginBtn);
        panel.add(Box.createVerticalStrut(10));
        panel.add(forgotPasswordBtn);  // Ajout du bouton pour mot de passe oublié

        add(panel);
    }
}
