# Rollenaufteilung(miteels ChatGPT, also diskutierbar):

### 👥 **Teammitglieder und ihre Aufgaben**

#### **Person 1: Controller & Task-Verwaltung**

**Verantwortlichkeiten:**
>Übernimmt Ahmad

* **Koordination der Berechnung:** Der Controller steuert den gesamten Ablauf, teilt Aufgaben zu und sammelt die Ergebnisse.
* **Aufgabenverteilung:** Unterteilt das Bild in "Tiles" oder Zeilen und teilt diese als Tasks an die Worker zu.
* **Task-Synchronisation:** Wartet, bis alle Worker ihre Berechnungen abgeschlossen haben, und stellt sicher, dass alle Subtasks korrekt ausgeführt werden.
* **Fortschrittskontrolle:** Behandelt die Anzeige von Fortschritten, basierend auf der Anzahl der fertiggestellten Tasks.

**Methoden:**

* `startComputation()`: Startet den Berechnungsprozess.
* `divideTasks()`: Teilt das Bild in kleinere Einheiten (z. B. Zeilen oder Tiles).
* `collectResults()`: Sammelt die berechneten Pixel-Daten von den Workern.

**Zusammenarbeit mit anderen Teammitgliedern:**

* Arbeitet mit **Person 2** zusammen, um die Berechnungslogik klar zu definieren.
* Kommuniziert mit **Person 3** für die Verwaltung des Thread-Pools.

---

#### **Person 2: Mandelbrot-Engine (Berechnungskern)**
>Übernimmt Tobias

**Verantwortlichkeiten:**

* **Mandelbrot-Berechnung:** Implementiert die eigentliche mathematische Berechnung der Mandelbrot-Menge pro Pixel.
* **Optimierungen:** Implementiert verschiedene Berechnungsalgorithmen wie Escape-Time und Smooth Coloring zur Optimierung der Performance.
* **Tile-Verarbeitung:** Berechnet die Mandelbrot-Werte für die durch den Controller zugewiesenen Tiles oder Zeilen.

**Methoden:**

* `computePixel(x, y)`: Berechnet den Wert für einen einzelnen Pixel.
* `computeTile(tile)`: Berechnet alle Pixel in einem gegebenen Tile (Block von Pixeln).

**Zusammenarbeit mit anderen Teammitgliedern:**

* Arbeitet eng mit **Person 1** zusammen, um die Berechnungslogik pro Task zu definieren.
* Koordiniert mit **Person 3** bei der Aufgabenverteilung und parallelen Berechnung.

---

#### **Person 3: Worker-System & Thread-Pool**
>Übernimmt Georg

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

* Kommuniziert mit **Person 1**, um Aufgaben zu erhalten.
* Arbeitet mit **Person 2** zusammen, um sicherzustellen, dass die Berechnungslogik korrekt ausgeführt wird.

---

#### **Person 4: Renderer & Bildausgabe **
>Übernimmt Eric 

**Verantwortlichkeiten:**

* **Renderer:** Zeichnet das Bild basierend auf den berechneten Pixelwerten.
* **Ausgabeformat:** Implementiert eine Methode, um das Bild zu speichern (z. B. als PNG-Datei) oder in einem GUI-Fenster anzuzeigen.
* **Interaktivität:** Implementiert eine einfache Benutzeroberfläche (z. B. mit Swing oder JavaFX) zur Anzeige des Fortschritts und zur Interaktion (Zoom, Speichern, etc.).

**Methoden:**

* `drawPixel(x, y, color)`: Zeichnet den Farbwert für einen Pixel im Bild.
* `saveImage(file)`: Speichert das Bild in eine Datei.
* `showProgress()`: Zeigt den Fortschritt der Berechnung im GUI an.

**Zusammenarbeit mit anderen Teammitgliedern:**

* Arbeitet mit **Person 1** zusammen, um die finalen Ergebnisse nach Abschluss der Berechnung anzuzeigen.
* Koordiniert mit **Person 2**, um sicherzustellen, dass die richtigen Farbwerte für die Visualisierung verwendet werden.

---

### 🔄 **Zusammenarbeit und Kommunikationsflüsse**

1. **Controller** (Person 1) teilt das Bild in **Tasks** auf (Zeilen oder Tiles).
2. **Mandelbrot-Engine** (Person 2) berechnet die Werte für jedes Pixel innerhalb eines Tiles.
3. **Thread-Pool und Worker** (Person 3) führen die Berechnungen parallel aus, indem sie Tasks vom Controller erhalten und die Mandelbrot-Werte berechnen.
4. Sobald alle Berechnungen abgeschlossen sind, sendet der **Controller** die Ergebnisse an den **Renderer** (Person 4), um das Bild zu visualisieren und zu speichern.
5. **Renderer** (Person 4) zeichnet das Bild und zeigt den Fortschritt an (optional in einem GUI).


