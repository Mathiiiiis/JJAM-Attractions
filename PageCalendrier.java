package view;

import dao.ReservationDAO;
import model.Client;
import model.Parc;

import javax.swing.*;
import java.awt.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.MonthDay;
import java.time.YearMonth;
import java.util.*;
import java.util.List;

public class PageCalendrier extends JFrame {
    private JPanel calendarPanel;
    private JComboBox<String> monthComboBox;
    private JSpinner yearSpinner;
    private int currentMonth;
    private int currentYear;
    private List<Parc> parcsSelectionnes;
    private int nombreDeBillets;
    private List<TicketPanel> billets;
    private Client client;
    private HistoriqueGUI historiqueGUI; // 🆕 Pour mise à jour historique

    private Set<LocalDate> joursFermes = new HashSet<>();

    public PageCalendrier(List<Parc> parcsSelectionnes, int nombreDeBillets, List<TicketPanel> billets, Client client, HistoriqueGUI historiqueGUI) {
        this.parcsSelectionnes = parcsSelectionnes;
        this.nombreDeBillets = nombreDeBillets;
        this.billets = billets;
        this.client = client;
        this.historiqueGUI = historiqueGUI; // 🆕

        setTitle("Calendrier de Réservation");
        setSize(1000, 750);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        currentMonth = LocalDate.now().getMonthValue();
        currentYear = LocalDate.now().getYear();

        initUI();
        genererFermeturesAleatoires();
        refreshCalendar();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout());

        JButton prevMonthBtn = new JButton("⬅️ Mois Précédent");
        prevMonthBtn.addActionListener(e -> {
            currentMonth--;
            if (currentMonth < 1) {
                currentMonth = 12;
                currentYear--;
            }
            updateSelectors();
            refreshCalendar();
        });

        JButton nextMonthBtn = new JButton("➡️ Mois Suivant");
        nextMonthBtn.addActionListener(e -> {
            currentMonth++;
            if (currentMonth > 12) {
                currentMonth = 1;
                currentYear++;
            }
            updateSelectors();
            refreshCalendar();
        });

        String[] mois = {"Janvier", "Février", "Mars", "Avril", "Mai", "Juin",
                "Juillet", "Août", "Septembre", "Octobre", "Novembre", "Décembre"};
        monthComboBox = new JComboBox<>(mois);
        monthComboBox.setSelectedIndex(currentMonth - 1);
        monthComboBox.addActionListener(e -> {
            currentMonth = monthComboBox.getSelectedIndex() + 1;
            refreshCalendar();
        });

        yearSpinner = new JSpinner(new SpinnerNumberModel(currentYear, 2000, 2100, 1));
        yearSpinner.addChangeListener(e -> {
            currentYear = (int) yearSpinner.getValue();
            refreshCalendar();
        });

        topPanel.add(prevMonthBtn);
        topPanel.add(monthComboBox);
        topPanel.add(yearSpinner);
        topPanel.add(nextMonthBtn);

        add(topPanel, BorderLayout.NORTH);

        calendarPanel = new JPanel(new GridLayout(0, 7, 5, 5));
        add(calendarPanel, BorderLayout.CENTER);

