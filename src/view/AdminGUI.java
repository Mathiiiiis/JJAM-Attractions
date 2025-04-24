package view;

import javax.swing.*;
import java.awt.*;
import dao.ClientDAO;  // Pour récupérer les informations des clients
import model.Client;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import dao.AttractionDAOImpl;  // Pour l'accès aux méthodes d'AttractionDAOImpl
import model.Attraction; // Pour utiliser la classe Attraction


public class AdminGUI extends JFrame {
    public AdminGUI() {
        super("Interface Administrateur");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panneau principal
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(240, 240, 255));
        panel.setBorder(BorderFactory.createEmptyBorder(60, 150, 60, 150));

        JLabel title = new JLabel("Gestion des Attractions");
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Bouton pour voir la liste des clients
        JButton viewClientsBtn = new JButton("Voir les Clients");
        viewClientsBtn.setBackground(new Color(0, 120, 215));
        viewClientsBtn.setForeground(Color.WHITE);
        viewClientsBtn.setFont(new Font("SansSerif", Font.BOLD, 16));
        viewClientsBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        viewClientsBtn.addActionListener(e -> {
            // Ouvrir une nouvelle fenêtre pour afficher les clients inscrits
            new ClientListGUI().setVisible(true);
        });

        // Ajouter une attraction
        JTextField nomField = new JTextField(20);
        JTextField parcField = new JTextField(20);
        JTextField prixField = new JTextField(10);
        JTextArea descField = new JTextArea(3, 20);

        JButton addAttractionBtn = new JButton("Ajouter l'attraction");

        addAttractionBtn.setBackground(new Color(0, 120, 215));
        addAttractionBtn.setForeground(Color.WHITE);
        addAttractionBtn.setFont(new Font("SansSerif", Font.BOLD, 16));
        addAttractionBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        addAttractionBtn.addActionListener(e -> {
            try {
                String nom = nomField.getText();
                String parc = parcField.getText();
                double prix = Double.parseDouble(prixField.getText());
                String description = descField.getText();

                // Insérer l'attraction dans la base de données
                AttractionDAOImpl attractionDAO = new AttractionDAOImpl();
                attractionDAO.addAttraction(new Attraction(nom, parc, prix, description));

                JOptionPane.showMessageDialog(this, "Attraction ajoutée avec succès !");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Veuillez entrer un prix valide.");
            }
        });

        panel.add(title);
        panel.add(Box.createVerticalStrut(20));
        panel.add(viewClientsBtn);  // Ajouter le bouton pour voir les clients
        panel.add(Box.createVerticalStrut(20));
        panel.add(new JLabel("Nom de l'attraction:"));
        panel.add(nomField);
        panel.add(new JLabel("Parc:"));
        panel.add(parcField);
        panel.add(new JLabel("Prix de l'attraction:"));
        panel.add(prixField);
        panel.add(new JLabel("Description:"));
        panel.add(new JScrollPane(descField));
        panel.add(Box.createVerticalStrut(20));
        panel.add(addAttractionBtn);

        add(panel);
    }

    // Classe interne pour afficher la liste des clients
    class ClientListGUI extends JFrame {
        public ClientListGUI() {
            setTitle("Liste des Clients");
            setSize(800, 600);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLocationRelativeTo(null);

            ClientDAO clientDAO = new ClientDAO();
            List<Client> clients = clientDAO.getAllClients();

            String[] columnNames = {"ID", "Nom", "Email", "Profil"};
            Object[][] data = new Object[clients.size()][4];

            for (int i = 0; i < clients.size(); i++) {
                Client client = clients.get(i);
                data[i][0] = client.getId();
                data[i][1] = client.getNom();
                data[i][2] = client.getEmail();
                data[i][3] = client.getProfil().name();
            }

            JTable clientTable = new JTable(data, columnNames);
            JScrollPane scrollPane = new JScrollPane(clientTable);
            add(scrollPane);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AdminGUI().setVisible(true));
    }
}
