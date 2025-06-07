@echo off
setlocal EnableDelayedExpansion

:: Zielverzeichnis erstellen, falls nicht vorhanden
mkdir target\test-classes 2>nul

:: Alle .java-Dateien aus src rekursiv sammeln
set "files="
for /R src %%f in (*.java) do (
    set "files=!files! %%f"
)

:: Java-Klassen kompilieren
javac -d target\test-classes -cp target !files!

echo Starte Bootstrap Server...
java -cp target\test-classes vs_beleg_ateg.bootstrap.BootstrapRegistration

endlocal