@echo off
setlocal EnableDelayedExpansion

:: Prüfe Argumentanzahl
if "%~1"=="" (
    echo Nutzung: run_worker.bat ^<master-host^>
    exit /b 1
)

set "MASTER_HOST=%~1"

:: Zielverzeichnis erstellen
mkdir target\test-classes 2>nul

:: Alle .java-Dateien aus src\ rekursiv sammeln
set "files="
for /R src %%f in (*.java) do (
    set "files=!files! %%f"
)

:: Java-Dateien kompilieren
javac -d target\test-classes -cp target !files!

:: Worker starten
echo Starte Worker mit Master '!MASTER_HOST!'...
java -cp target\test-classes vs_beleg_ateg.worker.WorkerServer !MASTER_HOST!

endlocal
