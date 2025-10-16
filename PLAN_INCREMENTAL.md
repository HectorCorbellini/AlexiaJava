# Alexia - Plan de Desarrollo Incremental

## 🎯 Objetivo
Crear la aplicación Alexia paso a paso, probando cada funcionalidad antes de continuar. Cada paso será pequeño, funcional y verificable.

---

## 📋 Lecciones del Primer Intento

### ❌ Problemas del primer intento:
- Se intentó crear toda la aplicación de una vez
- Código sobrecargado sin pruebas intermedias
- No se verificó que cada componente funcionara antes de continuar
- Demasiadas dependencias y configuraciones simultáneas

### ✅ Nuevo enfoque:
- **Desarrollo incremental**: Un paso a la vez
- **Pruebas continuas**: Ejecutar y verificar después de cada paso
- **Funcionalidad mínima**: Solo lo necesario en cada etapa
- **Sin código innecesario**: Solo agregar lo que se va a usar

---

## 🏗️ Arquitectura Simplificada (Fase Inicial)

```
┌─────────────────────────────────────────┐
│         USUARIO (Telegram)              │
└──────────────┬──────────────────────────┘
               │
               ▼
┌─────────────────────────────────────────┐
│    ALEXIA BACKEND (Spring Boot)         │
│                                          │
│  ┌────────────┐      ┌────────────┐    │
│  │  Telegram  │◄────►│  Grok AI   │    │
│  │   Service  │      │  Service   │    │
│  └────────────┘      └────────────┘    │
│         │                   │           │
│         │            ┌──────▼──────┐   │
│         │            │  OpenAI     │   │
│         │            │  Service    │   │
│         │            │ (Opcional)  │   │
│         │            └─────────────┘   │
│         │                               │
│  ┌──────▼──────┐                       │
│  │  Dashboard  │                       │
│  │  (Vaadin)   │                       │
│  └─────────────┘                       │
└──────────────┬──────────────────────────┘
               │
               ▼
┌─────────────────────────────────────────┐
│      SUPABASE (PostgreSQL)              │
│  - telegram_messages                    │
│  - ai_conversations                     │
│  - bot_commands                         │
│  - businesses (futuro)                  │
└─────────────────────────────────────────┘
```

---

## 📦 Pasos de Desarrollo

### **PASO 1: Proyecto Base y Dashboard Básico** ✅ COMPLETADO
**Objetivo**: Crear un proyecto Spring Boot + Vaadin que compile y muestre un dashboard vacío.

**Tareas**:
- [x] Crear estructura Maven básica
- [x] Configurar `pom.xml` con dependencias mínimas:
  - Spring Boot Starter Web
  - Spring Boot Starter Data JPA
  - PostgreSQL Driver
  - Vaadin (versión compatible)
  - Lombok
- [x] Crear `application.properties` básico
- [x] Crear clase principal `AlexiaApplication.java`
- [x] Crear `DashboardView.java` con mensaje "Bienvenido a Alexia"
- [x] Crear archivo `.env` con credenciales de Supabase

**Verificación**:
```bash
mvn clean install  # ✅ BUILD SUCCESS
mvn spring-boot:run  # ✅ Application Started
# Abrir http://localhost:8080
# ✅ Muestra: "Bienvenido a Alexia"
```

**Archivos creados**:
- `pom.xml`
- `src/main/java/com/alexia/AlexiaApplication.java`
- `src/main/java/com/alexia/views/DashboardView.java`
- `src/main/resources/application.properties`
- `.env`
- `.env.example`
- `README.md`
- `.gitignore`

**Fecha de completado**: 2025-10-14

---

### **PASO 2: Conexión a Supabase** ✅ COMPLETADO
**Objetivo**: Conectar la aplicación a la base de datos Supabase y verificar la conexión.

**Tareas**:
- [x] Configurar conexión a PostgreSQL en `application.properties`
- [x] Crear entidad simple `ConnectionTest.java`
- [x] Crear repositorio `ConnectionTestRepository.java`
- [x] Crear tabla `connection_test` en Supabase
- [x] Agregar botón en Dashboard para probar conexión
- [x] Mostrar resultado de la prueba en pantalla
- [x] Agregar dependencia `dotenv-java` para cargar `.env`
- [x] Configurar carga automática de variables de entorno

