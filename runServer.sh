#!/bin/bash

REGISTRY_PORT=1099

# Prüfen, ob Registry bereits läuft
PID=$(lsof -ti tcp:$REGISTRY_PORT)

if [ -z "$PID" ]; then
  echo "Starte RMI-Registry auf Port $REGISTRY_PORT..."
  rmiregistry -J-Djava.rmi.server.codebase=file:/$(pwd)/target/test-classes/ &
  sleep 2
else
  echo "RMI-Registry läuft bereits mit PID $PID auf Port $REGISTRY_PORT."
fi

echo "Kompiliere Java-Klassen..."

# Erstelle das Zielverzeichnis, falls es nicht existiert
mkdir -p target/test-classes

# Kompiliere alle .java-Dateien aus src/ ins target-Verzeichnis
find src -name "*.java" > sources.txt
javac -d target/test-classes @sources.txt
rm sources.txt


echo "Starte GUI..."
java -cp target/test-classes vs_beleg_ateg.gui.GUI
