#!/bin/bash

# Erstelle das Zielverzeichnis, falls es nicht existiert
mkdir -p target/test-classes

# Kompiliere alle .java-Dateien aus src/ ins target-Verzeichnis
javac -d target/test-classes -cp target $(find src -name "*.java")

echo "Starte Bootstrap Server..."
java -cp target/test-classes vs_beleg_ateg.bootstrap.BootstrapRegistration