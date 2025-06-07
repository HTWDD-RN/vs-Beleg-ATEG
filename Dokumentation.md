# Dokumentation – Mandelbrot-Projekt

Start für Funktionsfähige Berechnung:
- 1. ./runServer.sh 
- 2. Für alle Worker (default 4) ./runWorker.sh <Adresse des Servers>
- 3. ./runGui.sh und auf Berechneklicken, ggf Parameter verstellen


Das Klassenmodel ist in UML_Klassendiagramm.puml gespeichert.

Wir haben die Implementierung und Aufgaben wie folgt aufgeilt:
Eric: Implementierung der GUI
Ahmad: Implementierung des Controllers und Erstellung des BootstrapServers mit Georg
Georg: Implementierung der Worker, Planung der Projektarbeit
Tobias: Implementierung der PixelBerechnung

Genauere Beschreibung in Dokumentation.md


# Geschwindigkeit

- alle Tests unter Windows 10, Linux ist tatsächlich langsamer
- 1 Rechner mit i5-13500@4GHz ohne RMI (nur mit Thread-Verteilung)
  - 1 Threads: 128536 ms
  - 4 Threads: 38613 ms
  - 20 Threads: 12159 ms

- 1 Rechner mit i5-13500@4GHz mit RMI
  - 1 Worker: 130726 ms
  - 4 Worker: 40510 ms
  - 20 Worker: 13675 ms

- 2 Rechner mit i5-13500@4GHz und i5-1035G1@3GHz mit RMI über WLAN:
  - 20+1 Worker: 16599 ms
  - 20+4 Worker: 17180 ms
  - 20+8 Worker: 20889 ms

- 3 Rechner TODO