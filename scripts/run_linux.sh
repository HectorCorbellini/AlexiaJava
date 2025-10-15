#!/bin/bash

# Script para ejecutar la aplicación Alexia
# Detiene cualquier instancia previa y lanza una nueva

echo "🔍 Buscando instancias previas de la aplicación..."

# Buscar y detener procesos de spring-boot:run
PIDS=$(pgrep -f "spring-boot:run")

if [ -n "$PIDS" ]; then
    echo "⚠️  Encontradas instancias en ejecución (PIDs: $PIDS)"
    echo "🛑 Deteniendo instancias previas..."
    pkill -f "spring-boot:run"
    sleep 3
    echo "✓ Instancias previas detenidas"
else
    echo "✓ No hay instancias previas en ejecución"
fi

# Verificar que no queden procesos
REMAINING=$(pgrep -f "spring-boot:run")
if [ -n "$REMAINING" ]; then
    echo "⚠️  Forzando cierre de procesos restantes..."
    pkill -9 -f "spring-boot:run"
    sleep 2
fi

echo ""
echo "🚀 Iniciando aplicación Alexia..."
echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
echo ""

# Ejecutar la aplicación
mvn spring-boot:run
