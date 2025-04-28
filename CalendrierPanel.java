
package view;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashSet;
import java.util.Set;

public class CalendrierPanel extends JPanel {
    private YearMonth moisAnnee;
    private Set<LocalDate> joursHauteSaison = new HashSet<>();
    private Set<LocalDate> joursBasseSaison = new HashSet<>();
    private Set<LocalDate> joursSpeciaux = new HashSet<>();

    public CalendrierPanel(int annee, int mois) {
        this.moisAnnee = YearMonth.of(annee, mois);
        setLayout(new GridLayout(0, 7)); // 7 jours / semaine
        construireCalendrier();
    }

    private void construireCalendrier() {
        String[] jours = {"Lun", "Mar", "Mer", "Jeu", "Ven", "Sam", "Dim"};
        for (String jour : jours) {
            JLabel label = new JLabel(jour, SwingConstants.CENTER);
            label.setFont(new Font("Arial", Font.BOLD, 12));
            add(label);
        }

        LocalDate premierJour = moisAnnee.atDay(1);
        int decalage = premierJour.getDayOfWeek().getValue() % 7;
        for (int i = 0; i < decalage; i++) {
            add(new JLabel(""));
        }

        for (int jour = 1; jour <= moisAnnee.lengthOfMonth(); jour++) {
            LocalDate date = moisAnnee.atDay(jour);
            JButton boutonJour = new JButton(String.valueOf(jour));

            if (date.getDayOfWeek().getValue() == 1) {
                boutonJour.setBackground(Color.RED);
                boutonJour.setToolTipText("Parc fermé");
            } else if (joursHauteSaison.contains(date)) {
                boutonJour.setBackground(Color.GREEN);
                boutonJour.setToolTipText("Haute saison : 10h30 - 19h");
            } else if (joursBasseSaison.contains(date)) {
                boutonJour.setBackground(Color.LIGHT_GRAY);
                boutonJour.setToolTipText("Basse saison : 10h30 - 18h");
            } else if (date.getDayOfWeek().getValue() >= 6) {
                boutonJour.setBackground(Color.CYAN);
                boutonJour.setToolTipText("Week-end : horaires normaux");
            }
//
            add(boutonJour);
        }
    }

    public void setJoursHauteSaison(Set<LocalDate> jours) { this.joursHauteSaison = jours; }
    public void setJoursBasseSaison(Set<LocalDate> jours) { this.joursBasseSaison = jours; }
    public void setJoursSpeciaux(Set<LocalDate> jours) { this.joursSpeciaux = jours; }
}
