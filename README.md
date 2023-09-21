# sew9-2324-worttrainer-bbogenreiter
sew9-2324-worttrainer-bbogenreiter created by GitHub Classroom

**Autor:** [Bianca Bogenreiter]

## Aufgabe
Dieses Java-Projekt ist ein Rechtschreibtrainer für Volksschulkinder. Er zeigt Bilder an und zu diesem Bild, muss das Kind den dazugehörigen Namen richtig eingeben. Die Paare sind in einem json-File gespeichert und werden von dort geladen. Der FILE_PATH muss bei Verwendung richtig angepasst werden.

## Ablauf
Beim Start wird ein Auswahlmenü angezeigt, wo man entscheiden kann, ob man ein Zufallsbild oder ein Bild nach Index angezeigt wird oder ob man das Programm beenden möchte.

### Zufallsbild
Ein zufälliges Paar wird aus der Liste ausgewählt und das Bild wird angezeigt. Ein weiteres Eingabefenster erlaubt es dem Kind das Wort zu erraten. Bei richtiger Anwort wird es zurück zum Auswahlmenü geleitet und bei falscher ratet es weiter.

### Bild nach Index
Ein Bild wird anhand des gewählten Index aus der Liste ausgewählt und das Bild wird angezeigt. Ein weiteres Eingabefenster erlaubt es dem Kind das Wort zu erraten. Bei richtiger Anwort wird es zurück zum Auswahlmenü geleitet und bei falscher ratet es weiter.

### Programm beenden
Ein Fenster, das die Statistik anzeigt (wieviele Versuche richtig waren von allen) wird angezeigt und nach Schließung dieses Fensters wird das Programm beendet.

## Gradle
Im gradle.build wurde für die Speicherung und Ladung aus dem session.json eine externe Bibliothek eingebunden (Jackson Databind Version 2.15.2)
https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind/2.15.2

''' build.gradle
dependencies {
    implementation 'com.fasterxml.jackson.core:jackson-databind:2.15.2'
}
'''
''' Rechtschreibtrainer.java
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
'''

## Testung
unter resources ist das session.json eingebaut indem 3 Bilder mit Namen verfügbar sind um sofortige Testung möglich zu machen. Dieses ist erweiterbar. Der FILE_PATH muss jedoch bei Verwendung richtig angepasst werden.
