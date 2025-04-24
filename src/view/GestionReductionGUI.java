package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GestionReductionGUI extends JFrame {
    public GestionReductionGUI() {
        super("Gestion des Réductions");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(240, 240, 255));
        panel.setBorder(BorderFactory.createEmptyBorder(60, 150, 60, 150));

        JLabel title = new JLabel("Gérer les Réductions");
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField reductionField = new JTextField(10);
        JComboBox<String> typeBox = new JComboBox<>(new String[]{"Enfant", "Adulte", "Senior"});

        JButton addReductionBtn = new JButton("Ajouter Réduction");

        addReductionBtn.setBackground(new Color(0, 120, 215));
        addReductionBtn.setForeground(Color.WHITE);
        addReductionBtn.setFont(new Font("SansSerif", Font.BOLD, 16));
        addReductionBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        addReductionBtn.addActionListener(e -> {
            try {
                double reduction = Double.parseDouble(reductionField.getText());
                String type = (String) typeBox.getSelectedItem();

                // Ajouter la réduction dans la base de données
                // Code pour insérer la réduction à l'attraction ou au type de client

                JOptionPane.showMessageDialog(this, "Réduction ajoutée avec succès !");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Veuillez entrer une valeur de réduction valide.");
            }
        });

        panel.add(title);
        panel.add(Box.createVerticalStrut(20));
        panel.add(new JLabel("Réduction (%) :"));
        panel.add(reductionField);
        panel.add(new JLabel("Type de billet :"));
        panel.add(typeBox);
        panel.add(Box.createVerticalStrut(20));
        panel.add(addReductionBtn);

        add(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GestionReductionGUI().setVisible(true));
    }
}
