#!/bin/bash

REGISTRY_PORT=1099

echo "Kompiliere Java-Klassen..."

# Erstelle das Zielverzeichnis, falls es nicht existiert
mkdir -p target/test-classes

# Kompiliere alle .java-Dateien aus src/ ins target-Verzeichnis
find src -name "*.java" > sources.txt
javac -d target/test-classes @sources.txt
rm sources.txt


echo "Starte GUI..."
java -cp target/test-classes vs_beleg_ateg.gui.GUI
