# Instrucciones para Completar el Paso 3

## 🎯 Objetivo
Integrar el bot de Telegram con respuestas eco y guardar mensajes en Supabase.

---

## ✅ Lo que ya está hecho

1. ✅ Dependencia `telegrambots` agregada al `pom.xml`
2. ✅ Entidad `TelegramMessage.java` creada
3. ✅ Repositorio `TelegramMessageRepository.java` creado
4. ✅ DTO `TelegramMessageDTO.java` creado
5. ✅ Servicio `TelegramService.java` creado
6. ✅ Bot `AlexiaTelegramBot.java` implementado
7. ✅ Configuración `TelegramBotConfig.java` creada
8. ✅ Propiedades de Telegram agregadas a `application.properties`
9. ✅ Código compilado exitosamente (29 archivos)

---

## 📋 Paso a Paso para Completar

### 1. Crear la Tabla en Supabase

**Ve a tu proyecto Supabase**: https://hgcesbylhkjoxtymxysy.supabase.co

**Pasos**:
1. Haz clic en "SQL Editor" en el menú lateral
2. Copia y pega el siguiente SQL:

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

CREATE INDEX IF NOT EXISTS idx_telegram_messages_chat_id ON telegram_messages(chat_id);
CREATE INDEX IF NOT EXISTS idx_telegram_messages_created_at ON telegram_messages(created_at DESC);
```

3. Haz clic en "Run"
4. Verifica que aparezca "Success. No rows returned"

---

### 2. Ejecutar la Aplicación

Una vez creada la tabla, ejecuta:

```bash
mvn spring-boot:run
```

**Deberías ver en los logs**:
```
✓ Bot de Telegram inicializado: @AlexiaBot
✓ Bot de Telegram registrado exitosamente
✓ Alexia Application Started Successfully!
```

---

### 3. Probar el Bot en Telegram

1. **Abre Telegram** (móvil o web)

2. **Busca el bot**: 
   - Busca `@AlexiaBot` (o el nombre que configuraste)
   - O usa este link directo con el token: `t.me/[tu_bot_username]`

3. **Envía un mensaje**:
   ```
   Hola Alexia
   ```

4. **Deberías recibir**:
   ```
   Recibí tu mensaje: Hola Alexia
   ```

5. **Verifica en los logs de la aplicación**:
   ```
   Mensaje recibido de [TuNombre] (123456789): Hola Alexia
   Mensaje de Telegram guardado. ID: 1, Chat: 123456789, Usuario: TuNombre
   Respuesta enviada a chat 123456789
   ```

---

### 4. Verificar en Supabase

1. Ve a Supabase → "Table Editor"
2. Selecciona la tabla `telegram_messages`
3. Deberías ver tu mensaje guardado con:
   - chat_id
   - user_name
   - first_name
   - message_text: "Hola Alexia"
   - bot_response: "Recibí tu mensaje: Hola Alexia"
   - created_at

---

## 🧪 Pruebas Adicionales

### Prueba 1: Múltiples Mensajes
Envía varios mensajes seguidos:
```
Hola
¿Cómo estás?
Esto es una prueba
```

Cada uno debería recibir su respuesta eco.

### Prueba 2: Verificar Contador
En Supabase SQL Editor:
```sql
SELECT COUNT(*) FROM telegram_messages;
```

Debería mostrar el número de mensajes enviados.

### Prueba 3: Ver Mensajes por Usuario
```sql
SELECT 
    first_name,
    message_text,
    bot_response,
    created_at
FROM telegram_messages
ORDER BY created_at DESC
LIMIT 10;
```

---

## 🐛 Troubleshooting

### Error: "Bot not found"
**Problema**: El bot no está registrado en Telegram.
**Solución**: Verifica que el `TELEGRAM_BOT_TOKEN` en `.env` sea correcto.

### Error: "Unauthorized"
**Problema**: Token inválido o expirado.
**Solución**: Regenera el token con @BotFather en Telegram.

### Error: "Table doesn't exist"
**Problema**: La tabla no se creó en Supabase.
**Solución**: Ejecuta el SQL del paso 1 nuevamente.

### El bot no responde
**Problema**: La aplicación no está corriendo o hay un error.
**Solución**: 
1. Verifica los logs de la aplicación
2. Busca errores en rojo
3. Asegúrate de que aparezca "Bot de Telegram registrado exitosamente"

---

## ✅ Criterios de Éxito

El Paso 3 está **COMPLETADO** cuando:

- [x] La tabla `telegram_messages` existe en Supabase
- [ ] La aplicación inicia sin errores
- [ ] El bot responde a mensajes en Telegram
- [ ] Los mensajes se guardan en Supabase
- [ ] Los logs muestran la actividad correctamente

---

## 📊 Datos del Bot

**Token**: `8479048647:AAHoT022P5vgDtNuB7OB7KLBuBTSOTZNzcc`
**Username**: `@AlexiaBot` (o el que configuraste con @BotFather)

---

## 🚀 Próximo Paso

Una vez completado el Paso 3, continuaremos con:
**Paso 4: Dashboard con Logs de Telegram**
- Mostrar mensajes en el dashboard
- Grid con historial de conversaciones
- Filtros y búsqueda

---

**Fecha**: 2025-10-14  
**Estado**: Listo para probar
