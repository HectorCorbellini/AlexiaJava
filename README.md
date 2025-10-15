# Alexia - Asistente Automatizado

Asistente automatizado que ayuda a usuarios de WhatsApp y Telegram a encontrar negocios, productos y servicios locales usando IA y fuentes verificadas.

# Alexia - Asistente Automatizado

Asistente automatizado que ayuda a usuarios de WhatsApp y Telegram a encontrar negocios, productos y servicios locales usando IA y fuentes verificadas.

## 🚀 Estado Actual: PASO 3 COMPLETADO ✅ - Integración con Telegram

Este proyecto se está desarrollando de forma **incremental**, paso por paso, probando cada funcionalidad antes de continuar.

✅ **3 pasos completados de 10** (30% progreso)

Ver el plan completo en: [PLAN_INCREMENTAL.md](PLAN_INCREMENTAL.md)

## 📦 Código Fuente

**Repositorio en GitHub**: [https://github.com/HectorCorbellini/AlexiaJava](https://github.com/HectorCorbellini/AlexiaJava)

```bash
# Clonar el proyecto
git clone https://github.com/HectorCorbellini/AlexiaJava.git
cd AlexiaJava

# Compilar y ejecutar
mvn clean compile
mvn spring-boot:run
```

## 📋 Requisitos

- Java 17 o superior
- Maven 3.6+
- Cuenta de Supabase (PostgreSQL)
- Token de Telegram Bot (ya configurado)

## ⚙️ Configuración Inicial

1. **Clonar el repositorio**
```bash
cd /home/uko/COLOMBIA/JAVA/2do-Intento-JAVA/javaDos-
```

2. **Variables de entorno ya configuradas**
```bash
# El archivo .env ya está configurado con tus credenciales
# Token de Telegram: ✅ Configurado y verificado
# Credenciales de Supabase: ✅ Configuradas
```

3. **Crear tablas en Supabase**
```sql
-- Paso 2: Tabla de prueba de conexión
CREATE TABLE IF NOT EXISTS connection_test (
    id SERIAL PRIMARY KEY,
    message VARCHAR(255),
    created_at TIMESTAMP DEFAULT NOW()
);

-- Paso 3: Tabla de mensajes de Telegram
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

4. **Compilar el proyecto**
```bash
mvn clean compile
```

5. **Ejecutar la aplicación**
```bash
mvn spring-boot:run
```

6. **Acceder al dashboard**
```
http://localhost:8080
```

7. **Probar funcionalidades**
- ✅ **Conexión a Supabase**: Botón "Probar Conexión" en dashboard
- ✅ **Bot de Telegram**: Enviar mensaje a `@ukoquique_bot`

## 📦 Tecnologías

- **Backend**: Spring Boot 3.1.5
- **Frontend**: Vaadin 24.2.5 (Dashboard profesional)
- **Base de datos**: Supabase (PostgreSQL)
- **Bot**: Telegram Bots API 6.8.0
- **Java**: 17
- **Build**: Maven
- **Env Management**: Dotenv Java

## 🎯 Funcionalidades Implementadas

### ✅ Paso 1: Proyecto Base y Dashboard Básico
- [x] Estructura Maven configurada
- [x] Dashboard web básico con Vaadin
- [x] Mensaje de bienvenida inicial

### ✅ Paso 2: Conexión a Supabase
- [x] Entidad JPA `ConnectionTest`
- [x] Repositorio Spring Data JPA
- [x] Servicio de base de datos con DTOs
- [x] Use Case para desacoplamiento
- [x] Botón de prueba en dashboard
- [x] Carga automática de variables `.env`
- [x] Conexión verificada y funcionando

### ✅ Mejora UI: Dashboard Profesional Completo
- [x] MainLayout con menú lateral organizado
- [x] 8 cards de métricas con colores distintivos
- [x] Estado del sistema con badges visuales
- [x] 13 vistas placeholder para todas las secciones
- [x] Navegación fluida entre secciones
- [x] Diseño profesional con sombras y bordes

### ✅ Paso 3: Integración Básica con Telegram
- [x] Bot funcional `@ukoquique_bot` con respuestas eco
- [x] Persistencia automática de mensajes en Supabase
- [x] Logging completo de actividad del bot
- [x] Manejo robusto de errores
- [x] Token verificado y configurado correctamente

### ⏳ Próximos Pasos
- [ ] Paso 4: Dashboard con logs de Telegram
- [ ] Paso 5: Comandos básicos del bot (/start, /help, /status)
- [ ] Paso 6: Búsqueda simple por categoría
- [ ] Paso 7: CRUD completo de negocios
- [ ] Paso 8: Integración con IA (Grok)
- [ ] Paso 9: Búsqueda por ubicación (PostGIS)
- [ ] Paso 10: Dashboard con métricas avanzadas

## 📝 Estructura del Proyecto

```
javaDos-/
├── database/
│   ├── step2_connection_test.sql
│   └── step3_telegram_messages.sql
├── scripts/
│   └── delete_webhook.sh
├── src/
│   └── main/
│       ├── java/com/alexia/
│       │   ├── AlexiaApplication.java
│       │   ├── constants/           ← NUEVO
│       │   │   ├── Messages.java
│       │   │   └── UIConstants.java
│       │   ├── dto/                 ← NUEVO
│       │   │   ├── ConnectionResultDTO.java
│       │   │   └── TelegramMessageDTO.java
│       │   ├── entity/
│       │   │   ├── ConnectionTest.java
│       │   │   └── TelegramMessage.java
│       │   ├── repository/
│       │   │   ├── ConnectionTestRepository.java
│       │   │   └── TelegramMessageRepository.java
│       │   ├── service/
│       │   │   └── DatabaseService.java
│       │   ├── telegram/            ← NUEVO
│       │   │   └── AlexiaTelegramBot.java
│       │   ├── usecase/             ← NUEVO
│       │   │   └── TestConnectionUseCase.java
│       │   ├── config/              ← NUEVO
│       │   │   └── TelegramBotConfig.java
│       │   └── views/
│       │       ├── MainLayout.java
│       │       ├── DashboardView.java (refactorizado)
│       │       ├── BusinessesView.java
│       │       ├── ProductsView.java
│       │       ├── CampaignsView.java
│       │       ├── LeadsView.java
│       │       ├── TelegramView.java
│       │       ├── WhatsAppView.java
│       │       ├── ConversationsView.java
│       │       ├── MetricsView.java
│       │       ├── BillingView.java
│       │       ├── TrackingView.java
│       │       ├── ConfigurationView.java
│       │       ├── DatabaseView.java
│       │       └── LogsView.java
│       └── resources/
│           └── application.properties
├── .env (configurado)
├── pom.xml (con telegrambots)
├── PLAN_INCREMENTAL.md
├── CHANGELOG.md
├── MEJORAS_PENDIENTES.md
└── README.md
```

## 🔧 Comandos Útiles

### Ejecutar la Aplicación

```bash
# Linux/Mac: Usar el script (recomendado)
# Detiene instancias previas automáticamente y lanza la aplicación
./scripts/run_linux.sh

# Windows: Usar el script batch
scripts\run_windows.bat

# Cualquier OS: Maven directo
mvn spring-boot:run
```

### Compilar

```bash
# Compilar sin ejecutar
mvn clean compile

# Compilar y empaquetar
mvn clean package
```

### Logs y Debugging

```bash
# Linux/Mac: Ver logs del bot de Telegram
tail -f /proc/$(pgrep -f "spring-boot:run")/fd/1 | grep -i telegram

# Linux/Mac: Ver todos los logs en tiempo real
tail -f /proc/$(pgrep -f "spring-boot:run")/fd/1

# Windows: Los logs aparecen directamente en la consola
```

### Detener la Aplicación

```bash
# Linux/Mac: Detener todas las instancias
pkill -f "spring-boot:run"

# Linux/Mac: Forzar detención si no responde
pkill -9 -f "spring-boot:run"

# Windows: Presionar Ctrl+C en la consola o cerrar la ventana
```

### Utilidades de Telegram

```bash
# Eliminar webhook si es necesario (Linux/Mac)
./scripts/delete_webhook.sh
```

## 🎮 Usar el Bot de Telegram

1. **Abrir Telegram** (móvil o web)
2. **Buscar el bot**: `@ukoquique_bot`
3. **Enviar mensaje**: `Hola Alexia`
4. **Recibir respuesta**: `Recibí tu mensaje: Hola Alexia`
5. **Ver en Supabase**: Los mensajes se guardan automáticamente

## 📚 Documentación

- [Plan de Desarrollo Incremental](PLAN_INCREMENTAL.md)
- [Registro de Cambios](CHANGELOG.md)
- [Mejoras Pendientes](MEJORAS_PENDIENTES.md)
- [Spring Boot Docs](https://spring.io/projects/spring-boot)
- [Vaadin Docs](https://vaadin.com/docs)
- [Telegram Bots API](https://core.telegram.org/bots/api)

## 🐛 Troubleshooting

### Error de conexión a base de datos
Verificar que las credenciales en `.env` sean correctas.

### Puerto 8080 en uso
Cambiar el puerto en `application.properties`:
```properties
server.port=8081
```

### Error 401 en bot de Telegram
- El token puede haber expirado
- Usa @BotFather para obtener un token nuevo
- Actualiza `TELEGRAM_BOT_TOKEN` en `.env`

### El bot no responde
```bash
# Verificar que la aplicación esté corriendo
curl http://localhost:8080

# Verificar logs del bot
tail -f /proc/$(pgrep -f "spring-boot:run")/fd/1 | grep -i "telegram\|bot"
```

## 🔐 Manejo del Archivo .env

### ⚠️ IMPORTANTE: Seguridad del .env

El archivo `.env` contiene **credenciales sensibles** (API keys, passwords) que **NO deben subirse a GitHub**.

### 📝 Workflow Recomendado

#### **Durante el Desarrollo Local**:
```bash
# 1. Comentarizar .env en .gitignore para poder modificarlo
# Editar .gitignore y cambiar:
.env
# por:
#.env

# 2. Ahora puedes editar .env con tus credenciales reales
nano .env

# 3. Ejecutar la aplicación normalmente
mvn spring-boot:run
```

#### **Antes de Subir a GitHub**:
```bash
# 1. Descomentarizar .env en .gitignore
# Editar .gitignore y cambiar:
#.env
# por:
.env

# 2. Verificar que .env no esté en staging
git status
# No debe aparecer .env en la lista

# 3. Hacer commit y push
git add .
git commit -m "Tu mensaje"
git push origin main
```

### 🛡️ Protección Automática de GitHub

GitHub tiene **push protection** que bloquea automáticamente commits con:
- API keys (Groq, OpenAI, etc.)
- Tokens de acceso
- Passwords
- Claves privadas

Si ves este error:
```
remote: error: GH013: Repository rule violations found
remote: - Push cannot contain secrets
```

**Solución**:
```bash
# Remover .env del commit
git rm --cached .env

# Asegurarse que .env esté en .gitignore
echo ".env" >> .gitignore

# Hacer nuevo commit
git add .gitignore
git commit --amend -m "Tu mensaje (sin .env)"
git push origin main
```

### 📋 Archivo .env.example

El repositorio incluye `.env.example` con valores de ejemplo:
```bash
# Copiar para crear tu .env local
cp .env.example .env

# Editar con tus credenciales reales
nano .env
```

**Nunca** pongas credenciales reales en `.env.example` - este archivo SÍ se sube a GitHub.

## 📊 Progreso del Desarrollo

| Paso | Estado | Fecha | Descripción |
|------|--------|-------|-------------|
| 1 | ✅ | 2025-10-14 | Proyecto base y dashboard básico |
| 2 | ✅ | 2025-10-14 | Conexión a Supabase verificada |
| UI | ✅ | 2025-10-14 | Dashboard profesional con 13 vistas |
| 3 | ✅ | 2025-10-14 | Bot de Telegram funcional con eco |
| 4 | ⏳ | Próximo | Dashboard con logs de Telegram |
| 5 | ⏳ | Próximo | Comandos básicos del bot |
| 6 | ⏳ | Próximo | Búsqueda por categoría |
| 7 | ⏳ | Próximo | CRUD completo de negocios |
| 8 | ⏳ | Próximo | Integración con IA |
| 9 | ⏳ | Próximo | Búsqueda por ubicación |
| 10 | ⏳ | Próximo | Dashboard con métricas |

**Progreso actual**: 3/10 pasos = **30% completado**

## 📄 Licencia

Este proyecto es privado y está en desarrollo activo.

---

**Versión**: 1.0.0  
**Última actualización**: 2025-10-14  
**Estado**: Paso 3 completado - Bot de Telegram funcional  
**Próximo paso**: Dashboard con logs de Telegram
