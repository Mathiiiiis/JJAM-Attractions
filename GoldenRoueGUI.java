package view;

import model.Client;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class GoldenRoueGUI extends JPanel {
    private Client client;

    public GoldenRoueGUI(Client client) {
        this.client = client;
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("🎡 Golden Roue", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 26));
        add(titleLabel, BorderLayout.NORTH);

        JButton spinButton = new JButton("Tenter ma chance !");
        JLabel resultLabel = new JLabel("", SwingConstants.CENTER);

        spinButton.addActionListener(e -> {
            Random random = new Random();
            int result = random.nextInt(1_000_000); // 1 chance sur 1 million
            if (result == 0) {
                resultLabel.setText("🎉 Félicitations ! Vous avez gagné un accès gratuit !");
            } else {
                resultLabel.setText("Dommage, retentez demain !");
            }
        });

        JPanel centerPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        centerPanel.add(spinButton);
        centerPanel.add(resultLabel);

        add(centerPanel, BorderLayout.CENTER);
    }
}
