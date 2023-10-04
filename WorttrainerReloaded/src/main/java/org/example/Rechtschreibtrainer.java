package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
/**
 * Rechtschreibtrainer-Klasse, die aus den session.json Bilder und Namen lädt und diese als Rechtschreibspiel für Volksschulkinder darstellt.
 * Autor: Bianca Bogenreiter
 * Version: 2023-09-21
 */
public class Rechtschreibtrainer {

    private Paar aktPaar;
    private List<Paar> paarListe;
    private int alleVersuche;
    private int richtigeVersuche;
    //FILE_PATH muss an System angepasst werden
    private final String FILE_PATH = "C:\\Users\\bianc\\OneDrive\\Schule5DHIT\\SEW\\Modul09a\\WorttrainerReloaded\\src\\main\\files\\session.json";

    /**
     * Der Konstruktor, der das Spiel startet
     */
    public Rechtschreibtrainer(int startOrNot) {
        //lädt die Paare aus der session.json und startet.
        if(startOrNot == 1){
            this.loadSession();
            this.showSelection();
        }else if(startOrNot==0){
            this.loadSession();
        }

    }

    /**
     * Wählt ein Paar aus der Liste aus, das am angegebenen Index steht
     * @param index Stelle des zu wählenden Paars
     * @return void
     */
    public void selectPaar(int index) {
        //wenn der Index sinnvoll ist, wählt er das Paar aus
        if (index >= 0 && index < paarListe.size()) {
            aktPaar = paarListe.get(index);
        }
    }

    /**
     * Wählt ein Paar zufällig aus der Liste aus
     * @return void
     */
    public void selectRandomPaar() {
        //wählt ein Zufallspaar aus wenn die Liste NICHT leer ist
        if (!paarListe.isEmpty()) {
            int randomIndex = (int) (Math.random() * paarListe.size());
            aktPaar = paarListe.get(randomIndex);
        }
    }

    /**
     * Überprüft, ob die Antwort des Users mit dem Bildernamen übereinstimmt.
     * @param antwort Input des Users
     * @return boolean zeigt ob die Antwort richtig oder falsch war
     */
    public boolean checkAntwort(String antwort) {
        //wenn ein Paar ausgewählt ist und die Antwort nicht null ist...
        if (aktPaar != null && antwort != null) {
            //überprüft ob der Name stimmt und passt richtige Versuche und alle Versuche an
            if (antwort.equals(aktPaar.getBildname())) {
                richtigeVersuche++;
                alleVersuche++;
                return true;
            } else {
                alleVersuche++;
                return false;
            }
        }
        return false;
    }

    /**
     * Speichert die momentane Paar-Liste in das session.json
     * @return void
     */
    public void saveSession() {
        //mithilfe ObjectMapper von com.fasterxml.jackson.core:jackson-databind:2.15.2 wird die Liste in das json-file gespeichert
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            new ObjectMapper().writeValue(new File(FILE_PATH), this.paarListe);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Lädt die Paar-Liste aus dem session.json
     * @return void
     */
    public void loadSession() {
        //mithilfe ObjectMapper von com.fasterxml.jackson.core:jackson-databind:2.15.2 wird die Liste aus dem json-file geladen
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            File sessionFile = new File(FILE_PATH);

            if (sessionFile.exists()) {
                paarListe = objectMapper.readValue(sessionFile, new TypeReference<List<Paar>>() {});
            } else {
                // falls nicht existent
                paarListe = new ArrayList<>();
                System.out.println("Error: kein Session-File");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Zeigt 1 Bild solange an bis der dazupassende Name eingegeben wurde.
     * @return void
     */
    public void displayImageAndTakeGuess() {
        while (true) { // gleiches Bild bis richtig erraten
            if (aktPaar != null) {
                //holt Name und URL
                String imageUrl = String.valueOf(aktPaar.getBildurl());
                String bildname = aktPaar.getBildname();

                try {
                    //Erstellt die GUI mit Bild und Eingabefenster zum Schreiben.
                    ImageIcon imageIcon = new ImageIcon(new URL(imageUrl));
                    imageIcon.setImage(imageIcon.getImage().getScaledInstance(250, 250, Image.SCALE_DEFAULT)); // Set the size
                    JLabel imageLabel = new JLabel(imageIcon);
                    JTextField guessField = new JTextField(20);

                    Object[] message = {
                            "Rate den Namen/das Wort:", guessField,
                            imageLabel
                    };

                    int option = JOptionPane.showOptionDialog(
                            null,
                            message,
                            "Rate den Namen/das Wort",
                            JOptionPane.OK_CANCEL_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            new String[]{"OK", "Zufall"},
                            "OK");

                    if (option == JOptionPane.OK_OPTION) {
                        String userGuess = guessField.getText();
                        boolean isCorrect = checkAntwort(userGuess);

                        //wenn das Wort richtig erkannt wird, wird die while-Schleife verlassen.
                        if (isCorrect) {
                            break;
                        }
                    } else if (option == 1) {
                        selectRandomPaar();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //nach Verlassen geht es zurück zur Auswahl
        showSelection();
    }


    /**
     * Zeigt die Selection-Übersicht an wo man ein Zufallsbild, ein Bild am Index oder "Ende" aussuchen kann.
     * @return void
     */
    public void showSelection() {
        //gibt Bildauswahloptionen angepasst an die Anzahl der Paare in der Liste und eine Zufalls-Option
        String[] options = new String[paarListe.size() + 2];
        options[0] = "Zufall";

        for (int i = 0; i < paarListe.size(); i++) {
            options[i + 1] = String.valueOf(i);
        }

        //Unten wird ein "Ende" angefügt um das Programm zu verlassen und das Ergebnis zu sehen
        options[options.length - 1] = "Ende";

        String selectedOption = (String) JOptionPane.showInputDialog(
                null,
                "Auswahl wählen:",
                "Optionen",
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                options[0]
        );

        //angepasst an die Auswahl wird der passende Programmschritt ausgeführt.
        if (selectedOption != null) {
            if (selectedOption.equals("Zufall")) {
                selectRandomPaar();
                displayImageAndTakeGuess();
            } else if (selectedOption.equals("Ende")) {
                JOptionPane.showMessageDialog(null, "Versuche: " + richtigeVersuche+" von "+alleVersuche +" richtig");
            } else {
                int selectedIndex = Integer.parseInt(selectedOption);
                selectPaar(selectedIndex);
                displayImageAndTakeGuess();
            }
        }
    }

    public Paar getAktPaar() {
        return this.aktPaar;
    }

    public List<Paar> getPaarListe() {
        return this.paarListe;
    }
}
