//ce fichier va nous être utile pour:
package controller;

import dao.CalendrierDAO;
import model.DateEvenement;
import view.CalendrierPanel;

import java.time.YearMonth;
import java.util.HashSet;
import java.util.Set;

public class CalendrierController {

    private CalendrierDAO dao = new CalendrierDAO();

    public CalendrierPanel creerCalendrier(int annee, int mois) {
        Set<DateEvenement> dates = dao.chargerDates();

        Set<java.time.LocalDate> haute = new HashSet<>();
        Set<java.time.LocalDate> basse = new HashSet<>();
        Set<java.time.LocalDate> speciaux = new HashSet<>();

        for (DateEvenement d : dates) {
            switch (d.getType()) {
                case "haute_saison" -> haute.add(d.getDate());
                case "basse_saison" -> basse.add(d.getDate());
                case "special" -> speciaux.add(d.getDate());
            }
        }

        CalendrierPanel panel = new CalendrierPanel(annee, mois);
        panel.setJoursHauteSaison(haute);
        panel.setJoursBasseSaison(basse);
        panel.setJoursSpeciaux(speciaux);

        return panel;
    }
}
