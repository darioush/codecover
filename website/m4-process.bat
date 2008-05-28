@ECHO OFF
SETLOCAL
SET WebsiteHome=%~d0%~sp0
IF NOT EXIST "%WebsiteHome%website.inc.m4" GOTO VERZEICHNIS
SET TargetHome=%WebsiteHome%..\website-complete\

IF EXIST "%TargetHome%" RMDIR /S /Q "%TargetHome%"

ECHO m4-Makros aufl”sen
CALL m4-process-folder.bat %WebsiteHome% %TargetHome%

ECHO.
ECHO Restliche Dateien kopieren
ECHO .m4 > ignored
ECHO .sh >> ignored
ECHO .bat >> ignored
ECHO ignored >> ignored
XCOPY /S /Y /Q /EXCLUDE:ignored *.* %TargetHome%
DEL ignored

GOTO ENDE

:VERZEICHNIS
ECHO Verzeichnis "%WebsiteHome%" ist nicht das website Verzeichnis.
GOTO ENDE

:ENDE
REM ECHO ENDE!
REM PAUSE
ENDLOCAL