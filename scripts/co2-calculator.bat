@echo off
setlocal enabledelayedexpansion

:: Check for --help argument
for %%A in (%*) do (
    if "%%A"=="--help" (
        echo Usage: ./co2-calculator --start [CITY] --end [CITY] --transportation-method [METHOD]
        echo.
        echo Example:
        echo   ./co2-calculator --start "Berlin" --end "Munich" --transportation-method diesel-car-medium
        echo.
        echo Available transportation methods:
        echo   - diesel-car-small, petrol-car-small, plugin-hybrid-car-small, electric-car-small
        echo   - diesel-car-medium, petrol-car-medium, plugin-hybrid-car-medium, electric-car-medium
        echo   - diesel-car-large, petrol-car-large, plugin-hybrid-car-large, electric-car-large
        echo   - bus-default, train-default
        echo.
        exit /b 0
    )
)
java -jar "..\target\co2-calculator-jar-with-dependencies.jar" %*