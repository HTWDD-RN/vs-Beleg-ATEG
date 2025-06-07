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

# Messwerte
- 1 Rechner mit i5-13500@4GHz mit RMI
  - 1 Worker: 130726 ms
  - 4 Worker: 40510 ms
  - 20 Worker: 13675 ms

- 2 Rechner mit i5-13500@4GHz und i5-1035G1@3GHz mit RMI über WLAN:
  - 20+1 Worker: 16599 ms
  - 20+4 Worker: 17180 ms
  - 20+8 Worker: 20889 ms

- 3 Rechner TODO

Start für Funktionsfähige Berechnung:
1. ./runServer.sh 
2. Für alle Worker (default 4) ./runWorker.sh <Adresse des Servers>
3. ./runGui.sh und auf Start! klicken, ggf Parameter verstellen


Das Klassenmodel ist in UML_Klassendiagramm.puml gespeichert.

Wir haben die Implementierung und Aufgaben wie folgt aufgeilt:
Eric: Implementierung der GUI
Ahmad: Implementierung des Controllers und Erstellung des BootstrapServers mit Georg
Georg: Implementierung der Worker, Planung der Projektarbeit
Tobias: Implementierung der PixelBerechnung

Genauere Beschreibung in Dokumentation.md


# Geschwindigkeit


## 1 Rechner mit i5-13500@4GHz **ohne RMI** (nur mit Thread-Verteilung)

| Threads | Zeit (ms) |
|---------|-----------|
| 1       | 128484    |
| 2       | 71899     |
| 3       | 49995     |
| 4       | 38589     |
| 5       | 31506     |
| 6       | 26849     |
| 7       | 24274     |
| 8       | 22070     |
| 9       | 20276     |
| 10      | 18493     |
| 11      | 17236     |
| 12      | 15969     |
| 13      | 14939     |
| 14      | 14090     |
| 15      | 13319     |
| 16      | 12671     |
| 17      | 12078     |
| 18      | 12067     |
| 19      | 12294     |
| 20      | 12128     |

## 1 Rechner mit i5-13500@4GHz **mit RMI**

| Threads | Zeit (ms) |
|---------|-----------|
| 1       | 130938    |
| 2       | 73720     |
| 3       | 51763     |
| 4       | 40502     |
| 5       | 33525     |
| 6       | 28853     |
| 7       | 25747     |
| 8       | 23492     |
| 9       | 21677     |
| 10      | 20173     |
| 11      | 18833     |
| 12      | 17889     |
| 13      | 16940     |
| 14      | 15855     |
| 15      | 15138     |
| 16      | 14548     |
| 17      | 14168     |
| 18      | 14372     |
| 19      | 14521     |
| 20      | 13777     |

## 2 Rechner mit i5-13500@4GHz und i5-1035G1@3GHz **mit RMI über WLAN**

| Threads (Lokal+Remote) | Zeit (ms) |
|------------------------|-----------|
| 20+1                   | 16599     |
| 20+2                   | 18554     |
| 20+3                   | 18519     |
| 20+4                   | 18664     |
| 20+5                   | 20455     |
| 20+6                   | 19217     |
| 20+7                   | 21906     |
| 20+8                   | 21803     |