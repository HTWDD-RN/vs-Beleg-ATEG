# Dokumentation – Mandelbrot-Projekt

## Projektziel 

Implementierung eines verteilten Programms zur parallelen Berechnung und Darstellung der Mandelbrotmenge

## Technische Architektur
- **Module**: Controller, Worker, MandelbrotEngine, Renderer, Bootstrap-Server
- **Kommunikation**: Bootstrap-RMI-Aufrufe zwischen Controller ↔ Worker
- **Ablauf**: Tasks → Verteilung → Berechnung → Ergebnis → Anzeige

# Rollenaufteilung:

### **Ahmad: Controller & Task-Verwaltung & Implementierung des Bootstrap-Servers**

**Verantwortlichkeiten:**

* **Koordination der Berechnung:** Der Controller steuert den gesamten Ablauf, teilt Aufgaben zu und sammelt die Ergebnisse.
* **Aufgabenverteilung:** Unterteilt das Bild in "Tiles" oder Zeilen und teilt diese als Tasks an die Worker zu.
* **Task-Synchronisation:** Wartet, bis alle Worker ihre Berechnungen abgeschlossen haben, und stellt sicher, dass alle Subtasks korrekt ausgeführt werden.
* **Bootstrap-Server:** Implementiert Bootstrap-Server, der die Worker verwaltet und mit dem Controller zusammenarbeiten lässt.

**Methoden:**

* `startComputation()`: Startet den Berechnungsprozess.

**Zusammenarbeit mit anderen Teammitgliedern:**

* Übergibt Aufgaben über RMI an entfernte Worker, die von **Georg** implementiert wurden.
* Koordiniert die Taskverteilung, sammelt die TaskResult-Objekte und übergibt das fertige Bild an die GUI von **Eric**

---

#### **Tobias: Mandelbrot-Engine (Berechnungskern)**

**Verantwortlichkeiten:**

* **Mathematische Berechnung:** Implementiert die Escape-Time-Logik zur Berechnung der Mandelbrotmenge für einen gegebenen Punkt in der komplexen Ebene.
* **Umwandlung:** Wandelt Pixelkoordinaten (x, y) in komplexe Zahlen (re, im) um.
* **Farbgebung:** Berechnet einen Farbwert (RGB) auf Basis der Iterationsanzahl für die spätere Darstellung.
* **Optimierungspotenzial:** Die aktuelle Implementation nutzt klassische Escape-Time. Erweiterbar um z. B. Smooth Coloring.

**Zusammenarbeit mit anderen Teammitgliedern:**

* Arbeitet eng mit **Georg** zusammen, da die Workers für jeden Punkt computePixel(...) aufrufen.
* Die Methode liefert das Ergebnis zurück, das später von **Ahmad** koordiniert und von **Eric** angezeigt wird.

---

#### **Georg: Worker-System**


**Verantwortlichkeiten:**

* **Worker-Interface:** Definiert die Remote-Methoden, die vom Master aufgerufen werden können, um dem Worker Aufgaben zu übergeben. (-> WorkerInterface), gibt Result über Schnittstelle zurück
* **WorkerImpl:** Erstellt Thread zur Berechnung eines Tiles(evtl nicht nötig).
* **Worker-Thread:** Thread, der Task unterteilt und an MandelbrotEngine weitergibt
* **Daten-Klasse Task:** Enhält Daten zum zu berechnenden Tile des Fensters;
* **Daten-Klasse TaskResult:** Enhält Ergebnis-Daten zum zu berechnenden Tile des Fensters als 2-dimensionales Array;

**Methoden:**

* `computeTask()`: Führt vom Controller gegebene Task zur Berechnung aus, returnt Pixel-Rechteck. Ist RMI-Schnittstelle zu WorkerInterface

**Zusammenarbeit mit anderen Teammitgliedern:**

* Kommuniziert mit **Ahmad**, um Aufgaben zu erhalten.
* Arbeitet mit **Tobias** zusammen, um sicherzustellen, dass die Berechnungslogik korrekt ausgeführt wird.

<<<<<<< HEAD
- 1 Rechner mit i5-13500@4GHz mit RMI
  - 1 Worker: 130726 ms
  - 4 Worker: 40510 ms
  - 20 Worker: 13675 ms

- 2 Rechner mit i5-13500@4GHz und i5-1035G1@3GHz mit RMI über WLAN:
  - 20+1 Worker: 16599 ms
  - 20+4 Worker: 17180 ms
  - 20+8 Worker: 20889 ms

- 3 Rechner TODO
=======
---

#### **Eric: Renderer & Bildausgabe**
 

**Verantwortlichkeiten:**

* **Renderer:** Zeichnet das Bild basierend auf den berechneten Pixelwerten.
* **Ausgabeformat:** Implementiert eine Methode, um das Bild zu speichern (z. B. als PNG-Datei) oder in einem GUI-Fenster anzuzeigen.
* **Interaktivität:** Implementiert eine einfache Benutzeroberfläche zur Anzeige des Fortschritts und zur Interaktion (Zoom, Speichern, etc.).

**Zusammenarbeit mit anderen Teammitgliedern:**

* Arbeitet mit **Ahmad** zusammen, um die finalen Ergebnisse nach Abschluss der Berechnung anzuzeigen.
* Koordiniert mit **Tobias**, um sicherzustellen, dass die vom Berechnungsmodul gelieferten RGB-Werte korrekt dargestellt werden.

---

### 🔄 **Zusammenarbeit und Kommunikationsflüsse**

1. **Controller** (Ahmad) teilt das Bild in **Tasks** auf (Zeilen oder Tiles).
2. **Mandelbrot-Engine** (Tobias) berechnet die Werte für jedes Pixel innerhalb eines Tiles.
3. **Worker** (Georg) führen die Berechnungen parallel aus, indem sie Tasks vom Controller erhalten und die Mandelbrot-Werte berechnen.
4. Sobald alle Berechnungen abgeschlossen sind, sendet der **Controller** die Ergebnisse an den **Renderer** (Eric), um das Bild zu visualisieren und zu speichern.
5. **Renderer** (Eric) zeichnet das Bild und zeigt den Fortschritt an und misst die Berechnungszeit.
>>>>>>> b30cc8b (Updated Dokumentation)
