#!/bin/bash

# Script para ejecutar la aplicación Alexia
# Detiene cualquier instancia previa, elimina webhook de Telegram y lanza una nueva

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
echo "🔧 Eliminando webhook de Telegram (si existe)..."

# Cargar variables de entorno
if [ -f .env ]; then
    export $(cat .env | grep -v '^#' | xargs)
fi

# Eliminar webhook de Telegram
if [ -n "$TELEGRAM_BOT_TOKEN" ]; then
    RESPONSE=$(curl -s "https://api.telegram.org/bot${TELEGRAM_BOT_TOKEN}/deleteWebhook")
    if echo "$RESPONSE" | grep -q '"ok":true'; then
        echo "✓ Webhook eliminado correctamente"
    else
        echo "⚠️  No se pudo eliminar el webhook (puede que no exista)"
    fi
else
    echo "⚠️  TELEGRAM_BOT_TOKEN no encontrado en .env"
fi

echo ""
echo "🚀 Iniciando aplicación Alexia..."
echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
echo ""

# Ejecutar la aplicación
mvn spring-boot:run
