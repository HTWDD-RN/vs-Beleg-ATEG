# Dokumentation – Mandelbrot-Projekt

## Projektziel 

Implementierung eines verteilten Programms zur parallelen Berechnung und Darstellung der Mandelbrotmenge

## Technische Architektur
- **Module**: Controller, Worker, MandelbrotEngine, Renderer
- **Kommunikation**: RMI-Aufrufe zwischen Controller ↔ Worker
- **Ablauf**: Tasks → Verteilung → Berechnung → Ergebnis → Anzeige

## Verteilungsgewinn & Zeitmessung
| Threads | Zeit (ms)|
|---------|----------|
| 1       | ...      |
| 2       | ...      |
| 3       | ...      |
| 4       | ...      |

# Rollenaufteilung:

### **Ahmad: Controller & Task-Verwaltung**

**Verantwortlichkeiten:**

* **Koordination der Berechnung:** Der Controller steuert den gesamten Ablauf, teilt Aufgaben zu und sammelt die Ergebnisse.
* **Aufgabenverteilung:** Unterteilt das Bild in "Tiles" oder Zeilen und teilt diese als Tasks an die Worker zu.
* **Task-Synchronisation:** Wartet, bis alle Worker ihre Berechnungen abgeschlossen haben, und stellt sicher, dass alle Subtasks korrekt ausgeführt werden.
* **Fortschrittskontrolle:** Behandelt die Anzeige von Fortschritten, basierend auf der Anzahl der fertiggestellten Tasks.

**Methoden:**

* `startComputation()`: Startet den Berechnungsprozess.
* `divideTasks()`: Teilt das Bild in kleinere Einheiten (z. B. Zeilen oder Tiles).
* `collectResults()`: Sammelt die berechneten Pixel-Daten von den Workern.

**Zusammenarbeit mit anderen Teammitgliedern:**

* Nutzt die von **Tobias** bereitgestellte Berechnungslogik zur Lösung der Tasks.
* Übergibt Aufgaben über RMI an entfernte Worker, die von **Georg** implementiert wurden.
* Koordiniert die Taskverteilung, sammelt die TaskResult-Objekte und übergibt das fertige Bild an die GUI von **Eric**

---

#### **Tobias: Mandelbrot-Engine (Berechnungskern)**

**Verantwortlichkeiten:**

* **Mathematische Berechnung:** Implementiert die Escape-Time-Logik zur Berechnung der Mandelbrotmenge für einen gegebenen Punkt in der komplexen Ebene.
* **Umwandlung:** Wandelt Pixelkoordinaten (x, y) in komplexe Zahlen (re, im) um.
* **Farbgebung:** Berechnet einen Farbwert (RGB) auf Basis der Iterationsanzahl für die spätere Darstellung.
* **Optimierungspotenzial:** Die aktuelle Implementation nutzt klassische Escape-Time. Erweiterbar um z. B. Smooth Coloring.

**Methoden:**

* `computePixel(re, im, maxIter):int`: Berechnet den Farbwert eines einzelnen Punkts der Mandelbrot-Menge.
Gibt einen RGB-Wert (int) zurück, abhängig davon, wie schnell die Folge divergiert.
* `computeTile(tile)`: Ist konzeptionell vorgesehen, aber nicht als eigene Methode implementiert.
Die Berechnung eines Tiles erfolgt im Worker durch wiederholten Aufruf von `computePixel(...)`.

**Zusammenarbeit mit anderen Teammitgliedern:**

* Arbeitet eng mit **Georg** zusammen, da die Workers für jeden Punkt computePixel(...) aufrufen.
* Die Methode liefert das Ergebnis zurück, das später von **Ahmad** koordiniert und von **Eric** angezeigt wird.

---

#### **Georg: Worker-System & Thread-Pool**


**Verantwortlichkeiten:**

* **Worker-Interface:** Definiert die Remote-Methoden, die vom Master aufgerufen werden können, um dem Worker Aufgaben zu übergeben. (-> WorkerInterface), gibt Result über Schnittstelle zurück
* **WorkerImpl:** Erstellt Thread zur Berechnung eines Tiles(evtl nicht nötig).
* **Worker-Thread:** Thread, der Task unterteilt und an MandelbrotEngine weitergibt
* **Daten-Klasse Task:** Enhält Daten zum zu berechnenden Tile des Fensters;
* **Daten-Klasse TaskResult:** Enhält Ergebnis-Daten zum zu berechnenden Tile des Fensters als 2-dimensionales Array;

**Methoden:**

* `run()`: Führt die Berechnungen für die zugewiesene Aufgabe aus.
* `computeTask()`: Führt vom Controller gegebene Task zur Berechnung aus, returnt TaskResult. Ist RMI-Schnittstelle zu WorkerInterface


**Zusammenarbeit mit anderen Teammitgliedern:**

* Kommuniziert mit **Ahmad**, um Aufgaben zu erhalten.
* Arbeitet mit **Tobias** zusammen, um sicherzustellen, dass die Berechnungslogik korrekt ausgeführt wird.

---

#### **Eric: Renderer & Bildausgabe**
 

**Verantwortlichkeiten:**

* **Renderer:** Zeichnet das Bild basierend auf den berechneten Pixelwerten.
* **Ausgabeformat:** Implementiert eine Methode, um das Bild zu speichern (z. B. als PNG-Datei) oder in einem GUI-Fenster anzuzeigen.
* **Interaktivität:** Implementiert eine einfache Benutzeroberfläche (z. B. mit Swing oder JavaFX) zur Anzeige des Fortschritts und zur Interaktion (Zoom, Speichern, etc.).

**Methoden:**

* `drawPixel(x, y, color)`: Zeichnet den Farbwert für einen Pixel im Bild.
* `saveImage(file)`: Speichert das Bild in eine Datei.
* `showProgress()`: Zeigt den Fortschritt der Berechnung im GUI an.

**Zusammenarbeit mit anderen Teammitgliedern:**

* Arbeitet mit **Ahmad** zusammen, um die finalen Ergebnisse nach Abschluss der Berechnung anzuzeigen.
* Koordiniert mit **Tobias**, um sicherzustellen, dass die vom Berechnungsmodul gelieferten RGB-Werte korrekt dargestellt werden.

---

### 🔄 **Zusammenarbeit und Kommunikationsflüsse**

1. **Controller** (Ahmad) teilt das Bild in **Tasks** auf (Zeilen oder Tiles).
2. **Mandelbrot-Engine** (Tobias) berechnet die Werte für jedes Pixel innerhalb eines Tiles.
3. **Thread-Pool und Worker** (Georg) führen die Berechnungen parallel aus, indem sie Tasks vom Controller erhalten und die Mandelbrot-Werte berechnen.
4. Sobald alle Berechnungen abgeschlossen sind, sendet der **Controller** die Ergebnisse an den **Renderer** (Eric), um das Bild zu visualisieren und zu speichern.
5. **Renderer** (Eric) zeichnet das Bild und zeigt den Fortschritt an (optional in einem GUI).


