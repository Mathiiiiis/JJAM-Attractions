package view;

import dao.AttractionDAOImpl;
import dao.ParcDAO;
import dao.ReservationDAO;
import model.Attraction;
import model.Reservation;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AdminGUI extends JFrame {
    private final AttractionDAOImpl attractionDAO = new AttractionDAOImpl();
    private final ReservationDAO reservationDAO = new ReservationDAO();

    public AdminGUI() {
        super("Panel Administrateur - Gestion du Parc");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(new Color(245, 245, 245));

        JLabel title = new JLabel("Interface Administrateur", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 26));
        title.setBorder(BorderFactory.createEmptyBorder(30, 10, 30, 10));
        panelPrincipal.add(title, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(5, 2, 20, 20));
        buttonPanel.setBackground(new Color(245, 245, 245));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 80, 20, 80));

        Color bleuClair = new Color(173, 216, 230);

        JButton viewClientsBtn = createButton("Voir les Clients", bleuClair);
        viewClientsBtn.addActionListener(e -> new ClientListGUI().setVisible(true));

        JButton viewStatsBtn = createButton("Voir Statistiques Occupation", bleuClair);
        viewStatsBtn.addActionListener(e -> new StatistiquesGUI().setVisible(true));

        JButton addParcBtn = createButton("Ajouter un Parc", bleuClair);
        addParcBtn.addActionListener(e -> ajouterParc());

        JButton deleteParcBtn = createButton("Supprimer un Parc", bleuClair);
        deleteParcBtn.addActionListener(e -> supprimerParc());

        JButton addAttractionBtn = createButton("Ajouter une Attraction", bleuClair);
        addAttractionBtn.addActionListener(e -> new AjoutAttractionDialog(this).setVisible(true));

        JButton deleteAttractionBtn = createButton("Supprimer une Attraction", bleuClair);
        deleteAttractionBtn.addActionListener(e -> supprimerAttraction());

        JButton commandesClientsBtn = createButton("Commandes Clients", bleuClair);
        commandesClientsBtn.addActionListener(e -> afficherCommandesClient());

        JButton deconnexionBtn = createButton("Déconnexion", bleuClair);
        deconnexionBtn.addActionListener(e -> {
            this.dispose();
            new AccueilGUI().setVisible(true);
        });

        buttonPanel.add(viewClientsBtn);
        buttonPanel.add(viewStatsBtn);
        buttonPanel.add(addParcBtn);
        buttonPanel.add(deleteParcBtn);
        buttonPanel.add(addAttractionBtn);
        buttonPanel.add(deleteAttractionBtn);
        buttonPanel.add(commandesClientsBtn);
        buttonPanel.add(deconnexionBtn);
        buttonPanel.add(new JLabel());
        buttonPanel.add(new JLabel());

        panelPrincipal.add(buttonPanel, BorderLayout.CENTER);
        add(panelPrincipal);
    }

    private JButton createButton(String text, Color background) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setBackground(background);
        button.setForeground(Color.BLACK);
        button.setFont(new Font("SansSerif", Font.PLAIN, 16));
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        return button;
    }

    private void ajouterParc() {
        JTextField nomField = new JTextField();
        JTextField capaciteField = new JTextField();
        JTextField prixField = new JTextField();

        Object[] fields = {
                "Nom du parc:", nomField,
                "Capacité:", capaciteField,
                "Prix d'entrée:", prixField
        };

        int result = JOptionPane.showConfirmDialog(this, fields, "Ajouter un Parc", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                String nom = nomField.getText();
                int capacite = Integer.parseInt(capaciteField.getText());
                double prix = Double.parseDouble(prixField.getText());
                new ParcDAO().addParc(nom, capacite, prix);
                JOptionPane.showMessageDialog(this, "Parc ajouté avec succès !");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erreur : Données invalides.");
            }
        }
    }

    private void supprimerParc() {
        ParcDAO parcDAO = new ParcDAO();
        List<String> parcs = parcDAO.getAllParcNames();

        String parcASupprimer = (String) JOptionPane.showInputDialog(
                this,
                "Choisissez un parc à supprimer :",
                "Supprimer un Parc",
                JOptionPane.PLAIN_MESSAGE,
                null,
                parcs.toArray(),
                null
        );

        if (parcASupprimer != null) {
            parcDAO.deleteParc(parcASupprimer);
            JOptionPane.showMessageDialog(this, "Parc supprimé avec succès !");
        }
    }

    private void supprimerAttraction() {
        List<Attraction> attractions = attractionDAO.getAllAttractions();
        String[] nomsAttractions = attractions.stream().map(Attraction::getNom).toArray(String[]::new);

        String nomSelectionne = (String) JOptionPane.showInputDialog(
                this,
                "Sélectionnez l'attraction à supprimer :",
                "Supprimer une Attraction",
                JOptionPane.PLAIN_MESSAGE,
                null,
                nomsAttractions,
                null
        );

        if (nomSelectionne != null) {
            Attraction attraction = attractionDAO.getAttractionByName(nomSelectionne);
            if (attraction != null) {
                attractionDAO.deleteAttraction(attraction.getId());
                JOptionPane.showMessageDialog(this, "Attraction supprimée avec succès !");
            } else {
                JOptionPane.showMessageDialog(this, "Attraction non trouvée.");
            }
        }
    }

    private void afficherCommandesClient() {
        List<Integer> clientIDs = reservationDAO.getAllClientIDsWithCommandes();

        if (clientIDs.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Aucun client avec des commandes trouvées.");
            return;
        }

        Integer selectedClientID = (Integer) JOptionPane.showInputDialog(
                this,
                "Sélectionnez un ID client :",
                "Commandes Client",
                JOptionPane.PLAIN_MESSAGE,
                null,
                clientIDs.toArray(),
                clientIDs.get(0)
        );

        if (selectedClientID != null) {
            List<Reservation> commandes = reservationDAO.getCommandesByClientId(selectedClientID);

            if (commandes.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Aucune commande trouvée pour ce client.");
                return;
            }

            String[] columns = {"Date", "Parc", "Nombre de Tickets"};
            DefaultTableModel model = new DefaultTableModel(columns, 0);

            for (Reservation commande : commandes) {
                model.addRow(new Object[]{
                        commande.getDate(),
                        commande.getParcNom(),
                        commande.getNombreTickets()
                });
            }

            JTable table = new JTable(model);
            JScrollPane scrollPane = new JScrollPane(table);

            JFrame frame = new JFrame("Commandes du Client ID: " + selectedClientID);
            frame.setSize(600, 400);
            frame.setLocationRelativeTo(null);
            frame.add(scrollPane);
            frame.setVisible(true);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AdminGUI().setVisible(true));
    }
}
