package view;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import dao.ClientDAO;
import model.Client;
import javax.swing.table.DefaultTableModel //

public class ClientListGUI extends JFrame {

    public ClientListGUI() {
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Récupérer tous les clients
        ClientDAO clientDAO = new ClientDAO();
        List<Client> clients = clientDAO.getAllClients();

        // Créer un tableau pour afficher les clients
        String[] columnNames = {"ID", "Nom", "Email", "Profil"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        // Si la liste des clients est vide, afficher un message et ne pas créer de table
        if (clients.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Aucun client trouvé.");
            // Créer une ligne vide pour informer l'utilisateur
            model.addRow(new Object[]{"", "Aucun client", "", ""});
        } else {
            for (Client client : clients) {
                model.addRow(new Object[]{
                        client.getId(),
                        client.getNom(),
                        client.getEmail(),
                        client.getProfil().name()
                });
            }
        }

        // Créer une table et l'afficher
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);

        setVisible(true);
    }
}
