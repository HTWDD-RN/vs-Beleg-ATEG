# Dokumentation – Mandelbrot-Projekt

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