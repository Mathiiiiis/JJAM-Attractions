package view;

import model.Client;
import model.Profil;

import javax.swing.*;
import java.awt.*;
//ce fichier va nous être utile pour:
public class ProfileGUI extends JPanel {
    private Client client;

    public ProfileGUI(Client client) {
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

        JButton saveButton = new JButton("Sauvegarder");

        add(nomLabel);
        add(nomField);
        add(emailLabel);
        add(emailField);
        add(mdpLabel);
        add(mdpField);
        add(profilLabel);
        add(profilComboBox);
        add(new JLabel());
        add(saveButton);

        saveButton.addActionListener(e -> {
            client.setNom(nomField.getText());
            client.setEmail(emailField.getText());
            client.setMotDePasse(new String(mdpField.getPassword()));
            client.setProfil((Profil) profilComboBox.getSelectedItem());

            JOptionPane.showMessageDialog(this, "Profil mis à jour !");
        });
    }
}
