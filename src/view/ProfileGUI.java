package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import dao.ClientDAO;
import model.Client;
import model.Profil;
import java.util.ArrayList;
import dao.ClientDAO;

public class ProfileGUI extends JFrame {

    public ProfileGUI(Client client) {
        super("Mon Profil");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(240, 250, 255));
        panel.setBorder(BorderFactory.createEmptyBorder(60, 150, 60, 150));

        JLabel title = new JLabel("Mon Profil");
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel nameLabel = new JLabel("Nom : " + client.getNom());
        JLabel emailLabel = new JLabel("Email : " + client.getEmail());
        JLabel profilLabel = new JLabel("Profil : " + client.getProfil().name());

        // Champs de texte pour modification
        JTextField emailField = new JTextField(client.getEmail(), 20);
        JPasswordField passwordField = new JPasswordField(20);
        JButton saveBtn = new JButton("Sauvegarder les modifications");

        saveBtn.setBackground(new Color(0, 120, 215));
        saveBtn.setForeground(Color.WHITE);
        saveBtn.setFont(new Font("SansSerif", Font.BOLD, 16));
        saveBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        saveBtn.addActionListener(e -> {
            // Simulation de mise à jour des informations
            String newEmail = emailField.getText();
            String newPassword = new String(passwordField.getPassword());
            client.setEmail(newEmail);
            client.setMotDePasse(newPassword); // Mettre à jour le mot de passe dans la base de données

            ClientDAO clientDAO = new ClientDAO();
            clientDAO.updateClient(client); // Implémenter une méthode pour mettre à jour dans la base de données

            JOptionPane.showMessageDialog(this, "Profil mis à jour avec succès !");
        });

        panel.add(title);
        panel.add(Box.createVerticalStrut(20));
        panel.add(nameLabel);
        panel.add(emailLabel);
        panel.add(profilLabel);
        panel.add(Box.createVerticalStrut(20));
        panel.add(new JLabel("Modifier Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Modifier Mot de Passe:"));
        panel.add(passwordField);
        panel.add(Box.createVerticalStrut(20));
        panel.add(saveBtn);

        add(panel);
    }
}
