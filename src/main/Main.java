package main;

import javax.swing.*;
import view.AccueilGUI;

public class Main {
    public static void main(String[] args) { 
        SwingUtilities.invokeLater(() -> new AccueilGUI().setVisible(true));
    }
}
