package view;

import javax.swing.*;
import java.awt.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import dao.ClientDAO;
import model.Client;
import model.Profil;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class InscriptionGUI extends JFrame {
    public InscriptionGUI() {
        super("Inscription Client");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(255, 250, 240));
        panel.setBorder(BorderFactory.createEmptyBorder(60, 150, 60, 150));

        JLabel title = new JLabel("Inscription Client");
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField nomField = new JTextField(20);
        JTextField emailField = new JTextField(20);
        JPasswordField passField = new JPasswordField(20);

        // Création d'un champ de saisie pour la date au format JJ/MM/AAAA
        JFormattedTextField dobField = new JFormattedTextField(new SimpleDateFormat("dd/MM/yyyy"));
        dobField.setColumns(10);

        JComboBox<String> sexeBox = new JComboBox<>(new String[]{"Homme", "Femme", "Autre"});
        JButton registerBtn = new JButton("S'inscrire");

        registerBtn.setBackground(new Color(0, 120, 215));
        registerBtn.setForeground(Color.WHITE);
        registerBtn.setFont(new Font("SansSerif", Font.BOLD, 16));
        registerBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        registerBtn.addActionListener(e -> {
            try {
                // Récupérer la date de naissance entrée par l'utilisateur (format : JJ/MM/AAAA)
                String dobStr = dobField.getText();
                DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                Date dateOfBirth = formatter.parse(dobStr);

                // Calcul de l'âge à partir de la date de naissance
                LocalDate birthDate = new java.sql.Date(dateOfBirth.getTime()).toLocalDate();
                int age = Period.between(birthDate, LocalDate.now()).getYears();

                // Déterminer le profil (Enfant, Senior, Adulte)
                Profil profil = (age < 18) ? Profil.ENFANT : (age > 60) ? Profil.SENIOR : Profil.REGULIER;

                ClientDAO clientDAO = new ClientDAO();
                boolean ok = clientDAO.inscrireClient(
                        nomField.getText(),
                        emailField.getText(),
                        new String(passField.getPassword()),
                        profil
                );

                if (ok) {
                    // Après l'inscription réussie, récupérer le client
                    Client client = clientDAO.getClientByEmailAndPassword(
                            emailField.getText(),
                            new String(passField.getPassword())
                    );
                    JOptionPane.showMessageDialog(this, "Inscription réussie !");
                    // Passer le client au ReservationGUI
                    new ReservationGUI(client, new ArrayList<>()).setVisible(true);
                    dispose();  // Fermer la fenêtre d'inscription
                } else {
                    JOptionPane.showMessageDialog(this, "Erreur lors de l'inscription.");
                }
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(this, "Veuillez entrer une date valide (format: JJ/MM/AAAA).");
            }
        });

        panel.add(title);
        panel.add(Box.createVerticalStrut(20));
        panel.add(new JLabel("Nom:"));
        panel.add(nomField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Mot de passe:"));
        panel.add(passField);
        panel.add(new JLabel("Date de naissance (JJ/MM/AAAA):"));
        panel.add(dobField);
        panel.add(new JLabel("Sexe:"));
        panel.add(sexeBox);
        panel.add(Box.createVerticalStrut(20));
        panel.add(registerBtn);

        add(panel);
    }
}
