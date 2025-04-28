package view;

import model.Client;

import javax.swing.*;
import java.awt.*;

public class PromotionGUI extends JPanel {
    private Client client;

    public PromotionGUI(Client client) {
        this.client = client;
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("🎁 Promotions Actuelles", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 26));
        add(titleLabel, BorderLayout.NORTH);

        JTextArea promoArea = new JTextArea();
        promoArea.setEditable(false);
        promoArea.setText(
                "🎉 Promo du mois pour " + client.getNom() + " :\n" +
                        "- 20% sur tous les Fast Pass !\n" +
                        "- 10% sur le parc des Pirates pour les familles !\n\n" +
                        "Restez connectés pour de nouvelles offres !"
        );

        JScrollPane scrollPane = new JScrollPane(promoArea);
        add(scrollPane, BorderLayout.CENTER);
    }
}
