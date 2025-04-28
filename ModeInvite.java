package view;

import javax.swing.*;
import java.awt.*;
//ce fichier va nous être utile pour:
public class ModeInvite extends JFrame {

    public ModeInvite() {
        setTitle("Mode Invité - Bienvenue");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JLabel label = new JLabel("Bienvenue en Mode Invité !");
        label.setFont(new Font("SansSerif", Font.BOLD, 22));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton commencerBtn = new JButton("Commencer la Réservation ➡️");
        commencerBtn.setFont(new Font("SansSerif", Font.PLAIN, 18));
        commencerBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        commencerBtn.addActionListener(e -> {
            new ChoixParcInviteGUI().setVisible(true);
            dispose();
        });

        JButton retourBtn = new JButton("Retour à l'Accueil");
        retourBtn.setFont(new Font("SansSerif", Font.PLAIN, 16));
        retourBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        retourBtn.addActionListener(e -> {
            new AccueilGUI().setVisible(true);
            dispose();
        });

        panel.add(label);
        panel.add(Box.createVerticalStrut(30));
        panel.add(commencerBtn);
        panel.add(Box.createVerticalStrut(15));
        panel.add(retourBtn);

        add(panel);
        setVisible(true);
    }
}
