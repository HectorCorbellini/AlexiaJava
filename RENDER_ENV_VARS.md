# Variables de Entorno para Render

Este documento lista todas las variables de entorno necesarias para desplegar Alexia en Render.

## 📋 Variables Requeridas

### 1. Configuración de Base de Datos

```bash
# URL de conexión a PostgreSQL (Supabase o Render PostgreSQL)
DATABASE_URL=postgresql://usuario:password@host:5432/nombre_db

# Usuario de la base de datos
DB_USER=tu_usuario

# Contraseña de la base de datos
DB_PASSWORD=tu_contraseña_segura
```

**Ejemplo con Supabase:**
```bash
DATABASE_URL=postgresql://postgres.xxxxxxxxxxxx.supabase.co:5432/postgres
DB_USER=postgres
DB_PASSWORD=tu_password_de_supabase
```

### 2. Configuración de Telegram

```bash
# Token del bot de Telegram (obtener de @BotFather)
TELEGRAM_BOT_TOKEN=1234567890:ABCdefGHIjklMNOpqrsTUVwxyz123456789
```

### 3. Configuración de Spring Boot

```bash
# Perfil activo de Spring (siempre 'prod' en Render)
SPRING_PROFILES_ACTIVE=prod

# Puerto del servidor (Render lo asigna automáticamente)
PORT=8080
```

### 4. Optimización de Java (Opcional)

```bash
# Opciones de JVM para optimizar memoria en plan gratuito
JAVA_OPTS=-Xmx512m -Xms256m
```

---

## 🔧 Cómo Configurar en Render

### Opción 1: Desde el Dashboard

1. Ve a tu servicio en [Render Dashboard](https://dashboard.render.com/)
2. Click en **Environment** en el menú lateral
3. Agrega cada variable con el botón **Add Environment Variable**
4. Guarda los cambios

### Opción 2: Desde render.yaml

Las variables ya están definidas en `render.yaml` con `sync: false`, lo que significa que debes configurarlas manualmente en el dashboard por seguridad.

---

## 🔒 Seguridad

⚠️ **IMPORTANTE:**
- **NUNCA** subas archivos `.env` al repositorio
- **NUNCA** hardcodees credenciales en el código
- Usa variables de entorno para todos los datos sensibles
- Mantén tus tokens y contraseñas seguros

---

## 📝 Checklist de Configuración

Antes de desplegar, verifica que tengas:

- [ ] `DATABASE_URL` configurada
- [ ] `DB_USER` configurado
- [ ] `DB_PASSWORD` configurada
- [ ] `TELEGRAM_BOT_TOKEN` configurado
- [ ] `SPRING_PROFILES_ACTIVE=prod` configurado
- [ ] Base de datos creada y accesible
- [ ] Tablas de la base de datos creadas (ver `SUPABASE_PASOS.md`)

---

## 🆘 Solución de Problemas

### Error: "Could not connect to database"
- Verifica que `DATABASE_URL` esté correcta
- Asegúrate de que la base de datos acepte conexiones externas
- Revisa que `DB_USER` y `DB_PASSWORD` sean correctos

### Error: "Telegram bot token is invalid"
- Verifica que `TELEGRAM_BOT_TOKEN` esté correcto
- Asegúrate de que el token no tenga espacios al inicio o final
- Confirma que el bot esté activo en Telegram

### Error: "Port already in use"
- Render asigna el puerto automáticamente vía `$PORT`
- No es necesario configurar `PORT` manualmente en Render

---

## 📚 Referencias

- [Render Environment Variables](https://render.com/docs/environment-variables)
- [Supabase Connection Strings](https://supabase.com/docs/guides/database/connecting-to-postgres)
- [Telegram Bot API](https://core.telegram.org/bots/api)
