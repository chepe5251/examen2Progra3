@echo off
setlocal

set "ROOT=%~dp0"
set "SRC=%ROOT%src"
set "JAVAFX_LIB=C:\javafx-sdk-26\javafx-sdk-26\lib"

call "%ROOT%compilar.bat" || exit /b 1

pushd "%SRC%" || exit /b 1
java --module-path "%JAVAFX_LIB%" --add-modules javafx.controls --enable-native-access=javafx.graphics presentacion.MainApp
set "EXITCODE=%ERRORLEVEL%"
popd

exit /b %EXITCODE%