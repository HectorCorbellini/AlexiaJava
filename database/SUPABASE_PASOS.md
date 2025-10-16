# Guía de Configuración de Supabase

Este documento contiene las instrucciones para crear todas las tablas necesarias en Supabase para el proyecto Alexia.

## 🔧 Instrucciones Generales

1. **Accede a Supabase**: https://supabase.com
2. **Abre el SQL Editor** en tu proyecto
3. **Ejecuta los scripts** en el orden indicado

---

## 📦 Paso 2: Tabla de Prueba de Conexión

**Archivo**: `step2_connection_test.sql`

```sql
CREATE TABLE IF NOT EXISTS connection_test (
    id SERIAL PRIMARY KEY,
    message VARCHAR(255),
    created_at TIMESTAMP DEFAULT NOW()
);
```

**Propósito**: Verificar la conexión a la base de datos.

---

## 📱 Paso 3: Tabla de Mensajes de Telegram

**Archivo**: `step3_telegram_messages.sql`

```sql
CREATE TABLE IF NOT EXISTS telegram_messages (
    id BIGSERIAL PRIMARY KEY,
    chat_id BIGINT NOT NULL,
    user_name VARCHAR(255),
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    message_text TEXT,
    bot_response TEXT,
    created_at TIMESTAMP DEFAULT NOW()
);
```

**Propósito**: Almacenar todos los mensajes recibidos y enviados por el bot de Telegram.

---

## 🤖 Paso 5: Tabla de Comandos del Bot

**Archivo**: `step5_bot_commands.sql`

```sql
CREATE TABLE IF NOT EXISTS bot_commands (
    id BIGSERIAL PRIMARY KEY,
    chat_id BIGINT NOT NULL,
    command VARCHAR(50) NOT NULL,
    user_name VARCHAR(255),
    first_name VARCHAR(255),
    created_at TIMESTAMP DEFAULT NOW()
);

-- Índices para mejorar el rendimiento
CREATE INDEX IF NOT EXISTS idx_bot_commands_chat_id ON bot_commands(chat_id);
CREATE INDEX IF NOT EXISTS idx_bot_commands_command ON bot_commands(command);
CREATE INDEX IF NOT EXISTS idx_bot_commands_created_at ON bot_commands(created_at DESC);
```

**Propósito**: Registrar todos los comandos ejecutados por los usuarios (/start, /help, /status).

---

## ✅ Verificación

Después de ejecutar cada script, verifica que las tablas se crearon correctamente:

```sql
-- Ver todas las tablas
SELECT table_name 
FROM information_schema.tables 
WHERE table_schema = 'public';

-- Verificar estructura de una tabla específica
SELECT column_name, data_type, is_nullable
FROM information_schema.columns
WHERE table_name = 'bot_commands';

-- Verificar datos
SELECT * FROM connection_test LIMIT 5;
SELECT * FROM telegram_messages LIMIT 5;
SELECT * FROM bot_commands LIMIT 5;
```

---

## 🎯 Comandos Disponibles del Bot

Una vez que todas las tablas estén creadas y la aplicación ejecutándose, podrás usar:

### `/start` - Mensaje de bienvenida
```
¡Bienvenido a Alexia! 🤖

Soy tu asistente automatizado para encontrar negocios, productos y servicios locales.

Usa /help para ver los comandos disponibles.
```

### `/help` - Lista de comandos
```
📋 Comandos disponibles:

/start - Mensaje de bienvenida
/help - Muestra esta ayuda
/status - Estado del bot

También puedes enviarme cualquier mensaje y te responderé con eco.
```

### `/status` - Estado del bot
```
✅ Bot activo y funcionando

📊 Estadísticas:
• Mensajes procesados: 42
• Comandos ejecutados: 15
• Última actualización: 16/10/2025 07:30:00
```

---

## 📊 Orden de Ejecución Recomendado

1. ✅ **Paso 2**: `step2_connection_test.sql` - Tabla de prueba
2. ✅ **Paso 3**: `step3_telegram_messages.sql` - Mensajes de Telegram
3. ⏳ **Paso 5**: `step5_bot_commands.sql` - Comandos del bot (NUEVO)

---

## 🔍 Consultas Útiles

### Ver estadísticas de comandos
```sql
SELECT command, COUNT(*) as total
FROM bot_commands
GROUP BY command
ORDER BY total DESC;
```

### Ver mensajes recientes
```sql
SELECT chat_id, user_name, message_text, created_at
FROM telegram_messages
ORDER BY created_at DESC
LIMIT 10;
```

### Ver comandos por usuario
```sql
SELECT user_name, command, COUNT(*) as total
FROM bot_commands
WHERE user_name IS NOT NULL
GROUP BY user_name, command
ORDER BY total DESC;
```
