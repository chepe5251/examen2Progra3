Dim ruta, src, shell
Dim resultado

ruta = Left(WScript.ScriptFullName, InStrRev(WScript.ScriptFullName, "\"))
src  = ruta & "src"

Set shell = CreateObject("WScript.Shell")

' Paso 1: Compilar en silencio y validar el resultado
resultado = shell.Run("cmd /c """ & ruta & "compilar.bat""", 0, True)

If resultado <> 0 Then
    MsgBox "No se pudo compilar el proyecto. Revisa la configuracion de Java y JavaFX.", vbCritical, "Lab Access"
    WScript.Quit resultado
End If

' Paso 2: Lanzar la aplicacion con javaw (sin consola)
shell.Run "javaw" & _
    " --module-path ""C:\javafx-sdk-26\javafx-sdk-26\lib""" & _
    " --add-modules javafx.controls" & _
    " --enable-native-access=javafx.graphics" & _
    " -cp """ & src & """" & _
    " presentacion.MainApp", 0, False

Set shell = Nothing