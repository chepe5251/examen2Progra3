@echo off
setlocal

set "ROOT=%~dp0"
set "SRC=%ROOT%src"

pushd "%SRC%" || exit /b 1
javac entidades\*.java accesodatos\*.java logicaNegocio\*.java presentacion\Main.java
if errorlevel 1 (
    set "EXITCODE=%ERRORLEVEL%"
    popd
    exit /b %EXITCODE%
)

java presentacion.Main
set "EXITCODE=%ERRORLEVEL%"
popd

exit /b %EXITCODE%