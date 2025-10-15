@echo off
REM Script para ejecutar la aplicación Alexia en Windows
REM Detiene cualquier instancia previa y lanza una nueva

echo.
echo ========================================
echo   Alexia - Asistente Automatizado
echo ========================================
echo.

echo [1/3] Buscando instancias previas...

REM Buscar procesos de Maven
tasklist /FI "IMAGENAME eq java.exe" /FI "WINDOWTITLE eq *spring-boot*" 2>NUL | find /I "java.exe" >NUL

if %ERRORLEVEL% EQU 0 (
    echo [!] Encontradas instancias en ejecucion
    echo [2/3] Deteniendo instancias previas...
    
    REM Detener procesos de Maven/Spring Boot
    for /f "tokens=2" %%a in ('tasklist /FI "IMAGENAME eq java.exe" /FI "WINDOWTITLE eq *spring-boot*" ^| find /I "java.exe"') do (
        taskkill /PID %%a /F >NUL 2>&1
    )
    
    REM Esperar un momento
    timeout /t 3 /nobreak >NUL
    echo [OK] Instancias previas detenidas
) else (
    echo [OK] No hay instancias previas en ejecucion
)

echo.
echo [3/3] Iniciando aplicacion Alexia...
echo ========================================
echo.

REM Ejecutar la aplicación
call mvn spring-boot:run

if %ERRORLEVEL% NEQ 0 (
    echo.
    echo [ERROR] La aplicacion no pudo iniciarse
    echo Verifica que Maven este instalado y configurado correctamente
    pause
    exit /b 1
)