**Verificación**:
```bash
mvn spring-boot:run
# Abrir http://localhost:8080
# Hacer clic en "Probar Conexión"
# ✅ Muestra: "✓ Conexión exitosa a Supabase!"
# ✅ Registro guardado con ID: 1
```

**Archivos creados**:
- `src/main/java/com/alexia/entity/ConnectionTest.java`
- `src/main/java/com/alexia/repository/ConnectionTestRepository.java`
- `src/main/java/com/alexia/service/DatabaseService.java`
- `database/step2_connection_test.sql`

**Archivos modificados**:
- `pom.xml` (agregada dependencia dotenv-java)
- `src/main/java/com/alexia/AlexiaApplication.java` (carga de .env)
- `src/main/java/com/alexia/views/DashboardView.java` (botón de prueba)

**SQL ejecutado en Supabase**:
```sql
CREATE TABLE connection_test (
    id SERIAL PRIMARY KEY,
    message VARCHAR(255),
    created_at TIMESTAMP DEFAULT NOW()
);
```

**Fecha de completado**: 2025-10-14

---

### **MEJORA UI: Dashboard Profesional Completo** ✅ COMPLETADO
**Objetivo**: Crear un dashboard profesional con todas las secciones del proyecto visibles.

**Tareas**:
- [x] Crear `MainLayout.java` con menú lateral
- [x] Organizar menú en 4 secciones temáticas
- [x] Rediseñar `DashboardView.java` con métricas visuales
- [x] Crear 8 cards de métricas con iconos y colores
- [x] Agregar sección de estado del sistema con badges
- [x] Crear 13 vistas placeholder para todas las secciones
- [x] Implementar navegación entre vistas

**Vistas creadas**:
- `BusinessesView.java` - Gestión de negocios
- `ProductsView.java` - Catálogo de productos
- `CampaignsView.java` - Campañas CPC/CPA
- `LeadsView.java` - Gestión de leads
- `TelegramView.java` - Bot de Telegram
- `WhatsAppView.java` - Bot de WhatsApp
- `ConversationsView.java` - Historial de conversaciones
- `MetricsView.java` - Análisis de métricas
- `BillingView.java` - Facturación automática
- `TrackingView.java` - Sistema de tracking
- `ConfigurationView.java` - Configuración general
- `DatabaseView.java` - Gestión de BD
- `LogsView.java` - Registro de actividad

**Características del Dashboard**:
- 8 cards de métricas con colores distintivos
- Badges de estado para servicios (Supabase ✅, otros ⏳)
- Menú lateral con iconos y secciones organizadas
- Navegación fluida entre todas las vistas
- Diseño profesional con sombras y bordes de color

**Fecha de completado**: 2025-10-14

---

### **PASO 3: Integración Básica con Telegram** ✅ COMPLETADO
**Objetivo**: Conectar el bot de Telegram y recibir/responder mensajes simples.

**Tareas**:
- [x] Agregar dependencia `telegrambots` al `pom.xml`
- [x] Crear clase `TelegramBotConfig.java` con token del `.env`
- [x] Crear clase `AlexiaTelegramBot.java` que extienda `TelegramLongPollingBot`
- [x] Implementar método `onUpdateReceived()` para recibir mensajes
- [x] Crear entidad `TelegramMessage.java` para persistencia
- [x] Crear repositorio `TelegramMessageRepository.java`
- [x] Crear servicio `TelegramService.java` para lógica de negocio
- [x] Crear DTO `TelegramMessageDTO.java` para transferencia de datos
- [x] Crear tabla `telegram_messages` en Supabase
- [x] Configurar propiedades de Telegram en `application.properties`
- [x] Crear script para eliminar webhook si es necesario

