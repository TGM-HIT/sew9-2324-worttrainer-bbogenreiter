package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;

import static org.testng.AssertJUnit.*;

public class RechtschreibtrainerTest {
    private Rechtschreibtrainer trainer;

    @BeforeEach
    public void setUp() {
        trainer = new Rechtschreibtrainer(0);
    }

    @Test
    public void testSelectPaar() throws MalformedURLException {
        Paar testPaar = new Paar("Hund", "http://example.com/test.jpg");
        trainer.selectPaar(0);
        assertEquals(testPaar.getBildname(), trainer.getAktPaar().getBildname());
    }

    @Test
    public void testSelectRandomPaar() {
        trainer.selectRandomPaar();
        assertNotNull(trainer.getAktPaar());
    }

    @Test
    public void testCheckAntwortCorrect() {
        trainer.selectPaar(0);
        assertTrue(trainer.checkAntwort("Hund"));
    }

    @Test
    public void testCheckAntwortIncorrect() {
        trainer.selectPaar(0);
        assertFalse(trainer.checkAntwort("FalschesWort"));
    }

    @Test
    public void testSaveAndLoadSession() throws MalformedURLException {
        Paar testPaar1 = new Paar("Pfau", "https://image.geo.de/30127374/t/ef/v3/w1440/r1/-/blauer-pfau-shutterstock-geolino-extra-63-querofrmat-jpg--72195-.jpg");
        Paar testPaar2 = new Paar("Huhn", "https://kiwithek.wien/images/Huhn_Kopf.jpg");

        trainer.getPaarListe().add(testPaar1);
        trainer.getPaarListe().add(testPaar2);

        trainer.saveSession();
        trainer.loadSession();

        assertEquals(testPaar1.getBildname(), trainer.getPaarListe().get(3).getBildname());
        assertEquals(testPaar2.getBildname(), trainer.getPaarListe().get(4).getBildname());
    }

}
