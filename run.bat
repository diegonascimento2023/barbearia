@echo off
title Sistema da Barbearia
cls

cd /d "%~dp0"

set "LOCAL_MAVEN=%USERPROFILE%\Tools\apache-maven-3.9.16\bin\mvn.cmd"

if exist "%LOCAL_MAVEN%" (
    call "%LOCAL_MAVEN%" -q compile exec:java -Dexec.mainClass=com.barbearia.App
) else (
    call mvn -q compile exec:java -Dexec.mainClass=com.barbearia.App
)

pause