**Verificación**:
```bash
mvn spring-boot:run
# ✅ Bot de Telegram inicializado: @ukoquique_bot
# ✅ Bot de Telegram registrado exitosamente
# ✅ Aplicación corriendo en http://localhost:8080

# Probar en Telegram:
# Enviar mensaje a @ukoquique_bot
# ✅ Recibe respuesta eco: "Recibí tu mensaje: [texto]"
# ✅ Mensaje guardado en Supabase
```

**Archivos creados**:
- `src/main/java/com/alexia/telegram/AlexiaTelegramBot.java`
- `src/main/java/com/alexia/config/TelegramBotConfig.java`
- `src/main/java/com/alexia/entity/TelegramMessage.java`
- `src/main/java/com/alexia/repository/TelegramMessageRepository.java`
- `src/main/java/com/alexia/service/TelegramService.java`
- `src/main/java/com/alexia/dto/TelegramMessageDTO.java`
- `database/step3_telegram_messages.sql`
- `scripts/delete_webhook.sh`

**Archivos modificados**:
- `pom.xml` (agregada dependencia telegrambots)
- `.env` (token de Telegram actualizado)
- `src/main/resources/application.properties` (propiedades de Telegram)

**Datos del Bot**:
- **Username**: `@ukoquique_bot`
- **Token**: `8479048647:AAE8-jaU0aQ1g7LHAMcourJ4ncq2IhA1anQ`
- **Estado**: ✅ Activo y respondiendo mensajes

**Fecha de completado**: 2025-10-14

---

### ✅ **Tareas Completadas del Paso 3**
- [x] Responder con eco: "Recibí tu mensaje: [texto]"
- [x] Crear tabla `telegram_messages` en Supabase para guardar logs
- [x] Guardar cada mensaje recibido en la base de datos

**Verificación realizada**: ✅
- Bot respondiendo correctamente con mensajes eco
- Tabla `telegram_messages` creada en Supabase
- Entidad `TelegramMessage` con validaciones completas
- Servicio `TelegramService` guardando mensajes correctamente
- Repositorio `TelegramMessageRepository` funcionando

**Estado**: ✅ **PASO 3 COMPLETADO AL 100%**

---

~~### **PASO 3: Bot de Telegram Básico (Eco)**~~
~~**Objetivo**: Crear un bot de Telegram que responda con eco y guarde los mensajes.~~

~~**Tareas**:~~
- [ ] Responder con eco: "Recibí tu mensaje: [texto]"
- [ ] Crear tabla `telegram_messages` en Supabase para guardar logs
- [ ] Guardar cada mensaje recibido en la base de datos

**Verificación**:
```bash
mvn spring-boot:run
# Abrir Telegram
# Buscar el bot: @[tu_bot_username]
# Enviar: "Hola"
# Debe responder: "Recibí tu mensaje: Hola"
# Verificar en Supabase que se guardó el mensaje
```

**Archivos a crear/modificar**:
- `src/main/java/com/alexia/config/TelegramBotConfig.java`
- `src/main/java/com/alexia/telegram/AlexiaTelegramBot.java`
- `src/main/java/com/alexia/entity/TelegramMessage.java`
- `src/main/java/com/alexia/repository/TelegramMessageRepository.java`
- `src/main/java/com/alexia/service/TelegramService.java`
- Modificar `pom.xml`

**SQL en Supabase**:
```sql
CREATE TABLE telegram_messages (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    chat_id BIGINT NOT NULL,
    user_name VARCHAR(255),
    message_text TEXT,
    created_at TIMESTAMP DEFAULT NOW()
);
```

---

### **PASO 4: Dashboard con Logs de Telegram** ✅ COMPLETADO
**Objetivo**: Mostrar en el dashboard los mensajes recibidos de Telegram.

**Tareas**:
- [x] Crear vista `TelegramLogsView.java` en Vaadin
- [x] Crear Grid para mostrar mensajes (fecha, usuario, mensaje)
- [x] Agregar menú lateral con navegación Dashboard/Logs
- [x] Implementar auto-refresh cada 5 segundos
- [x] Agregar filtro por fecha

