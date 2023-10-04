package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * erweitert Strategy-Interface zum Speichern der PaarListe in JSON
 * Autor: Bianca Bogenreiter
 * Version: 2023-04-10
 */

public class JsonSessionSaver implements SessionSaver {
    private final String FILE_PATH = "C:\\Users\\bianc\\OneDrive\\Schule5DHIT\\SEW\\Modul09a\\WorttrainerReloaded\\src\\main\\files\\session.json";

    /**
     * speichert die paarListe in einem JSON-File mit 'com.fasterxml.jackson.core:jackson-databind:2.15.2'
     * @param paarListe die zu speichernde Liste
     * @throws IOException
     */
    @Override
    public void saveSession(List<Paar> paarListe) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(new File(FILE_PATH), paarListe);
    }

    /**
     * ladet die paarListe aus einem JSON-File mit 'com.fasterxml.jackson.core:jackson-databind:2.15.2'
     * @throws IOException
     */
    @Override
    public List<Paar> loadSession() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        File sessionFile = new File(FILE_PATH);

        if (sessionFile.exists()) {
            return objectMapper.readValue(sessionFile, new TypeReference<List<Paar>>() {});
        } else {
            return null;
        }
    }
}
