# Rollenaufteilung(miteels ChatGPT, also diskutierbar):

**Person 1: Controller & Task-Verwaltung**
- Verantwortlich für die Steuerung des Ablaufs.
- Teilt das Bild in "Tiles" oder "Zeilenblöcke" auf.
- Überwacht Fortschritt, synchronisiert Threads.
- >Übernimmt Ahmad?

**Person 2: Mandelbrot-Engine (Kernberechnung)**
- Implementiert die Berechnung pro Pixel (z. B. mandelbrot(x, y)).
- Optimierungen wie Escape-Time oder Smooth Coloring.
- >Übernimmt Tobias

***Person 3: Worker-System & Parallelisierung**
- Setzt ein Thread-Pool um (ExecutorService).
- Optional: Fork/Join Framework oder Work-Stealing.
- Holt Aufgaben aus Queue und berechnet Subbereiche.
- >Übernimmt Georg?

**Person 4: GUI / Renderer / Bildausgabe**
- Darstellung des Endbilds (Swing, JavaFX oder als PNG speichern).
- Fortschrittsanzeige & Interaktion (Zoom, Repaint, etc.).
- Paremeter in GUI einstellbar
- >Übernimmt Eric 