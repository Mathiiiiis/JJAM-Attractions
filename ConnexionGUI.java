package view;
//ce fichier va nous être utile pour:
import dao.ClientDAO;
import model.Client;

import javax.swing.*;
import java.awt.*;

public class ConnexionGUI extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;

    public ConnexionGUI() {
        setTitle("Connexion");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 🔥 Important : DISPOSE pour ne pas tout fermer
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        emailField = new JTextField();
        passwordField = new JPasswordField();
        JButton loginButton = new JButton("Connexion");
        JButton registerButton = new JButton("Inscription");

        panel.add(new JLabel("Email :"));
        panel.add(emailField);
        panel.add(new JLabel("Mot de passe :"));
        panel.add(passwordField);
        panel.add(loginButton);
        panel.add(registerButton);

        add(panel);

        loginButton.addActionListener(e -> {
            String email = emailField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();

            if (email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            ClientDAO clientDAO = new ClientDAO();
            Client client = clientDAO.login(email, password);

            if (client != null) {
                JOptionPane.showMessageDialog(this, "✅ Connexion réussie !");
                new ClientDashboardGUI(client).setVisible(true); // 🔥 Lancement du Dashboard Complet
                dispose(); // ✅ Fermer juste Connexion
            } else {
                JOptionPane.showMessageDialog(this, "❌ Identifiants incorrects.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        registerButton.addActionListener(e -> {
            new InscriptionGUI().setVisible(true);
            dispose();
        });
    }
}
