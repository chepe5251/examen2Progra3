@echo off
cd /d "%~dp0src"
javac entidades/*.java accesodatos/*.java logicaNegocio/*.java presentacion/Main.java 2>nul
java presentacion.Main
