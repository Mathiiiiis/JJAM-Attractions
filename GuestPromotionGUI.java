package view;

import javax.swing.*;
import java.awt.*;

public class GuestPromotionGUI extends JPanel {

    public GuestPromotionGUI() {
        setLayout(new BorderLayout());

        JLabel title = new JLabel("🎉 Mode Invité - Promotions en Cours", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 26));
        title.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        add(title, BorderLayout.NORTH);

        JTextArea promoArea = new JTextArea();
        promoArea.setEditable(false);
        promoArea.setLineWrap(true);
        promoArea.setWrapStyleWord(true);
        promoArea.setFont(new Font("SansSerif", Font.PLAIN, 16));

        promoArea.setText(
                "🎟️ 20% de réduction sur le Parc Pirates pour les réservations avant fin juin !\n\n" +
                        "🚀 1 FastPass offert pour toute réservation de 3 attractions !\n\n" +
                        "👨‍👩‍👧‍👦 Offre Famille : -10% pour les groupes de 4 personnes ou plus !"
        );

        JScrollPane scrollPane = new JScrollPane(promoArea);
        add(scrollPane, BorderLayout.CENTER);

        // Bouton pour inciter à se connecter
        JButton loginButton = new JButton("🔐 Se connecter pour profiter des promotions");
        loginButton.setFont(new Font("SansSerif", Font.BOLD, 18));
        loginButton.setBackground(new Color(255, 140, 0));
        loginButton.setForeground(Color.WHITE);
        loginButton.addActionListener(e -> {
            new ConnexionGUI().setVisible(true);
            SwingUtilities.getWindowAncestor(this).dispose();
        });

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(loginButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }
}
