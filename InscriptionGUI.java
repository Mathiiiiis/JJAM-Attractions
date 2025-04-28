package view;

import dao.ClientDAO;
import model.Profil;

import javax.swing.*;
import java.awt.*;

public class InscriptionGUI extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JTextField nomField;
    private JComboBox<Profil> profilComboBox;

    public InscriptionGUI() {
        setTitle("Inscription");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));

        emailField = new JTextField();
        passwordField = new JPasswordField();
        nomField = new JTextField();
        profilComboBox = new JComboBox<>(Profil.values());

        JButton registerButton = new JButton("S'inscrire");
        JButton retourButton = new JButton("Retour");

        panel.add(new JLabel("Nom :"));
        panel.add(nomField);
        panel.add(new JLabel("Email :"));
        panel.add(emailField);
        panel.add(new JLabel("Mot de passe :"));
        panel.add(passwordField);
        panel.add(new JLabel("Profil :"));
        panel.add(profilComboBox);

        panel.add(registerButton);
        panel.add(retourButton);

        add(panel);

        registerButton.addActionListener(e -> {
            String nom = nomField.getText();
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            Profil profil = (Profil) profilComboBox.getSelectedItem();

            if (email.isEmpty() || password.isEmpty() || nom.isEmpty() || profil == null) {
                JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs.", "Erreur", JOptionPane.ERROR_MESSAGE);
            } else {
                ClientDAO clientDAO = new ClientDAO();
                boolean success = clientDAO.inscrireClient(email, password, nom, profil);
                if (success) {
                    JOptionPane.showMessageDialog(this, "Inscription réussie !");
                    new AccueilGUI().setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Erreur lors de l'inscription. Email peut-être déjà utilisé ?", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        retourButton.addActionListener(e -> {
            new AccueilGUI().setVisible(true);
            dispose();
        });
    }
}
