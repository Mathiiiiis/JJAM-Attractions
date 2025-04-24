package controller;

import model.Attraction;
import model.Client;
import model.Profil;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.MonthDay;

public class ReservationController {

    public double calculerPrix(Attraction attraction, Client client, LocalDate date, boolean isPromo) {
        double base = attraction.getPrixBase();
        return base; // logique par défaut à adapter selon le client
    }

    public double calculerPrix(Attraction attraction, Profil profil, LocalDate date, boolean isPromo) {
        double base = attraction.getPrixBase();

        return switch (profil) {
            case ENFANT -> base * 0.5;
            case SENIOR -> base * 0.7;
            default -> base;
        };
    }

    private boolean estJourSpecial(LocalDate date) {
        MonthDay halloween = MonthDay.of(10, 31);
        return MonthDay.from(date).equals(halloween);
    }

    private boolean estWeekend(LocalDate date) {
        DayOfWeek d = date.getDayOfWeek();
        return d == DayOfWeek.SATURDAY || d == DayOfWeek.SUNDAY;
    }
}