**Verificación**:
```bash
mvn spring-boot:run
# Abrir http://localhost:8080
# Navegar a "Logs de Telegram"
# Enviar mensajes desde Telegram
# ✅ Verificar que aparecen en la tabla del dashboard
# ✅ Auto-refresh funcionando cada 5 segundos
# ✅ Filtro por fecha operativo
```

**Archivos creados**:
- `src/main/java/com/alexia/views/TelegramLogsView.java`

**Archivos modificados**:
- `src/main/java/com/alexia/views/MainLayout.java` (agregado enlace en menú)
- `src/main/java/com/alexia/views/DashboardView.java` (métricas con datos reales)

**Características implementadas**:
- Grid con 5 columnas: Fecha, Usuario, Mensaje, Respuesta Bot, Chat ID
- Auto-refresh cada 5 segundos con thread daemon
- Filtro por fecha con botones "Hoy" y "Limpiar"
- Contador de mensajes totales
- Botón de actualización manual
- Diseño responsivo y profesional
- Paginación de 20 mensajes por página

**Fecha de completado**: 2025-10-16

---

### **PASO 5: Comandos Básicos del Bot** ✅ COMPLETADO
**Objetivo**: Implementar comandos simples en el bot de Telegram.

**Tareas**:
- [x] Implementar comando `/start` - Mensaje de bienvenida
- [x] Implementar comando `/help` - Lista de comandos disponibles
- [x] Implementar comando `/status` - Estado del bot
- [x] Crear tabla `bot_commands` para registrar uso de comandos
- [ ] Mostrar estadísticas de comandos en el dashboard (opcional)

**Verificación**:
```bash
# En Telegram:
/start → ✅ "¡Bienvenido a Alexia! 🤖..."
/help → ✅ "📋 Comandos disponibles: /start, /help, /status"
/status → ✅ "✅ Bot activo ✓ | Mensajes procesados: X | Comandos ejecutados: Y"
```

**Archivos creados**:
- `src/main/java/com/alexia/entity/BotCommand.java`
- `src/main/java/com/alexia/repository/BotCommandRepository.java`
- `src/main/java/com/alexia/constants/BotCommands.java`
- `database/step5_bot_commands.sql`
- `database/SUPABASE_PASOS.md`

**Archivos modificados**:
- `src/main/java/com/alexia/telegram/AlexiaTelegramBot.java` (manejo de comandos)
- `src/main/java/com/alexia/config/TelegramBotConfig.java` (inyección de dependencias)

**SQL ejecutado en Supabase**:
```sql
CREATE TABLE IF NOT EXISTS bot_commands (
    id BIGSERIAL PRIMARY KEY,
    chat_id BIGINT NOT NULL,
    command VARCHAR(50) NOT NULL,
    user_name VARCHAR(255),
    first_name VARCHAR(255),
    created_at TIMESTAMP DEFAULT NOW()
);

-- Índices para optimización
CREATE INDEX IF NOT EXISTS idx_bot_commands_chat_id ON bot_commands(chat_id);
CREATE INDEX IF NOT EXISTS idx_bot_commands_command ON bot_commands(command);
CREATE INDEX IF NOT EXISTS idx_bot_commands_created_at ON bot_commands(created_at DESC);
```

**Características implementadas**:
- Comando `/start` con mensaje de bienvenida personalizado
- Comando `/help` con lista de comandos disponibles
- Comando `/status` con estadísticas en tiempo real (mensajes y comandos)
- Registro automático de todos los comandos en base de datos
- Patrón switch expression para manejo de comandos
- Logging completo de comandos ejecutados
- Manejo de comandos no reconocidos

**Fecha de completado**: 2025-10-16

---

### **PASO 6: Integración con Grok AI (xAI)** ✅ COMPLETADO
**Objetivo**: Integrar Grok AI para que el bot responda con inteligencia artificial en lugar de eco simple.

