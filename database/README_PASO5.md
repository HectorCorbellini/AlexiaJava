# Paso 5: Comandos Básicos del Bot

## 📋 Tabla a Crear en Supabase

Antes de ejecutar la aplicación, debes crear la tabla `bot_commands` en Supabase.

### 🔧 Instrucciones

1. **Accede a Supabase**: https://supabase.com
2. **Abre el SQL Editor**
3. **Ejecuta el siguiente script**: `step5_bot_commands.sql`

### 📊 Estructura de la Tabla

```sql
CREATE TABLE IF NOT EXISTS bot_commands (
    id BIGSERIAL PRIMARY KEY,
    chat_id BIGINT NOT NULL,
    command VARCHAR(50) NOT NULL,
    user_name VARCHAR(255),
    first_name VARCHAR(255),
    created_at TIMESTAMP DEFAULT NOW()
);
```

### ✅ Verificación

Después de ejecutar el script, verifica que la tabla se creó correctamente:

```sql
SELECT * FROM bot_commands LIMIT 5;
```

### 🎯 Comandos Disponibles

Una vez que la tabla esté creada y la aplicación ejecutándose, podrás usar:

- `/start` - Mensaje de bienvenida
- `/help` - Lista de comandos disponibles
- `/status` - Estado del bot con estadísticas

### 📝 Ejemplo de Uso

```
Usuario: /start
Bot: ¡Bienvenido a Alexia! 🤖

Soy tu asistente automatizado para encontrar negocios, productos y servicios locales.

Usa /help para ver los comandos disponibles.
```

```
Usuario: /help
Bot: 📋 Comandos disponibles:

/start - Mensaje de bienvenida
/help - Muestra esta ayuda
/status - Estado del bot

También puedes enviarme cualquier mensaje y te responderé con eco.
```

```
Usuario: /status
Bot: ✅ Bot activo y funcionando

📊 Estadísticas:
• Mensajes procesados: 42
• Comandos ejecutados: 15
• Última actualización: 16/10/2025 07:30:00
```
