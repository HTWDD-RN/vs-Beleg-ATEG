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


# Geschwindigkeit

- 1 Rechner mit i5-13500@4GHz
  - 1 Worker: 128536 ms
  - 4 Worker: 38613 ms
  - 20 Worker: 12159 ms 