**Tareas**:
- [x] Agregar dependencia HTTP client (OkHttp) en `pom.xml`
- [x] Crear `GrokService.java` para comunicación con API de Groq
- [x] Crear DTOs: `GrokRequest.java`, `GrokResponse.java`, `GrokMessage.java`
- [x] Actualizar `AlexiaTelegramBot.java` para usar Grok AI
- [x] Implementar historial de conversación en memoria
- [x] Configurar API key de Groq en `.env` y `application.properties`
- [x] Verificar funcionamiento con pruebas reales

**Verificación**:
```bash
# En Telegram:
/start → ✅ Mensaje de bienvenida
/help → ✅ Lista de comandos
/status → ✅ Estado con estadísticas
"Hola, ¿cómo estás?" → ✅ "Hola, ¿en qué puedo ayudarte?" (Grok AI)
```

**Archivos creados**:
- `src/main/java/com/alexia/dto/GrokMessage.java`
- `src/main/java/com/alexia/dto/GrokRequest.java`
- `src/main/java/com/alexia/dto/GrokResponse.java`
- `src/main/java/com/alexia/service/GrokService.java`
- `GROK_PASOS.md` (documentación completa)

**Archivos modificados**:
- `pom.xml` (dependencias OkHttp y Jackson)
- `src/main/java/com/alexia/telegram/AlexiaTelegramBot.java` (integración Grok)
- `src/main/java/com/alexia/config/TelegramBotConfig.java` (inyección de GrokService)
- `application.properties` (configuración Grok)
- `.env` (API key de Groq)

**Características técnicas**:
- **Modelo**: llama-3.1-8b-instant (rápido y eficiente)
- **Historial**: Hasta 20 mensajes por conversación
- **Fallback**: Respuesta eco si Grok AI falla
- **Tiempo de respuesta**: ~1-3 segundos
- **Idioma**: Español (configurado en system prompt)

**Fecha de completado**: 2025-10-16

---
- [ ] Configurar API key de Grok en `.env`
- [ ] Implementar método `generateResponse(String message)` en GrokService
- [ ] Modificar `AlexiaTelegramBot` para usar Grok en lugar de eco
- [ ] Crear tabla `ai_conversations` para guardar historial
- [ ] Implementar manejo de errores y fallback a eco si falla Grok

**Verificación**:
```bash
# En Telegram:
"¿Qué es la inteligencia artificial?" 
→ Respuesta inteligente de Grok

"Cuéntame un chiste"
→ Chiste generado por Grok

"¿Cuál es la capital de Francia?"
→ "París es la capital de Francia..."
```

**Archivos a crear/modificar**:
- `src/main/java/com/alexia/service/GrokService.java`
- `src/main/java/com/alexia/service/IGrokService.java`
- `src/main/java/com/alexia/dto/AIRequest.java`
- `src/main/java/com/alexia/dto/AIResponse.java`
- `src/main/java/com/alexia/entity/AIConversation.java`
- `src/main/java/com/alexia/repository/AIConversationRepository.java`
- Modificar `AlexiaTelegramBot.java`
- Modificar `pom.xml` (agregar OkHttp)
- Modificar `.env` (GROK_API_KEY)

**SQL en Supabase**:
```sql
CREATE TABLE ai_conversations (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    chat_id BIGINT NOT NULL,
    user_message TEXT NOT NULL,
    ai_response TEXT NOT NULL,
    ai_provider VARCHAR(50) DEFAULT 'grok',
    tokens_used INTEGER,
    response_time_ms INTEGER,
    created_at TIMESTAMP DEFAULT NOW()
);
```

**Configuración en .env**:
```bash
# Grok AI Configuration
GROK_API_KEY=tu_api_key_de_grok
GROK_API_URL=https://api.x.ai/v1/chat/completions
GROK_MODEL=grok-beta
```

---

### **PASO 7: Dashboard de Conversaciones con IA**
**Objetivo**: Mostrar en el dashboard las conversaciones con IA y métricas de uso.

**Tareas**:
- [ ] Crear vista `AIConversationsView.java` en Vaadin
- [ ] Crear Grid para mostrar conversaciones (fecha, usuario, mensaje, respuesta)
- [ ] Agregar métricas: total conversaciones, tokens usados, tiempo promedio
- [ ] Implementar filtro por proveedor de IA (Grok/OpenAI)
- [ ] Agregar gráfico de conversaciones por día
- [ ] Mostrar estado de la API (activa/inactiva)

