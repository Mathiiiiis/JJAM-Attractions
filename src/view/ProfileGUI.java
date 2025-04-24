package view;

import javax.swing.*;
import java.awt.*;
import dao.ClientDAO;
import model.Client;
import model.Profil;  // Assurez-vous d'importer l'énumération Profil

public class ProfileGUI extends JFrame {

    public ProfileGUI(Client client) {
        super("Mon Profil");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(240, 240, 255));
        panel.setBorder(BorderFactory.createEmptyBorder(60, 150, 60, 150));

        JLabel title = new JLabel("Mon Profil");
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel nameLabel = new JLabel("Nom: " + client.getNom());
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel emailLabel = new JLabel("Email: " + client.getEmail());
        emailLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel profileLabel = new JLabel("Profil: " + client.getProfil().name());
        profileLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Champs pour modifier l'email et le mot de passe
        JTextField emailField = new JTextField(client.getEmail(), 20);
        JPasswordField passwordField = new JPasswordField(20);

        // Bouton pour sauvegarder les modifications
        JButton saveBtn = new JButton("Sauvegarder les modifications");
        saveBtn.setBackground(new Color(0, 120, 215));
        saveBtn.setForeground(Color.WHITE);
        saveBtn.setFont(new Font("SansSerif", Font.BOLD, 16));
        saveBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        saveBtn.addActionListener(e -> {
            String newEmail = emailField.getText();
            String newPassword = new String(passwordField.getPassword());

            client.setEmail(newEmail);
            client.setMotDePasse(newPassword); // Mettez à jour les informations du client

            ClientDAO clientDAO = new ClientDAO();
            clientDAO.updateClient(client);  // Mise à jour des informations dans la base de données

            JOptionPane.showMessageDialog(this, "Profil mis à jour avec succès !");
        });

        // Bouton pour afficher l'historique des réservations
        JButton viewHistoryBtn = new JButton("Voir mon historique");
        viewHistoryBtn.setBackground(new Color(0, 120, 215));
        viewHistoryBtn.setForeground(Color.WHITE);
        viewHistoryBtn.setFont(new Font("SansSerif", Font.BOLD, 16));
        viewHistoryBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        viewHistoryBtn.addActionListener(e -> {
            // Ouvrir la fenêtre pour voir l'historique des réservations
            new ReservationHistoryGUI(client).setVisible(true);
        });

        panel.add(title);
        panel.add(Box.createVerticalStrut(20));
        panel.add(nameLabel);
        panel.add(emailLabel);
        panel.add(profileLabel);
        panel.add(Box.createVerticalStrut(20));
        panel.add(new JLabel("Modifier Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Modifier Mot de Passe:"));
        panel.add(passwordField);
        panel.add(Box.createVerticalStrut(20));
        panel.add(saveBtn);
        panel.add(Box.createVerticalStrut(20));
        panel.add(viewHistoryBtn);

        add(panel);
    }

    public static void main(String[] args) {
        // Pour tester, vous pouvez créer un client fictif
        Client testClient = new Client();
        testClient.setNom("John Doe");
        testClient.setEmail("john.doe@example.com");
        testClient.setProfil(Profil.REGULIER); // Exemple de profil
        SwingUtilities.invokeLater(() -> new ProfileGUI(testClient).setVisible(true));
    }
}
