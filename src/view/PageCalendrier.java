
package view;

import controller.CalendrierController;
import model.Client;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class PageCalendrier extends JFrame {
    private final Client client;
    private JPanel calendrierContainer;
    private JComboBox<String> comboMois;
    private JSpinner spinnerAnnee;

    public PageCalendrier(Client client) {
        super("Calendrier des Attractions");
        this.client = client;

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());

        String[] mois = {"Janvier", "Février", "Mars", "Avril", "Mai", "Juin",
                "Juillet", "Août", "Septembre", "Octobre", "Novembre", "Décembre"};
        comboMois = new JComboBox<>(mois);
        spinnerAnnee = new JSpinner(new SpinnerNumberModel(LocalDate.now().getYear(), 2000, 2100, 1));

        JButton chargerBtn = new JButton("Afficher le mois");
        chargerBtn.addActionListener(e -> chargerCalendrier());

        topPanel.add(new JLabel("Mois:"));
        topPanel.add(comboMois);
        topPanel.add(new JLabel("Année:"));
        topPanel.add(spinnerAnnee);
        topPanel.add(chargerBtn);

        calendrierContainer = new JPanel();
        calendrierContainer.setLayout(new BorderLayout());

        setLayout(new BorderLayout());

        JButton retourBtn = new JButton("Retour à l'accueil");
        retourBtn.addActionListener(e -> {
            new MenuPrincipal().setVisible(true);
            dispose();
        });
        topPanel.add(retourBtn);
        add(topPanel, BorderLayout.NORTH);


        add(calendrierContainer, BorderLayout.CENTER);

        // Légende et restauration
        JPanel infoPanel = new JPanel(new GridLayout(2, 1));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        infoPanel.setBackground(Color.WHITE);

        JPanel legendePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        legendePanel.add(creerLegende("Parc fermé", Color.RED));
        legendePanel.add(creerLegende("Week-end", Color.CYAN));
        legendePanel.add(creerLegende("Vacances scolaires", Color.YELLOW));

        JPanel restoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel restoInfo = new JLabel("🍴 Restauration disponible : 3 restaurants ouverts — 11h45 à 15h (haute saison) / 11h45 à 14h (basse saison)");
        restoPanel.add(restoInfo);

        infoPanel.add(legendePanel);
        infoPanel.add(restoPanel);
        add(infoPanel, BorderLayout.SOUTH);


        // Charger le mois actuel
        comboMois.setSelectedIndex(LocalDate.now().getMonthValue() - 1);
        chargerCalendrier();
    }

    private void chargerCalendrier() {
        int mois = comboMois.getSelectedIndex() + 1;
        int annee = (Integer) spinnerAnnee.getValue();
        YearMonth ym = YearMonth.of(annee, mois);

        CalendrierController controller = new CalendrierController();
        CalendrierPanel panel = controller.creerCalendrier(annee, mois);

        // Génère les vacances (fixes pour ce projet)
        Set<LocalDate> vacances = genererVacances(annee);

        for (Component comp : panel.getComponents()) {
            if (comp instanceof JButton bouton) {
                try {
                    int jour = Integer.parseInt(bouton.getText());
                    LocalDate date = ym.atDay(jour);

                    // Appliquer code couleur
                    if (date.getDayOfWeek().getValue() == 1) {
                        bouton.setBackground(Color.RED);
                        bouton.setToolTipText("Parc fermé");
                    } else if (date.getDayOfWeek().getValue() >= 6) {
                        bouton.setBackground(Color.CYAN);
                        bouton.setToolTipText("Week-end");
                    } else if (vacances.contains(date)) {
                        bouton.setBackground(Color.YELLOW);
                        bouton.setToolTipText("Vacances scolaires");
                    }

                    // Action au clic
                    bouton.addActionListener(e -> {
                        if (date.getDayOfWeek().getValue() == 1) {
                            JOptionPane.showMessageDialog(this,
                                    "Le parc est fermé ce jour-là (lundi). Veuillez choisir une autre date.");
                        } else {
                            new ReservationGUI(client, new ArrayList<>(), date).setVisible(true);
                            dispose();
                        }
                    });

                } catch (NumberFormatException ignored) {}
            }
        }

        calendrierContainer.removeAll();
        calendrierContainer.add(panel, BorderLayout.CENTER);
        calendrierContainer.revalidate();
        calendrierContainer.repaint();
    }

    private Set<LocalDate> genererVacances(int annee) {
        Set<LocalDate> vacances = new HashSet<>();

        // Noël : dernière quinzaine de décembre
        vacances.addAll(genererPeriode(LocalDate.of(annee, 12, 18), LocalDate.of(annee, 12, 31)));

        // Février : 2 semaines autour du 10
        vacances.addAll(genererPeriode(LocalDate.of(annee, 2, 5), LocalDate.of(annee, 2, 18)));

        // Pâques : début avril (2 semaines)
        vacances.addAll(genererPeriode(LocalDate.of(annee, 4, 8), LocalDate.of(annee, 4, 21)));

        // Été : juillet & août complets
        vacances.addAll(genererPeriode(LocalDate.of(annee, 7, 1), LocalDate.of(annee, 8, 31)));

        // Halloween : fin octobre
        vacances.addAll(genererPeriode(LocalDate.of(annee, 10, 24), LocalDate.of(annee, 10, 31)));

        return vacances;
    }

    private Set<LocalDate> genererPeriode(LocalDate debut, LocalDate fin) {
        Set<LocalDate> dates = new HashSet<>();
        for (LocalDate date = debut; !date.isAfter(fin); date = date.plusDays(1)) {
            dates.add(date);
        }
        return dates;
    }


    private JPanel creerLegende(String texte, Color couleur) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel couleurLabel = new JLabel("   ");
        couleurLabel.setOpaque(true);
        couleurLabel.setBackground(couleur);
        couleurLabel.setPreferredSize(new Dimension(20, 20));
        panel.add(couleurLabel);
        panel.add(new JLabel(texte));
        panel.setOpaque(false);
        return panel;
    }
}

