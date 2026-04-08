@echo off
cd /d "%~dp0src"

echo Compilando...
javac --module-path "C:\javafx-sdk-26\javafx-sdk-26\lib" --add-modules javafx.controls entidades/*.java accesodatos/*.java logicaNegocio/*.java presentacion/util/EstilosUI.java presentacion/controladores/*.java presentacion/MainApp.java 2>nul

if %errorlevel% neq 0 (
    echo ERROR: No se pudo compilar el proyecto.
    pause
    exit /b 1
)

java --module-path "C:\javafx-sdk-26\javafx-sdk-26\lib" --add-modules javafx.controls --enable-native-access=javafx.graphics presentacion.MainApp
