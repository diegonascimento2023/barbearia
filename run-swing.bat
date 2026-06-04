@echo off
title Sistema da Barbearia - Swing
cls

cd /d "%~dp0"

set "LOCAL_MAVEN=%USERPROFILE%\Tools\apache-maven-3.9.16\bin\mvn.cmd"

if exist "%LOCAL_MAVEN%" (
    call "%LOCAL_MAVEN%" -q compile exec:java -Dexec.mainClass=com.barbearia.SwingApp
) else (
    call mvn -q compile exec:java -Dexec.mainClass=com.barbearia.SwingApp
)

pause
