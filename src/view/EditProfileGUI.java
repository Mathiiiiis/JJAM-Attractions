package view;

import javax.swing.*;
import java.awt.*;
import dao.ClientDAO;
import model.Client;

public class EditProfileGUI extends JFrame {
    public EditProfileGUI(Client client) {
        super("Modifier mon Profil");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(240, 240, 255));
        panel.setBorder(BorderFactory.createEmptyBorder(60, 150, 60, 150));

        JLabel title = new JLabel("Modifier Mon Profil");
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel emailLabel = new JLabel("Email: ");
        JTextField emailField = new JTextField(client.getEmail(), 20);

        JLabel passwordLabel = new JLabel("Mot de passe: ");
        JPasswordField passwordField = new JPasswordField(20);

        JButton saveBtn = new JButton("Sauvegarder");

        saveBtn.setBackground(new Color(0, 120, 215));
        saveBtn.setForeground(Color.WHITE);
        saveBtn.setFont(new Font("SansSerif", Font.BOLD, 16));
        saveBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        saveBtn.addActionListener(e -> {
            String newEmail = emailField.getText();
            String newPassword = new String(passwordField.getPassword());

            client.setEmail(newEmail);
            client.setMotDePasse(newPassword);

            ClientDAO clientDAO = new ClientDAO();
            clientDAO.updateClient(client); // Mettre à jour dans la base de données

            JOptionPane.showMessageDialog(this, "Profil mis à jour avec succès !");
        });

        panel.add(title);
        panel.add(Box.createVerticalStrut(20));
        panel.add(emailLabel);
        panel.add(emailField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(Box.createVerticalStrut(20));
        panel.add(saveBtn);

        add(panel);
    }
}
