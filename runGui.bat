@echo off
setlocal EnableDelayedExpansion
echo Kompiliere Java-Klassen...

:: Zielverzeichnis erstellen (falls nicht vorhanden)
mkdir target\test-classes 2>nul

:: Alle Java-Dateien aus src\ rekursiv sammeln
set "files="
for /R src %%f in (*.java) do (
    set "files=!files! %%f"
)

javac -d target\test-classes -cp target !files!
endlocal

echo Starte GUI...
java -cp target\test-classes vs_beleg_ateg.gui.GUI
