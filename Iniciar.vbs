Dim ruta, src, shell

ruta = Left(WScript.ScriptFullName, InStrRev(WScript.ScriptFullName, "\"))
src  = ruta & "src"

Set shell = CreateObject("WScript.Shell")

' Paso 1: Compilar en silencio (ventana oculta, espera a que termine)
shell.Run "cmd /c cd /d """ & src & """ && javac" & _
    " --module-path ""C:\javafx-sdk-26\javafx-sdk-26\lib""" & _
    " --add-modules javafx.controls" & _
    " entidades/*.java accesodatos/*.java logicaNegocio/*.java" & _
    " presentacion/util/EstilosUI.java" & _
    " presentacion/controladores/*.java" & _
    " presentacion/MainApp.java", 0, True

' Paso 2: Lanzar la aplicacion con javaw (sin consola)
shell.Run "javaw" & _
    " --module-path ""C:\javafx-sdk-26\javafx-sdk-26\lib""" & _
    " --add-modules javafx.controls" & _
    " --enable-native-access=javafx.graphics" & _
    " -cp """ & src & """" & _
    " presentacion.MainApp", 0, False

Set shell = Nothing
