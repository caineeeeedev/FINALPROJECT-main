@echo off
title QuitiQuiz
cd /d "%~dp0"

if not exist out mkdir out

javac -d out src\*.java

if errorlevel 1 (
    echo.
    echo The program could not be compiled.
    echo Make sure Java JDK is installed.
    pause
    exit
)

cls
java -cp out Main

echo.
pause