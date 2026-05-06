
## Beschreibung
Ein zentraler Motivationsfaktor für Katja ist die Sichtbarkeit ihrer Fortschritte. Dafür muss die App in der Lage sein, physische Zeichnungen zu digitalisieren. Nutzerinnen sollen die Wahl haben, entweder ein Foto direkt aus der App heraus aufzunehmen oder ein bereits existierendes Bild aus der System-Galerie zu importieren.

## Aufgaben

### 1. Permission Management
- [x] Deklaration der notwendigen Permissions in der `AndroidManifest.xml` (Kamera).
- [x] Implementierung eines Permission-Handlers in Compose (unter Nutzung von `Accompanist Permissions` oder der nativen `ActivityResult` API), um den Zugriff auf die Kamera zur Laufzeit anzufragen.

### 2. Galerie-Import (PhotoPicker)
- [x] Integration des modernen **Android PhotoPicker**, der keine speziellen Speicher-Berechtigungen benötigt, um ein einzelnes Bild auszuwählen.

### 3. Kamera-Integration
- [x] Implementierung der Kamera-Funktion (entweder via `MediaStore.ACTION_IMAGE_CAPTURE` Intent für einfache Zwecke oder via `CameraX` für eine tiefere Integration).

### 4. Bild-Persistenz & Storage-Handling
- [x] **Speichern:** Kopieren des ausgewählten/aufgenommenen Bildes in den `Internal Storage` der App (`context.filesDir`), damit die Bilder erhalten bleiben, auch wenn sie in der System-Galerie gelöscht werden.
- [x] **Verknüpfung:** Speichern des resultierenden Dateipfads als String in der `ChallengeCompletion`-Entity in der Room-Datenbank.

### 5. Error-Handling
- [x] Behandlung von Grenzfällen: Try-catch für Speicherfehler, null-checks für Bilder.
- [ ] Erweiterte Fehlerbehandlung für ausgelöste Exceptions.

## Technische Hinweise
- **Scoped Storage:** Da wir Bilder lokal speichern, nutzen wir den privaten Speicherbereich der App. Das erspart uns mühsame `READ_EXTERNAL_STORAGE` Berechtigungen auf neueren Android-Versionen.
- **Performance:** Bilder werden mit JPEG-Komprimierung (90%) gespeichert, um den lokalen Speicherplatz nicht unnötig zu belasten.
- **Namensvergabe:** Eindeutige Dateinamen via UUID (`completion_${UUID.randomUUID()}.jpg`).

## Akzeptanzkriterien
- [x] Der Nutzer kann beim Abschließen einer Challenge zwischen "Foto aufnehmen" und "Aus Galerie wählen" entscheiden
- [x] Kamera startet mit Permission-Anfrage
- [x] Bilder werden in Internal Storage gespeichert
- [x] Der Bildpfad wird in der Datenbank gespeichert (ChallengeCompletion.imagePath)
- [x] Bilder bleiben erhalten, auch wenn in System-Galerie gelöscht

## 📊 STATUS: ✅ 90% ABGESCHLOSSEN

### ✅ Implementiert:
1. [x] Permission Management (Runtime + Manifest)
2. [x] Galerie-Import mit PhotoPicker
3. [x] Kamera-Integration mit TakePicturePreview()
4. [x] Bild-Speicherung in Internal Storage mit Komprimierung (JPEG 90%)
5. [x] UUID für eindeutige Dateinamen
6. [x] Dateipfad-Verknüpfung mit ChallengeCompletion
7. [x] Basis Error-Handling (Try-Catch)

### ⚠️ Noch TODO (QA Phase):
- [ ] Erweiterte Fehlerbehandlung (Speicher voll, große Bilder, etc.)
- [ ] User-Feedback für misslungene Speicherungen
- [ ] Performance-Tests mit großen Bildern
