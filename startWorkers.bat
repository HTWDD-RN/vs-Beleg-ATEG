@echo off
setlocal enabledelayedexpansion

rem === Konfiguration ===
set COUNT=20
set HOST=localhost

rem === Worker starten ===
echo Starte !COUNT! Worker mit Master-Host: %HOST%
for /L %%i in (1,1,%COUNT%) do (
    echo Starte Worker %%i...
    start runWorker localhost %HOST%
)