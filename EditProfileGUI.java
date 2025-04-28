package view;

import dao.ClientDAO;
import model.Client;
import model.Profil;

import javax.swing.*;
import java.awt.*;

public class EditProfileGUI extends JPanel {
    private Client client;

    public EditProfileGUI(Client client) {
        this.client = client;
        setLayout(new GridLayout(5, 2, 10, 10));
        setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        JLabel nomLabel = new JLabel("Nom:");
        JTextField nomField = new JTextField(client.getNom());

        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField(client.getEmail());

        JLabel mdpLabel = new JLabel("Mot de Passe:");
        JPasswordField mdpField = new JPasswordField(client.getMotDePasse());

        JLabel profilLabel = new JLabel("Profil:");
        JComboBox<Profil> profilComboBox = new JComboBox<>(Profil.values());
        profilComboBox.setSelectedItem(client.getProfil());

        JButton updateButton = new JButton("Mettre à jour");

        add(nomLabel);
        add(nomField);
        add(emailLabel);
        add(emailField);
        add(mdpLabel);
        add(mdpField);
        add(profilLabel);
        add(profilComboBox);
        add(new JLabel());
        add(updateButton);

        updateButton.addActionListener(e -> {
            client.setNom(nomField.getText());
            client.setEmail(emailField.getText());
            client.setMotDePasse(new String(mdpField.getPassword()));
            client.setProfil((Profil) profilComboBox.getSelectedItem());

            ClientDAO dao = new ClientDAO();
            boolean success = dao.updateClient(client);
            if (success) {
                JOptionPane.showMessageDialog(this, "Mise à jour réussie !");
            } else {
                JOptionPane.showMessageDialog(this, "Erreur lors de la mise à jour.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
