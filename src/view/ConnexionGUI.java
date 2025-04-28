
package view;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.time.LocalDate;
import dao.ClientDAO;
import model.Client;

public class ConnexionGUI extends JFrame {
    public ConnexionGUI() {
        super("Connexion Client");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(245, 245, 255));
        panel.setBorder(BorderFactory.createEmptyBorder(59, 150, 60, 150));

        JLabel title = new JLabel("Connexion Client");
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField emailField = new JTextField(20);
        JPasswordField passField = new JPasswordField(20);
        JButton loginBtn = new JButton("Se connecter");

        loginBtn.setBackground(new Color(0, 120, 215));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFont(new Font("SansSerif", Font.BOLD, 16));
        loginBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        loginBtn.addActionListener(e -> {
            ClientDAO clientDAO = new ClientDAO();
            Client client = clientDAO.getClientByEmailAndPassword(emailField.getText(), new String(passField.getPassword()));

            if (client != null) {
                JOptionPane.showMessageDialog(this, "Connexion réussie !");
                new PageCalendrier(client).setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Identifiants incorrects !");
            }
        });

        panel.add(title);
        panel.add(Box.createVerticalStrut(20));
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Mot de passe:"));
        panel.add(passField);
        panel.add(Box.createVerticalStrut(20));
        panel.add(loginBtn);

        add(panel);
    }
}
