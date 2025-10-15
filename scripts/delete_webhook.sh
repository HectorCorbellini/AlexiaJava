#!/bin/bash

# Script para eliminar el webhook de Telegram
# Esto es necesario cuando el bot estaba configurado con webhook y queremos usar polling

TOKEN="8479048647:AAHoT022P5vgDtNuB7OB7KLBuBTSOTZNzcc"

echo "🔧 Eliminando webhook de Telegram..."

# Eliminar webhook
curl -X POST "https://api.telegram.org/bot${TOKEN}/deleteWebhook"

echo ""
echo "✅ Webhook eliminado. Ahora puedes ejecutar el bot con polling."
echo ""
echo "Para verificar el estado del bot:"
curl -X GET "https://api.telegram.org/bot${TOKEN}/getMe"
