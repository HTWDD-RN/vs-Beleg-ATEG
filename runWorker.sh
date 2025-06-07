#!/bin/bash

if [ "$#" -ne 1 ]; then
    echo "Nutzung: ./run_worker.sh <master-host>"
    exit 1
fi

MASTER_HOST=$1
WORKER_ID=$2

# Zielverzeichnis erstellen
mkdir -p target/test-classes

# Kompiliere alle .java-Dateien
javac -d target/test-classes -cp target $(find src -name "*.java")

# Worker starten mit übergebenem Master-Host und Worker-ID
echo "Starte Worker mit Master '$MASTER_HOST'..."
java -cp target/test-classes vs_beleg_ateg.worker.WorkerServer "$MASTER_HOST"
