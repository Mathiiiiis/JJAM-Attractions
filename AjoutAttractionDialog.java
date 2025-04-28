package view;

import dao.AttractionDAOImpl;
import dao.ParcDAO;
import model.Attraction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AjoutAttractionDialog extends JDialog {

    private JTextField nomField;
    private JComboBox<String> parcComboBox;
    private JTextField typeField;
    private JTextArea descriptionArea;

    private AttractionDAOImpl attractionDAO;
    private ParcDAO parcDAO;

    public AjoutAttractionDialog(Frame parent) {
        super(parent, "Ajouter une attraction", true);

        attractionDAO = new AttractionDAOImpl();
        parcDAO = new ParcDAO();

        setLayout(new GridLayout(6, 2, 10, 10));

        // Initialisation des champs
        nomField = new JTextField();
        typeField = new JTextField();
        descriptionArea = new JTextArea(3, 20);

        // Remplissage de la ComboBox des parcs
        List<String> parcs = parcDAO.getAllParcNames();
        parcComboBox = new JComboBox<>(parcs.toArray(new String[0]));

        // Ajout des composants au formulaire
        add(new JLabel("Nom de l'attraction :"));
        add(nomField);

        add(new JLabel("Parc associé :"));
        add(parcComboBox);

        add(new JLabel("Type :"));
        add(typeField);

        add(new JLabel("Description :"));
        add(new JScrollPane(descriptionArea));

        JButton ajouterButton = new JButton("Ajouter l'attraction");
        add(new JLabel()); // espace vide pour aligner
        add(ajouterButton);
//
        // Action sur le bouton
        ajouterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ajouterAttraction();
            }
        });

        pack();
        setLocationRelativeTo(parent);
        setVisible(true);
    }

    private void ajouterAttraction() {
        try {
            String nom = nomField.getText().trim();
            String parc = (String) parcComboBox.getSelectedItem();
            String type = typeField.getText().trim();
            String description = descriptionArea.getText().trim();

            if (nom.isEmpty() || parc == null || type.isEmpty() || description.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Attraction attraction = new Attraction();
            attraction.setNom(nom);
            attraction.setParc(parc);
            attraction.setType(type);
            attraction.setDescription(description);

            attractionDAO.addAttraction(attraction);

            JOptionPane.showMessageDialog(this, "Attraction ajoutée avec succès !");
            dispose(); // Fermer la fenêtre après ajout

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout de l'attraction : " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}
