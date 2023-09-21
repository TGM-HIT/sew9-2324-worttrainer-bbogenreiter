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

public class Rechtschreibtrainer {

    private Paar aktPaar;
    private List<Paar> paarListe;
    private int alleVersuche;
    private int richtigeVersuche;
    private final String FILE_PATH = "C:\\Users\\Franz\\OneDrive\\Schule5DHIT\\SEW\\Modul09a\\session.json";
    public Rechtschreibtrainer() {
        this.loadSession();
        this.showSelection();
    }

    public void selectPaar(int index) {
        if (index >= 0 && index < paarListe.size()) {
            aktPaar = paarListe.get(index);
        }
    }

    public void selectRandomPaar() {
        if (!paarListe.isEmpty()) {
            int randomIndex = (int) (Math.random() * paarListe.size());
            aktPaar = paarListe.get(randomIndex);
        }
    }

    public boolean checkAntwort(String antwort) {
        if (aktPaar != null && antwort != null) {
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

    public void saveSession() {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            new ObjectMapper().writeValue(new File(FILE_PATH), this.paarListe);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadSession() {
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

    public void displayImageAndTakeGuess() {
        while (true) { // gleiches Bild bis richtig erraten
            if (aktPaar != null) {
                String imageUrl = String.valueOf(aktPaar.getBildurl());
                String bildname = aktPaar.getBildname();

                try {
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
        showSelection();
    }


    public void showSelection() {
        String[] options = new String[paarListe.size() + 2];
        options[0] = "Zufall";

        for (int i = 0; i < paarListe.size(); i++) {
            options[i + 1] = String.valueOf(i);
        }

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



}
