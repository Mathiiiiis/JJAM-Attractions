package controller;

import model.Attraction;//ce fichier va nous être utile pour:
import model.Client;
import model.Parc;
import model.Profil;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.MonthDay;
import java.util.List;

public class ReservationController {

    // ✅ Calcul du prix final pour une attraction précise (billet à l'unité)
    public double calculerPrix(Attraction attraction, Client client, LocalDate date, boolean isPromo) {
        double base = attraction.getPrixBase();
        double prixFinal = base;

        if (client.getProfil() == Profil.ENFANT) {
            prixFinal *= 0.6;
        } else if (client.getProfil() == Profil.SENIOR) {
            prixFinal *= 0.7;
        }

        if (isPromo) {
            prixFinal += prixFinal * 1.6;
        }

        return prixFinal;
    }

    // ✅ Calcul du prix final pour une attraction selon uniquement un Profil
    public double calculerPrix(Attraction attraction, Profil profil, LocalDate date, boolean isPromo) {
        double base = attraction.getPrixBase();
        double prixFinal = base;

        switch (profil) {
            case ENFANT -> prixFinal *= 0.5;
            case SENIOR -> prixFinal *= 0.7;
            default -> {} // Adulte : pas de réduction spéciale
        }

        if (isPromo) {
            prixFinal += prixFinal * 1.6;
        }

        return prixFinal;
    }

    // ✅ Calcul du prix global en cumulant les prix d'entrée des parcs sélectionnés
    public static double calculerPrixDeBase(List<Parc> parcsSelectionnes) {
        double total = 0;
        for (Parc parc : parcsSelectionnes) {
            total += parc.getPrixEntree();
        }

        int nombreParcs = parcsSelectionnes.size();

        // 🔥 Remises selon nombre de parcs choisis
        if (nombreParcs == 2) {
            total *= 0.9; // 10% de remise
        } else if (nombreParcs == 3) {
            total *= 0.7; // 30% de remise
        } else if (nombreParcs == 4) {
            total *= 0.6; // 40% de remise
        }

        return total;
    }

    // ✅ Application de réduction selon le profil du client
    public static double appliquerReduction(double prixBase, Profil profil) {
        return switch (profil) {
            case ENFANT -> prixBase * 0.5;
            case SENIOR -> prixBase * 0.7;
            case REGULIER -> prixBase;
        };
    }

    // ✅ Supplément pour l'option Fast Pass
    public static double appliquerFastPass(double prixSansFastPass) {
        return prixSansFastPass * 1.6;
    }

    // ✅ Vérifie si la date est un jour spécial comme Halloween (par exemple)
    private boolean estJourSpecial(LocalDate date) {
        MonthDay halloween = MonthDay.of(10, 31);
        return MonthDay.from(date).equals(halloween);
    }

    // ✅ Vérifie si la date tombe sur un week-end
    private boolean estWeekend(LocalDate date) {
        DayOfWeek day = date.getDayOfWeek();
        return day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY;
    }
}
