@echo off
setlocal

set "ROOT=%~dp0"
set "SRC=%ROOT%src"
set "JAVAFX_LIB=C:\javafx-sdk-26\javafx-sdk-26\lib"

if not exist "%JAVAFX_LIB%" (
    echo No se encontro JavaFX en "%JAVAFX_LIB%".
    echo Ajusta la variable JAVAFX_LIB dentro de compilar.bat.
    exit /b 1
)

pushd "%SRC%" || exit /b 1

echo Compilando proyecto...
javac --module-path "%JAVAFX_LIB%" --add-modules javafx.controls ^
    entidades\*.java ^
    accesodatos\*.java ^
    logicaNegocio\*.java ^
    presentacion\util\EstilosUI.java ^
    presentacion\controladores\*.java ^
    presentacion\Main.java ^
    presentacion\MainApp.java

set "EXITCODE=%ERRORLEVEL%"
popd

if not "%EXITCODE%"=="0" (
    exit /b %EXITCODE%
)

echo Compilacion completada.
exit /b 0