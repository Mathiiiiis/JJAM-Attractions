package view;

import dao.ClientDAO;
import model.Client;
import model.Profil;

import javax.swing.*;
import java.awt.*;

public class AjoutBilletDialog extends JDialog {
    private boolean validInput = false;
    private String nom;
    private Profil profil;
    private Client clientConnecteSupp = null;

    private JTextField nomField;
    private JComboBox<Profil> profilCombo;
    private JTextField emailField;
    private JPasswordField passwordField;

    public AjoutBilletDialog(JFrame parent) {
        super(parent, "Ajouter un billet", true);
        setSize(400, 300);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JPanel choixPanel = new JPanel(new GridLayout(2, 1, 10, 10));

        JButton billetClassiqueBtn = new JButton("➕ Ajouter un billet classique");
        JButton connexionBtn = new JButton("🔒 Se connecter pour un billet connecté");

        choixPanel.add(billetClassiqueBtn);
        choixPanel.add(connexionBtn);

        add(choixPanel, BorderLayout.CENTER);

        billetClassiqueBtn.addActionListener(e -> ouvrirFormulaireClassique());
        connexionBtn.addActionListener(e -> ouvrirFormulaireConnexion());
    }

    private void ouvrirFormulaireClassique() {
        JDialog formDialog = new JDialog(this, "Billet Classique", true);
        formDialog.setSize(400, 250);
        formDialog.setLocationRelativeTo(this);
        formDialog.setLayout(new GridLayout(3, 2, 10, 10));

        nomField = new JTextField();
        profilCombo = new JComboBox<>(Profil.values());

        JButton validerBtn = new JButton("Valider");
        JButton annulerBtn = new JButton("Annuler");

        formDialog.add(new JLabel("Nom :"));
        formDialog.add(nomField);
        formDialog.add(new JLabel("Profil :"));
        formDialog.add(profilCombo);
        formDialog.add(validerBtn);
        formDialog.add(annulerBtn);

        validerBtn.addActionListener(e -> {
            nom = nomField.getText().trim();
            profil = (Profil) profilCombo.getSelectedItem();
            if (!nom.isEmpty() && profil != null) {
                validInput = true;
                formDialog.dispose();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs !");
            }
        });

        annulerBtn.addActionListener(e -> formDialog.dispose());

        formDialog.setVisible(true);
    }

    private void ouvrirFormulaireConnexion() {
        JDialog loginDialog = new JDialog(this, "Connexion Client", true);
        loginDialog.setSize(400, 250);
        loginDialog.setLocationRelativeTo(this);
        loginDialog.setLayout(new GridLayout(3, 2, 10, 10));

        emailField = new JTextField();
        passwordField = new JPasswordField();

        JButton validerBtn = new JButton("Se connecter");
        JButton annulerBtn = new JButton("Annuler");

        loginDialog.add(new JLabel("Email :"));
        loginDialog.add(emailField);
        loginDialog.add(new JLabel("Mot de passe :"));
        loginDialog.add(passwordField);
        loginDialog.add(validerBtn);
        loginDialog.add(annulerBtn);

        validerBtn.addActionListener(e -> {
            String email = emailField.getText().trim();
            String password = new String(passwordField.getPassword());

            ClientDAO clientDAO = new ClientDAO();
            clientConnecteSupp = clientDAO.login(email, password);

            if (clientConnecteSupp != null) {
                nom = clientConnecteSupp.getNom(); // 🔥 Correction : prendre le vrai nom
                profil = clientConnecteSupp.getProfil(); // 🔥 Correction : prendre le vrai profil

                validInput = true;
                JOptionPane.showMessageDialog(this, "Connexion réussie ✅", "Succès", JOptionPane.INFORMATION_MESSAGE);
                loginDialog.dispose();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Connexion échouée ❌. Vérifiez vos identifiants.", "Erreur", JOptionPane.ERROR_MESSAGE);
                loginDialog.dispose();
            }
        });

        annulerBtn.addActionListener(e -> loginDialog.dispose());

        loginDialog.setVisible(true);
    }

    public boolean isValidInput() {
        return validInput;
    }

    public String getNom() {
        return nom;
    }

    public Profil getProfil() {
        return profil;
    }

    public Client getClientConnecteSupp() {
        return clientConnecteSupp;
    }
}
