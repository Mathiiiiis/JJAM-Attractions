package controller;

import model.Attraction;
import model.Client;
import model.Profil;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.MonthDay;

public class ReservationController {

    // Méthode pour calculer le prix d'une attraction en fonction du profil et du Fast Pass
    public double calculerPrix(Attraction attraction, Client client, LocalDate date, boolean isPromo) {
        double base = attraction.getPrixBase();
        double prixFinal = base; // Prix de base sans réduction

        // Appliquer la réduction selon le profil
        if (client.getProfil() == Profil.ENFANT) {
            prixFinal *= 0.5; // Réduction de 50% pour les enfants
        } else if (client.getProfil() == Profil.SENIOR) {
            prixFinal *= 0.7; // Réduction de 30% pour les seniors
        }

        // Appliquer un supplément de 160% si le Fast Pass est sélectionné
        if (isPromo) {
            prixFinal += prixFinal * 1.6;  // Ajoute 160% de supplément pour Fast Pass
        }

        return prixFinal;
    }

    // Méthode pour calculer le prix en fonction du profil (simplifiée sans client)
    public double calculerPrix(Attraction attraction, Profil profil, LocalDate date, boolean isPromo) {
        double base = attraction.getPrixBase();
        double prixFinal = base; // Prix de base

        // Appliquer la réduction selon le profil
        switch (profil) {
            case ENFANT -> prixFinal *= 0.5; // 50% de réduction pour les enfants
            case SENIOR -> prixFinal *= 0.7; // 30% de réduction pour les seniors
            default -> {} // Pas de réduction pour les adultes
        }

        // Ajouter un supplément de 160% pour le Fast Pass
        if (isPromo) {
            prixFinal += prixFinal * 1.6;  // 160% de supplément pour Fast Pass
        }

        return prixFinal;
    }

    // Méthode pour vérifier si c'est un jour spécial (ex : Halloween)
    private boolean estJourSpecial(LocalDate date) {
        MonthDay halloween = MonthDay.of(10, 31);  // Halloween le 31 octobre
        return MonthDay.from(date).equals(halloween);
    }

    // Méthode pour vérifier si c'est un weekend
    private boolean estWeekend(LocalDate date) {
        DayOfWeek d = date.getDayOfWeek();
        return d == DayOfWeek.SATURDAY || d == DayOfWeek.SUNDAY;
    }
}
