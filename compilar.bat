@echo off
echo Compilando proyecto...
cd /d "%~dp0src"
javac --module-path "C:\javafx-sdk-26\javafx-sdk-26\lib" --add-modules javafx.controls entidades/*.java accesodatos/*.java logicaNegocio/*.java presentacion/util/EstilosUI.java presentacion/controladores/*.java presentacion/MainApp.java 2>&1 | findstr /v "Note:"
echo.
echo Listo. Ejecuta run.bat para abrir la aplicacion.
pause