**Verificación**:
```bash
mvn spring-boot:run
# Abrir http://localhost:8080
# Navegar a "Conversaciones IA"
# Enviar mensajes desde Telegram
# Verificar que aparecen las conversaciones con respuestas de Grok
# Ver métricas actualizadas
```

**Archivos a crear/modificar**:
- `src/main/java/com/alexia/views/AIConversationsView.java`
- Modificar `MainLayout.java` (agregar menú)
- Modificar `DashboardView.java` (agregar métricas de IA)

---

### **PASO 8: Integración con OpenAI (Opcional/Futuro)**
**Objetivo**: Agregar OpenAI como proveedor alternativo de IA con capacidad de cambiar entre Grok y OpenAI.

**Tareas**:
- [ ] Crear `OpenAIService.java` similar a GrokService
- [ ] Crear interfaz común `IAIProvider.java`
- [ ] Implementar patrón Strategy para cambiar entre proveedores
- [ ] Agregar comando `/ai grok` y `/ai openai` para cambiar proveedor
- [ ] Configurar API key de OpenAI en `.env`
- [ ] Implementar comparación de respuestas (modo debug)
- [ ] Agregar selector de proveedor en el dashboard

**Verificación**:
```bash
# En Telegram:
/ai grok → "Usando Grok AI ✓"
"Hola" → Respuesta de Grok

/ai openai → "Usando OpenAI ✓"
"Hola" → Respuesta de OpenAI

/ai status → "Proveedor actual: Grok | Disponibles: Grok, OpenAI"
```

**Archivos a crear/modificar**:
- `src/main/java/com/alexia/service/OpenAIService.java`
- `src/main/java/com/alexia/service/IAIProvider.java`
- `src/main/java/com/alexia/service/AIProviderFactory.java`
- Modificar `AlexiaTelegramBot.java`
- Modificar `.env` (OPENAI_API_KEY)
- Modificar `AIConversation.java` (agregar campo provider)

**Configuración en .env**:
```bash
# OpenAI Configuration (opcional)
OPENAI_API_KEY=tu_api_key_de_openai
OPENAI_API_URL=https://api.openai.com/v1/chat/completions
OPENAI_MODEL=gpt-4
AI_DEFAULT_PROVIDER=grok
```

---

### **PASO 9: Búsqueda Simple en Base de Datos**
**Objetivo**: Crear tabla de negocios y permitir búsqueda básica por categoría.

**Tareas**:
- [ ] Crear tabla `businesses` en Supabase (versión simplificada)
- [ ] Crear entidad `Business.java`
- [ ] Crear repositorio `BusinessRepository.java`
- [ ] Agregar algunos negocios de prueba manualmente en Supabase
- [ ] Implementar búsqueda: "buscar [categoría]" en Telegram
- [ ] Responder con lista de negocios encontrados

**Verificación**:
```bash
# En Telegram:
"buscar panadería" → "Encontré 2 panaderías:
1. Panadería El Sol - Calle 123
2. Pan Caliente - Av. Principal"
```

**Archivos a crear/modificar**:
- `src/main/java/com/alexia/entity/Business.java`
- `src/main/java/com/alexia/repository/BusinessRepository.java`
- `src/main/java/com/alexia/service/BusinessService.java`
- Modificar `AlexiaTelegramBot.java`

**SQL en Supabase**:
```sql
CREATE TABLE businesses (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(255) NOT NULL,
    category VARCHAR(100),
    address VARCHAR(500),
    phone VARCHAR(50),
    is_active BOOLEAN DEFAULT true,
    created_at TIMESTAMP DEFAULT NOW()
);

-- Insertar datos de prueba
INSERT INTO businesses (name, category, address, phone) VALUES
('Panadería El Sol', 'panadería', 'Calle 123, Costa Azul', '555-0001'),
('Pan Caliente', 'panadería', 'Av. Principal 456', '555-0002'),
('Restaurante La Costa', 'restaurante', 'Playa Norte 789', '555-0003');
```

