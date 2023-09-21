package org.example;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Paar-Klasse, die die Paar-Objekte bestehend aus URL und Namen erstellt.
 * Autor: Bianca Bogenreiter
 * Version: 2023-09-21
 */
public class Paar {
    private String bildname;
    private URL bildurl;

    /**
     * Der leere Konstruktur
     */
    public Paar() {
    }

    /**
     * Der Konstruktor von Paar
     * @param bildname der Name des Bildes
     * @param bildurl die URL des Bildes als String
     */
    public Paar(String bildname, String bildurl) throws MalformedURLException {
        if (bildname == null || bildurl == null) {
            throw new IllegalArgumentException("Bildname oder Bildurl kann nicht leer sein.");
        }

        // Check ob URL gültig + String zu URL
        try {
            this.bildurl = new URL(bildurl);
        } catch (MalformedURLException e) {
            throw new MalformedURLException("Bildurl ist keine gültige URL.");
        }

        this.bildname = bildname;
    }

    /**
     * gibt den Bildnamen zurück
     * @return String der Name des Bildes
     */
    public String getBildname() {
        return this.bildname;
    }

    /**
     * gibt die Bildurl zurück
     * @return URL die URL des Bildes
     */
    public URL getBildurl() {
        return this.bildurl;
    }
}
