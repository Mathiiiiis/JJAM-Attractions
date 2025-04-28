package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class AccueilGUI extends JFrame {
    public AccueilGUI() {
        super("Bienvenue au Parc de l'ECE !");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Bandeau de bienvenue
        JLabel welcomeLabel = new JLabel("Bienvenue au Parc de l'ECE  !", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("SansSerif", Font.BOLD, 40));
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setOpaque(true);
        welcomeLabel.setBackground(new Color(0, 120, 215)); // bleu doux
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        add(welcomeLabel, BorderLayout.NORTH);

        // Panel central pour les boutons
        JPanel centerPanel = new JPanel(new GridLayout(2, 2, 30, 30));
        centerPanel.setBackground(new Color(240, 248, 255)); // bleu très clair
        centerPanel.setBorder(BorderFactory.createEmptyBorder(80, 150, 80, 150));

        JButton registerBtn = createStyledButton("S'inscrire");
        JButton guestBtn = createStyledButton("Continuer en tant qu'invité");
        JButton loginBtn = createStyledButton("Se connecter");
        JButton adminBtn = createStyledButton("Accès administrateur");

        centerPanel.add(registerBtn);
        centerPanel.add(guestBtn);
        centerPanel.add(loginBtn);
        centerPanel.add(adminBtn);

        add(centerPanel, BorderLayout.CENTER);

        // Actions boutons
        registerBtn.addActionListener((ActionEvent e) -> {
            new InscriptionGUI().setVisible(true);
            dispose();
        });

        guestBtn.addActionListener((ActionEvent e) -> {
            new PageCalendrier(null).setVisible(true);
            dispose();
        });

        loginBtn.addActionListener((ActionEvent e) -> {
            new ConnexionGUI().setVisible(true);
            dispose();
        });

        adminBtn.addActionListener((ActionEvent e) -> {
            String motDePasse = JOptionPane.showInputDialog(this, "Veuillez entrer le mot de passe administrateur :");
            if (motDePasse != null && motDePasse.equals("admin")) {
                new AdminGUI().setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Mot de passe incorrect !");
            }
        });
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.BOLD, 22));
        button.setBackground(new Color(0, 150, 136)); // vert d'eau moderne
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AccueilGUI().setVisible(true));
    }
}