---

### **PASO 7: CRUD de Negocios en Dashboard**
**Objetivo**: Permitir crear, editar y eliminar negocios desde el panel web.

**Tareas**:
- [ ] Crear vista `BusinessView.java` con Grid de negocios
- [ ] Crear formulario para agregar/editar negocios
- [ ] Implementar botones: Nuevo, Editar, Eliminar
- [ ] Agregar validaciones básicas
- [ ] Actualizar menú lateral con opción "Negocios"

**Verificación**:
```bash
# En el dashboard:
# Ir a "Negocios"
# Hacer clic en "Nuevo"
# Llenar formulario y guardar
# Verificar que aparece en la lista
# Desde Telegram buscar el negocio creado
```

**Archivos a crear/modificar**:
- `src/main/java/com/alexia/views/BusinessView.java`
- `src/main/java/com/alexia/views/BusinessForm.java`
- Modificar `MainLayout.java`

---

### **PASO 8: Integración con IA (Grok)**
**Objetivo**: Usar Grok para analizar la intención del usuario.

**Tareas**:
- [ ] Agregar dependencia HTTP client al `pom.xml`
- [ ] Crear clase `GrokService.java`
- [ ] Implementar llamada a API de Grok
- [ ] Extraer categoría del mensaje del usuario usando IA
- [ ] Usar la categoría extraída para buscar negocios
- [ ] Registrar consultas IA en tabla `ai_queries`

**Verificación**:
```bash
# En Telegram:
"Necesito comprar pan" → Grok extrae "panadería" → Muestra panaderías
"Dónde puedo comer?" → Grok extrae "restaurante" → Muestra restaurantes
```

**Archivos a crear/modificar**:
- `src/main/java/com/alexia/service/GrokService.java`
- `src/main/java/com/alexia/entity/AIQuery.java`
- `src/main/java/com/alexia/repository/AIQueryRepository.java`
- Modificar `AlexiaTelegramBot.java`

**SQL en Supabase**:
```sql
CREATE TABLE ai_queries (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_message TEXT,
    extracted_category VARCHAR(100),
    ai_response TEXT,
    created_at TIMESTAMP DEFAULT NOW()
);
```

---

### **PASO 9: Búsqueda por Ubicación**
**Objetivo**: Permitir que el usuario comparta su ubicación y buscar negocios cercanos.

**Tareas**:
- [ ] Agregar columnas de ubicación a tabla `businesses`
- [ ] Modificar entidad `Business.java` con latitud/longitud
- [ ] Capturar ubicación compartida en Telegram
- [ ] Implementar búsqueda por distancia (PostGIS)
- [ ] Responder con negocios ordenados por cercanía

**Verificación**:
```bash
# En Telegram:
# Compartir ubicación
# Escribir: "panaderías cerca"
# Debe responder con negocios ordenados por distancia
```

**Archivos a crear/modificar**:
- Modificar `Business.java`
- Modificar `BusinessRepository.java`
- Modificar `AlexiaTelegramBot.java`
- Modificar `BusinessService.java`

**SQL en Supabase**:
```sql
ALTER TABLE businesses 
ADD COLUMN latitude DECIMAL(10, 8),
ADD COLUMN longitude DECIMAL(11, 8);

-- Actualizar datos de prueba con coordenadas
UPDATE businesses SET latitude = 10.3910, longitude = -75.4794 WHERE name = 'Panadería El Sol';
```

---

### **PASO 10: Dashboard con Métricas**
**Objetivo**: Mostrar estadísticas básicas en el dashboard.

**Tareas**:
- [ ] Crear cards con métricas:
  - Total de mensajes recibidos
  - Total de búsquedas realizadas
  - Total de negocios registrados
  - Comandos más usados
- [ ] Agregar gráfico simple de mensajes por día
- [ ] Implementar filtro por rango de fechas

**Verificación**:
```bash
# Abrir dashboard
# Verificar que muestra:
# - "Mensajes: 45"
# - "Búsquedas: 23"
# - "Negocios: 8"
# - Gráfico de barras con actividad
```

