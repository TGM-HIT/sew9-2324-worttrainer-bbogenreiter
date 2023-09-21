package org.example;

import java.net.MalformedURLException;
import java.net.URL;

public class Paar {
    private String bildname;
    private URL bildurl;

    public Paar() {
    }

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

    public String getBildname() {
        return this.bildname;
    }

    public URL getBildurl() {
        return this.bildurl;
    }
}
