package view;

import javax.swing.*;
import java.awt.*;

public class EcranPaiement extends JFrame {
    private JProgressBar progressBar;
    private Timer timer;

    public EcranPaiement(double montant) {
        setTitle("Traitement Paiement...");
        setSize(400, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel montantLabel = new JLabel("Montant à payer : " + String.format("%.2f", montant) + " €", SwingConstants.CENTER);
        montantLabel.setFont(new Font("SansSerif", Font.BOLD, 18));

        progressBar = new JProgressBar(0, 100);
        progressBar.setValue(0);
        progressBar.setStringPainted(true);

        setLayout(new BorderLayout());
        add(montantLabel, BorderLayout.NORTH);
        add(progressBar, BorderLayout.CENTER);

        timer = new Timer(50, e -> updateProgress());
        timer.start();
    }

    private void updateProgress() {
        int value = progressBar.getValue();
        if (value < 100) {
            progressBar.setValue(value + 2);
        } else {
            timer.stop();
            JOptionPane.showMessageDialog(this, "✅ Paiement effectué avec succès !");
            dispose();
            new MenuPrincipal().setVisible(true);
        }
    }
}