**Archivos a crear/modificar**:
- Modificar `DashboardView.java`
- `src/main/java/com/alexia/service/MetricsService.java`

---

## 🔑 Credenciales Disponibles (del primer intento)

```env
# Supabase
SUPABASE_URL=https://your-project.supabase.co
SUPABASE_KEY=your-anon-key
SUPABASE_DB_URL=jdbc:postgresql://db.your-project.supabase.co:5432/postgres
SUPABASE_DB_USER=postgres
SUPABASE_DB_PASSWORD=your-password

# Grok
GROK_API_KEY=your-grok-api-key

# Telegram
TELEGRAM_BOT_TOKEN=your-telegram-bot-token
```

---

## 📝 Reglas de Desarrollo

### ✅ HACER:
1. **Un paso a la vez**: Completar y probar antes de continuar
2. **Código mínimo**: Solo lo necesario para la funcionalidad actual
3. **Probar siempre**: Ejecutar `mvn spring-boot:run` después de cada paso
4. **Commits frecuentes**: Guardar progreso después de cada paso exitoso
5. **Logs claros**: Agregar logs para debugging

### ❌ NO HACER:
1. **No adelantarse**: No agregar código para pasos futuros
2. **No sobrecargar**: No agregar dependencias innecesarias
3. **No asumir**: Verificar que todo funciona antes de continuar
4. **No código muerto**: Eliminar código que no se usa
5. **No complejidad prematura**: Mantener simple hasta que sea necesario

---

## 🎯 Criterios de Éxito por Paso

Cada paso se considera **COMPLETADO** solo si:
- ✅ El código compila sin errores
- ✅ La aplicación inicia correctamente
- ✅ La funcionalidad específica funciona como se espera
- ✅ Se puede demostrar visualmente (dashboard o Telegram)
- ✅ Los datos se guardan correctamente en Supabase

---

## 📊 Progreso Actual

| Paso | Estado | Fecha | Descripción |
|------|--------|-------|-------------|
| 1. Proyecto Base y Dashboard | ✅ Completado | 2025-10-14 | Maven, Spring Boot, Vaadin básico |
| 2. Conexión a Supabase | ✅ Completado | 2025-10-14 | Conexión verificada, dotenv configurado |
| UI. Dashboard Profesional | ✅ Completado | 2025-10-14 | 13 vistas, menú lateral, métricas |
| 3. Integración con Telegram | ✅ Completado | 2025-10-14 | Bot funcional con respuestas eco |
| 4. Dashboard con Logs | ✅ Completado | 2025-10-16 | Visualización de mensajes con auto-refresh |
| 5. Comandos Básicos | ✅ Completado | 2025-10-16 | Comandos básicos del bot (/start, /help, /status) |
| 6. Integración con Grok AI | ✅ Completado | 2025-10-16 | Integración con Grok AI |
| 7. Dashboard de Conversaciones IA | ⏳ Pendiente | - | Métricas y visualización de IA |
| 8. Integración con OpenAI | ⏳ Pendiente | - | Proveedor alternativo de IA |
| 9. Búsqueda Simple | ⏳ Pendiente | - | Búsqueda por categoría |
| 10. Dashboard con Métricas | ⏳ Pendiente | - | Gráficos y estadísticas |

{{ ... }}

---

## 🚀 Pasos Futuros (Después del Paso 10)

Una vez completados los 10 pasos básicos, se pueden agregar:
- Integración con Google Places (fallback)
- Sistema de campañas y tracking
- Facturación automática
- Integración con WhatsApp
- Autenticación y roles en dashboard
- Sistema de productos
- Notificaciones push
- Reportes avanzados

---

**Versión**: 1.0  
**Fecha de creación**: 2025-10-14  
**Última actualización**: 2025-10-16  
**Progreso**: 6 pasos de 10 pasos = **60% completado** - Comandos Básicos del Bot funcionando  
**Próximo paso**: Paso 7 - Dashboard de Conversaciones IA
