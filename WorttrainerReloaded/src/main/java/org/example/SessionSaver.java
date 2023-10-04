package org.example;

import java.io.IOException;
import java.util.List;

/**
 * Interface für das Strategy-Pattern zum Wechseln zwischen Speichermethoden
 * Autor: Bianca Bogenreiter
 * Version: 2023-04-10
 */

public interface SessionSaver {
    void saveSession(List<Paar> paarListe) throws IOException;
    List<Paar> loadSession() throws IOException;
}