        add(creerLegende(), BorderLayout.SOUTH);
    }

    private void refreshCalendar() {
        calendarPanel.removeAll();

        String[] jours = {"Lun", "Mar", "Mer", "Jeu", "Ven", "Sam", "Dim"};
        for (String jour : jours) {
            JLabel label = new JLabel(jour, SwingConstants.CENTER);
            label.setFont(new Font("SansSerif", Font.BOLD, 14));
            calendarPanel.add(label);
        }

        YearMonth yearMonth = YearMonth.of(currentYear, currentMonth);
        LocalDate firstDay = yearMonth.atDay(1);
        int dayOfWeek = firstDay.getDayOfWeek().getValue();
        int daysInMonth = yearMonth.lengthOfMonth();

        ReservationDAO reservationDAO = new ReservationDAO();

        for (int i = 1; i < dayOfWeek; i++) {
            calendarPanel.add(new JLabel(""));
        }

        for (int day = 1; day <= daysInMonth; day++) {
            LocalDate date = LocalDate.of(currentYear, currentMonth, day);
            JButton dayButton = new JButton(String.valueOf(day));
            dayButton.setOpaque(true);
            dayButton.setBorderPainted(false);

            boolean isClosed = joursFermes.contains(date);
            boolean isVacation = estVacances(date);
            boolean indisponible = !reservationDAO.verifierDisponibilite(convertirParcsEnAttractions(parcsSelectionnes), date, nombreDeBillets);

            if (isClosed) {
                dayButton.setBackground(Color.RED);
                dayButton.setToolTipText("Jour de fermeture du parc");
            } else if (indisponible) {
                dayButton.setBackground(Color.DARK_GRAY);
                dayButton.setToolTipText("Date complète");
            } else if (isVacation) {
                dayButton.setBackground(new Color(34, 139, 34));
                if (estEvenementSpecial(date) != null) {
                    dayButton.setBackground(Color.MAGENTA);
                    dayButton.setToolTipText(estEvenementSpecial(date));
                }
            } else {
                dayButton.setBackground(new Color(144, 238, 144));
                if (estEvenementSpecial(date) != null) {
                    dayButton.setBackground(Color.MAGENTA);
                    dayButton.setToolTipText(estEvenementSpecial(date));
                }
            }

            LocalDate finalDate = date;
            dayButton.addActionListener(e -> {
                if (isClosed) {
                    JOptionPane.showMessageDialog(this, "Le parc est fermé ce jour-là.");
                } else if (indisponible) {
                    JOptionPane.showMessageDialog(this, "La date est complète pour vos billets.");
                } else {
                    JOptionPane.showMessageDialog(this, "Date sélectionnée : " + finalDate);
                    new ResumeCommandeGUI(client, billets, parcsSelectionnes, finalDate).setVisible(true); // 🆕 historiqueGUI transmis
                    dispose();
                }
            });

            calendarPanel.add(dayButton);
        }

        calendarPanel.revalidate();
        calendarPanel.repaint();
    }

    private List<model.Attraction> convertirParcsEnAttractions(List<Parc> parcs) {
        List<model.Attraction> attractions = new ArrayList<>();
        for (Parc parc : parcs) {
            attractions.add(new model.Attraction(parc.getNom(), parc.getNom(), parc.getPrixEntree(), "Parc"));
        }
        return attractions;
    }

    private boolean estVacances(LocalDate date) {
        MonthDay moisJour = MonthDay.from(date);
        return (moisJour.isAfter(MonthDay.of(6, 30)) && moisJour.isBefore(MonthDay.of(9, 1))) ||
                (moisJour.isAfter(MonthDay.of(12, 18)) || moisJour.isBefore(MonthDay.of(1, 5)));
    }

    private void genererFermeturesAleatoires() {
        Random random = new Random();
        joursFermes.clear();
        for (int month = 1; month <= 12; month++) {
            int tries = 0;
            while (tries < 2) {
                int jour = 1 + random.nextInt(25);
                LocalDate date = LocalDate.of(currentYear, month, jour);
                if (date.getDayOfWeek() != DayOfWeek.SATURDAY && date.getDayOfWeek() != DayOfWeek.SUNDAY) {
                    joursFermes.add(date);
                    tries++;
                }
            }
        }
    }

    private JPanel creerLegende() {
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Légende"));
        panel.setBackground(Color.WHITE);

        panel.add(creerItemLegende("Haute saison (vacances)", new Color(34, 139, 34)));
        panel.add(creerItemLegende("Basse saison", new Color(144, 238, 144)));
        panel.add(creerItemLegende("Parc fermé", Color.RED));
        panel.add(creerItemLegende("Date complète", Color.DARK_GRAY));
        panel.add(creerItemLegende("Événements spéciaux (Feu d'artifice, Parade)", Color.MAGENTA));

        return panel;
    }

    private JPanel creerItemLegende(String texte, Color couleur) {
        JPanel item = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel couleurLabel = new JLabel("     ");
        couleurLabel.setOpaque(true);
        couleurLabel.setBackground(couleur);
        item.add(couleurLabel);
        item.add(new JLabel(texte));
        item.setBackground(Color.WHITE);
        return item;
    }

    private String estEvenementSpecial(LocalDate date) {
        MonthDay md = MonthDay.from(date);
        if (md.equals(MonthDay.of(7, 14))) return "🎆 Feu d'artifice - 14 juillet";
        if (md.equals(MonthDay.of(10, 31))) return "🎃 Nocturne Halloween spéciale !";
        if (md.equals(MonthDay.of(4, 16))) return "🎉 Grande Parade de Pâques !";
        return null;
    }

    private void updateSelectors() {
        monthComboBox.setSelectedIndex(currentMonth - 1);
        yearSpinner.setValue(currentYear);
    }
}
