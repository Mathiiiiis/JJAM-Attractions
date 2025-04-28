package view;

import javax.swing.*;
import java.awt.*;
//
public class AccueilGUI extends JFrame {


    

    public AccueilGUI() {
        super("Accueil - Parc d'Attractions");
        setSize(800, 600); // 📏 Plus spacieux
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // 🌟 Partie haute - Titre
        JLabel welcomeLabel = new JLabel("🎡 Bienvenue au Parc de l'ECE 🎢", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("SansSerif", Font.BOLD, 32));
        welcomeLabel.setForeground(new Color(0, 120, 215));
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(30, 10, 20, 10));
        add(welcomeLabel, BorderLayout.NORTH);

        // 🎨 Panel centre pour logo/image
        JPanel imagePanel = new JPanel();
        imagePanel.setBackground(Color.WHITE);
        JLabel imageLabel = new JLabel();
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/img/attraction.png")); // 🔥 Mets une image ici
            Image scaledImage = icon.getImage().getScaledInstance(300, 200, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(scaledImage));
        } catch (Exception e) {
            imageLabel.setText("🏰"); // 🔥 Emoji par défaut si pas d'image
            imageLabel.setFont(new Font("SansSerif", Font.PLAIN, 100));
        }
        imagePanel.add(imageLabel);
        add(imagePanel, BorderLayout.CENTER);

        // 📚 Panel bas pour boutons
        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(30, 80, 30, 80));
        buttonPanel.setBackground(new Color(245, 245, 245));

        JButton registerBtn = createStyledButton("📝 S'inscrire");
        JButton guestBtn = createStyledButton("🎟️ Continuer en tant qu'invité");
        JButton loginBtn = createStyledButton("🔐 Se connecter");
        JButton adminBtn = createStyledButton("⚙️ Accès administrateur");

        buttonPanel.add(registerBtn);
        buttonPanel.add(guestBtn);
        buttonPanel.add(loginBtn);
        buttonPanel.add(adminBtn);

        add(buttonPanel, BorderLayout.SOUTH);

        // 🎯 Actions des boutons
        registerBtn.addActionListener(e -> {
            new InscriptionGUI().setVisible(true);
            dispose();
        });

        guestBtn.addActionListener(e -> {
            new ChoixParcInviteGUI().setVisible(true);
            dispose();
        });

        loginBtn.addActionListener(e -> {
            new ConnexionGUI().setVisible(true);
            dispose();
        });

        adminBtn.addActionListener(e -> {
            String motDePasse = JOptionPane.showInputDialog(this, "Mot de passe administrateur :");
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
        button.setFont(new Font("SansSerif", Font.BOLD, 18));
        button.setBackground(new Color(0, 150, 136));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Curseur main
        button.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        return button;
    }

    public static void main(String[] args) {
        try {
            // Si tu as FlatLaf tu peux décommenter :
            // UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatLightLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> new AccueilGUI().setVisible(true));
    }
}
