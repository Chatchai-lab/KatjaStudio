
## Beschreibung
Die Galerie ist das Herzstück zur Fortschrittsvisualisierung. Sie soll alle bisherigen Werke von Katja in einer ansprechenden Übersicht (Grid) anzeigen. Nutzerinnen können hier durch ihre künstlerische Reise scrollen, die Entwicklung ihrer Fähigkeiten beobachten und Details zu jeder abgeschlossenen Übung einsehen.

## Aufgaben

### 1. Galerie-UI (Grid Layout)
- [ ] Erstellen des `GalleryScreen` mit einem `LazyVerticalGrid`.
- [ ] Implementierung von Bild-Karten (`GalleryCard`), die das gezeichnete Bild im quadratischen Format (Aspect Ratio 1:1) anzeigen.
- [ ] Anzeige von Metadaten auf der Karte oder in einem Overlay (z. B. Datum und Challenge-Name).

### 2. Datenanbindung & Sortierung
- [ ] Abfrage aller `ChallengeCompletion`-Einträge mit Bildpfad aus der Room-Datenbank.
- [ ] Sortierung der Liste nach `completedAt` (absteigend, neueste zuerst).
- [ ] Gruppierung der Bilder nach Monaten oder Wochen (optional, zur besseren Übersicht).

### 3. Detailansicht (Fullscreen)
- [ ] Implementierung einer Detailansicht, die sich öffnet, wenn ein Bild angeklickt wird.
- [ ] Anzeige des Bildes in voller Größe inkl. Zoom-Möglichkeit.
- [ ] Einblendung der Notizen und der Dauer der Übung unter dem Bild.

### 4. Empty State
- [ ] Gestaltung eines freundlichen "Empty State" für den Fall, dass noch keine Zeichnungen existieren (z. B. mit einem motivierenden Text: "Deine Galerie ist noch leer. Starte deine erste Challenge!").

### 5. Performance-Optimierung
- [ ] Nutzung einer Image-Loading-Library wie **Coil**, um Bilder asynchron zu laden und Bitmaps effizient zu cachen (verhindert Ruckeln beim Scrollen).

## Technische Hinweise
- **Bild-Skalierung:** Achte darauf, Thumbnails für die Grid-Ansicht zu verwenden oder die Bilder beim Laden zu skalieren, um `OutOfMemory`-Fehler zu vermeiden.
- **URI-Zugriff:** Stelle sicher, dass die Dateipfade aus der Datenbank korrekt in `File` oder `Uri` Objekte umgewandelt werden, damit Coil sie lesen kann.

## Akzeptanzkriterien
- [ ] Der Galerie-Screen zeigt ein Grid mit allen Bildern, die einen Pfad in der Datenbank haben.
- [ ] Die neuesten Zeichnungen stehen ganz oben.
- [ ] Ein Klick auf ein Bild öffnet die Detailansicht mit allen gespeicherten Infos (Notiz, Datum, Titel).
- [ ] Die App bleibt flüssig, auch wenn die Galerie mehr als 20 Bilder enthält.
- [ ] Der Empty State wird angezeigt, wenn die Datenbank keine Abschlüsse enthält.