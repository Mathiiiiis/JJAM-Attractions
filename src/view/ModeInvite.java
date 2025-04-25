
package view;

import javax.swing.*;
import java.awt.*;
import view.PageCalendrier;

public class ModeInvite {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PageCalendrier calendrierInvite = new PageCalendrier(null);
            calendrierInvite.setTitle("Calendrier des Attractions - Mode Invité");
            calendrierInvite.setVisible(true);
        });
    }
}
