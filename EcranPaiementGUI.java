package view;

import javax.swing.*;
import java.awt.*;

public class EcranPaiementGUI extends JFrame {
    private JProgressBar progressBar;
    private JLabel messageLabel;
    private Timer timer;
    private int progress = 0;

    public EcranPaiementGUI(Runnable onFinish, double montant) {
        setTitle("Paiement en cours");
        setSize(400, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JLabel titre = new JLabel("\uD83D\uDCB3 Traitement du paiement de " + String.format("%.2f", montant) + " €", SwingConstants.CENTER);
        titre.setFont(new Font("SansSerif", Font.BOLD, 18));
        titre.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(titre, BorderLayout.NORTH);

        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        add(progressBar, BorderLayout.CENTER);

        messageLabel = new JLabel("Connexion sécurisée en cours...", SwingConstants.CENTER);
        messageLabel.setFont(new Font("SansSerif", Font.ITALIC, 14));
        messageLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(messageLabel, BorderLayout.SOUTH);

        setVisible(true);

        timer = new Timer(50, e -> {
            progress++;
            progressBar.setValue(progress);

            if (progress == 30) {
                messageLabel.setText("Validation auprès de votre banque...");
            } else if (progress == 60) {
                messageLabel.setText("Finalisation de votre paiement...");
            } else if (progress >= 100) {
                timer.stop();
                dispose();
                onFinish.run();
            }
        });
        timer.start();
    }
}
