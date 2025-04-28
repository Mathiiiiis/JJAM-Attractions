package view;

import dao.AttractionDAOImpl;
import model.Attraction;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class ModifierAttractionDialog extends JDialog {

    private JComboBox<String> attractionComboBox;
    private JTextField typeField;
    private JTextArea descriptionArea;
    private final AttractionDAOImpl attractionDAO = new AttractionDAOImpl();

    public ModifierAttractionDialog(JFrame parent) {
        super(parent, "Modifier une Attraction", true);
        setSize(500, 400);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        // Panel principal
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // Label
        JLabel label = new JLabel("Sélectionnez une attraction à modifier :");
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(label);
        panel.add(Box.createVerticalStrut(10));

        // ComboBox avec tous les noms d'attractions
        attractionComboBox = new JComboBox<>();
        remplirAttractions();
        panel.add(attractionComboBox);
        panel.add(Box.createVerticalStrut(20));

        // Champ pour le type
        panel.add(new JLabel("Nouveau Type :"));
        typeField = new JTextField(20);
        panel.add(typeField);
        panel.add(Box.createVerticalStrut(10));

        // Champ pour la description
        panel.add(new JLabel("Nouvelle Description :"));
        descriptionArea = new JTextArea(5, 20);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(descriptionArea);
        panel.add(scrollPane);
        panel.add(Box.createVerticalStrut(20));

        // Bouton enregistrer
        JButton enregistrerBtn = new JButton("Enregistrer");
        enregistrerBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        enregistrerBtn.addActionListener(e -> enregistrerModification());

        panel.add(enregistrerBtn);

        add(panel, BorderLayout.CENTER);
    }

    private void remplirAttractions() {
        List<Attraction> attractions = attractionDAO.getAllAttractions();
        if (attractions != null && !attractions.isEmpty()) {
            List<String> nomsAttractions = attractions.stream()
                    .map(Attraction::getNom)
                    .collect(Collectors.toList());
            for (String nom : nomsAttractions) {
                attractionComboBox.addItem(nom);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Aucune attraction disponible !");
        }
    }

    private void enregistrerModification() {
        String nomAttractionSelectionnee = (String) attractionComboBox.getSelectedItem();
        if (nomAttractionSelectionnee != null) {
            Attraction attraction = attractionDAO.getAttractionByName(nomAttractionSelectionnee);
            if (attraction != null) {
                String nouveauType = typeField.getText().trim();
                String nouvelleDescription = descriptionArea.getText().trim();

                if (!nouveauType.isEmpty() && !nouvelleDescription.isEmpty()) {
                    attraction.setType(nouveauType);
                    attraction.setDescription(nouvelleDescription);
                    attractionDAO.updateTypeAndDescription(attraction);

                    JOptionPane.showMessageDialog(this, "Attraction modifiée avec succès !");
                    this.dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs !");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Erreur : Attraction introuvable !");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner une attraction !");
        }
    }
}
