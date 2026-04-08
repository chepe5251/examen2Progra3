@echo off
cd /d "%~dp0src"
java --module-path "C:\javafx-sdk-26\javafx-sdk-26\lib" --add-modules javafx.controls --enable-native-access=javafx.graphics presentacion.MainApp
