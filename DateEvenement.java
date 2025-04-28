
package model;
//ce fichier va nous être utile pour:
import java.time.LocalDate;

public class DateEvenement {
    private LocalDate date;
    private String type; // haute_saison, basse_saison, special, ferme
    private String description;

    public DateEvenement(LocalDate date, String type, String description) {
        this.date = date;
        this.type = type;
        this.description = description;
    }

    public LocalDate getDate() { return date; }
    public String getType() { return type; }
    public String getDescription() { return description; }
}
