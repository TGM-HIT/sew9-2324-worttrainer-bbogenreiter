package org.example;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/**
 * erweitert Strategy-Interface zum Speichern der PaarListe in XML
 * Autor: Bianca Bogenreiter
 * Version: 2023-04-10
 */

public class XmlSessionSaver implements SessionSaver {
    private final String FILE_PATH = "C:\\Users\\bianc\\OneDrive\\Schule5DHIT\\SEW\\Modul09a\\WorttrainerReloaded\\src\\main\\files\\session.xml";

    /**
     * speichert die paarListe in einem XML-File
     * @param paarListe die zu speichernde Liste
     * @throws IOException
     */
    @Override
    public void saveSession(List<Paar> paarListe) throws IOException {
        try {
            // Erstellen einer neuen XML-Document-Instanz
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            Document doc = docBuilder.newDocument();
            // Erstellen des Wurzelelements "paarList"
            Element rootElement = doc.createElement("paarList");
            doc.appendChild(rootElement);

            //Schleife für jedes Paar in der Liste
            for (Paar paar : paarListe) {
                Element paarElement = doc.createElement("paar");

                //Bildname Element erstellen und einfügen
                Element bildnameElement = doc.createElement("bildname");
                bildnameElement.appendChild(doc.createTextNode(paar.getBildname()));
                paarElement.appendChild(bildnameElement);

                //Bildname Element erstellen und einfügen
                Element bildurlElement = doc.createElement("bildurl");
                bildurlElement.appendChild(doc.createTextNode(String.valueOf(paar.getBildurl())));
                paarElement.appendChild(bildurlElement);

                //paar element zu paarliste element hinzufügen
                rootElement.appendChild(paarElement);
            }

            //Ausführen der Transformation zum Speichern
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(FILE_PATH));

            transformer.transform(source, result);

        } catch (ParserConfigurationException | TransformerException e) {
            throw new IOException("Error bei XML-Speicherung", e);
        }
    }

    /**
     * lädt die paarListe aus einem XML-File
     * @throws IOException
     */
    @Override
    public List<Paar> loadSession() throws IOException {
        List<Paar> paarListe = new ArrayList<>();

        try {
            File sessionFile = new File(FILE_PATH);

            if (sessionFile.exists()) {
                // Erstellen einer XML-Document-Instanz aus der Datei
                DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
                Document doc = docBuilder.parse(sessionFile);

                // Abrufen der paar Elemente und Erstellen der Paar-Objekte
                NodeList paarNodes = doc.getElementsByTagName("paar");

                //für jedes Paar im xml
                for (int i = 0; i < paarNodes.getLength(); i++) {
                    Node paarNode = paarNodes.item(i);

                    if (paarNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element element = (Element) paarNode;
                        // Holt den bildnamen und bildurl
                        String bildname = element.getElementsByTagName("bildname").item(0).getTextContent();
                        String bildurl = element.getElementsByTagName("bildurl").item(0).getTextContent();

                        //bildet Paar und fügt es in die liste ein
                        Paar paar = new Paar(bildname, bildurl);
                        paarListe.add(paar);
                    }
                }
            }

        } catch (ParserConfigurationException | SAXException e) {
            throw new IOException("Error bei XML-Ladung", e);
        }
        //gibt die liste zurück
        return paarListe;
    }
}
