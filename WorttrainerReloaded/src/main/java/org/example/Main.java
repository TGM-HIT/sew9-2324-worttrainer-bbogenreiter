package org.example;

import javax.swing.*;

/**
 * Main-Klasse die den Rechtschreibtrainer startet
 * Autor: Bianca Bogenreiter
 * Version: 2023-09-21
 */
public class Main {
    public static void main(String[] args) {
        //User wird gefragt ob er mit XML oder JSON speichern möchte
        String[] options = {"XML", "JSON"};
        int choice = JOptionPane.showOptionDialog(
                null,
                "Wählen Sie das Speicherformat:",
                "Speicherformat auswählen",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);

        SessionSaver sessionSaver;

        if (choice == 0) { //xml
            sessionSaver = new XmlSessionSaver();
        } else { //json
            sessionSaver = new JsonSessionSaver();
        }

        // Start the Rechtschreibtrainer with the selected session saver
        new Rechtschreibtrainer(1, sessionSaver);
    }
}
