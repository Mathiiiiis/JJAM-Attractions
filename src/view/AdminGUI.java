package view;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import dao.AttractionDAOImpl;
import model.Attraction;

public class AdminGUI extends JFrame {
    private JTextField nomField, parcField, prixField, placesField;
    private JTextArea descField;
    private JTable attractionTable;
    private DefaultTableModel tableModel;
    private AttractionDAOImpl attractionDAO;

    public AdminGUI() {
        super("Interface Administrateur");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        attractionDAO = new AttractionDAOImpl();

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(240, 240, 255));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 100, 30, 100));

        JLabel title = new JLabel("Gestion des Attractions");
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        nomField = new JTextField(20);
        parcField = new JTextField(20);
        prixField = new JTextField(10);
        placesField = new JTextField(10);
        descField = new JTextArea(3, 20);

        JButton addAttractionBtn = new JButton("Ajouter l'attraction");
        addAttractionBtn.setBackground(new Color(0, 120, 215));
        addAttractionBtn.setForeground(Color.WHITE);
        addAttractionBtn.setFont(new Font("SansSerif", Font.BOLD, 16));
        addAttractionBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        addAttractionBtn.addActionListener(e -> addAttraction());

        JButton modifyAttractionBtn = new JButton("Modifier une Attraction");
        modifyAttractionBtn.setBackground(new Color(0, 120, 215));
        modifyAttractionBtn.setForeground(Color.WHITE);
        modifyAttractionBtn.setFont(new Font("SansSerif", Font.BOLD, 16));
        modifyAttractionBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        modifyAttractionBtn.addActionListener(e -> openModifyDialog());

        JButton deleteAttractionBtn = new JButton("Supprimer l'Attraction sélectionnée");
        deleteAttractionBtn.setBackground(new Color(0, 120, 215));
        deleteAttractionBtn.setForeground(Color.WHITE);
        deleteAttractionBtn.setFont(new Font("SansSerif", Font.BOLD, 16));
        deleteAttractionBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        deleteAttractionBtn.addActionListener(e -> deleteSelectedAttraction());

        tableModel = new DefaultTableModel(new Object[]{"Nom", "Parc", "Prix (€)", "Places", "Description"}, 0);
        attractionTable = new JTable(tableModel);
        attractionTable.setRowHeight(30);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        attractionTable.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        attractionTable.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);

        JScrollPane tableScroll = new JScrollPane(attractionTable);

        JButton viewClientsBtn = new JButton("Voir les Clients");
        viewClientsBtn.setBackground(new Color(0, 120, 215));
        viewClientsBtn.setForeground(Color.WHITE);
        viewClientsBtn.setFont(new Font("SansSerif", Font.BOLD, 16));
        viewClientsBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        viewClientsBtn.addActionListener(e -> new ClientListGUI().setVisible(true));

        JButton viewStatsBtn = new JButton("Voir Statistiques Occupation");
        viewStatsBtn.setBackground(new Color(0, 120, 215));
        viewStatsBtn.setForeground(Color.WHITE);
        viewStatsBtn.setFont(new Font("SansSerif", Font.BOLD, 16));
        viewStatsBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        viewStatsBtn.addActionListener(e -> new StatistiquesGUI().setVisible(true));

        panel.add(title);
        panel.add(Box.createVerticalStrut(20));
        panel.add(viewClientsBtn);
        panel.add(Box.createVerticalStrut(10));
        panel.add(viewStatsBtn);
        panel.add(Box.createVerticalStrut(10));
        panel.add(modifyAttractionBtn);
        panel.add(Box.createVerticalStrut(10));
        panel.add(deleteAttractionBtn);
        panel.add(Box.createVerticalStrut(20));
        panel.add(new JLabel("Nom de l'attraction:"));
        panel.add(nomField);
        panel.add(new JLabel("Parc:"));
        panel.add(parcField);
        panel.add(new JLabel("Prix de l'attraction:"));
        panel.add(prixField);
        panel.add(new JLabel("Nombre de places:"));
        panel.add(placesField);
        panel.add(new JLabel("Description:"));
        panel.add(new JScrollPane(descField));
        panel.add(Box.createVerticalStrut(20));
        panel.add(addAttractionBtn);
        panel.add(Box.createVerticalStrut(30));
        panel.add(new JLabel("Attractions Disponibles:"));
        panel.add(tableScroll);

        add(panel);
        refreshTable();
    }

    private void addAttraction() {
        try {
            String nom = nomField.getText();
            String parc = parcField.getText();
            double prix = Double.parseDouble(prixField.getText());
            int places = Integer.parseInt(placesField.getText());
            String description = descField.getText();

            Attraction newAttraction = new Attraction(nom, parc, prix, description, places);
            attractionDAO.addAttraction(newAttraction);

            JOptionPane.showMessageDialog(this, "Attraction ajoutée avec succès !");
            refreshTable();

            nomField.setText("");
            parcField.setText("");
            prixField.setText("");
            placesField.setText("");
            descField.setText("");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Veuillez entrer des valeurs valides.");
        }
    }

    private void openModifyDialog() {
        String attractionName = JOptionPane.showInputDialog(this, "Nom de l'attraction à modifier:");
        if (attractionName != null && !attractionName.trim().isEmpty()) {
            Attraction attraction = attractionDAO.getAttractionByName(attractionName.trim());
            if (attraction != null) {
                String newPrixStr = JOptionPane.showInputDialog(this, "Nouveau prix:", attraction.getPrix_base());
                String newPlacesStr = JOptionPane.showInputDialog(this, "Nouveau nombre de places:", attraction.getPlaces());
                String newDesc = JOptionPane.showInputDialog(this, "Nouvelle description:", attraction.getDescription());

                try {
                    double newPrix = Double.parseDouble(newPrixStr);
                    int newPlaces = Integer.parseInt(newPlacesStr);

                    attraction.setPrix_base(newPrix);
                    attraction.setPlaces(newPlaces);
                    attraction.setDescription(newDesc);

                    attractionDAO.updateAttraction(attraction);
                    JOptionPane.showMessageDialog(this, "Attraction modifiée avec succès !");
                    refreshTable();
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Valeurs invalides.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Attraction non trouvée !");
            }
        }
    }

    private void deleteSelectedAttraction() {
        int selectedRow = attractionTable.getSelectedRow();
        if (selectedRow != -1) {
            String nom = (String) tableModel.getValueAt(selectedRow, 0);
            Attraction attraction = attractionDAO.getAttractionByName(nom);
            if (attraction != null) {
                int confirm = JOptionPane.showConfirmDialog(this, "Confirmez-vous la suppression de: " + nom + " ?", "Confirmation", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    attractionDAO.deleteAttraction(attraction.getId());
                    JOptionPane.showMessageDialog(this, "Attraction supprimée avec succès !");
                    refreshTable();
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner une attraction à supprimer.");
        }
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        List<Attraction> attractions = attractionDAO.getAllAttractions();
        for (Attraction attraction : attractions) {
            tableModel.addRow(new Object[]{
                    attraction.getNom(),
                    attraction.getParc(),
                    String.format("%.2f €", attraction.getPrix_base()),
                    attraction.getPlaces(),
                    attraction.getDescription()
            });
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AdminGUI().setVisible(true));
    }
}
