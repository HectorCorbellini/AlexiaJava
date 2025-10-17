# Guía de Despliegue en Render

## 📋 Requisitos Previos

- [x] Cuenta en [Render](https://render.com)
- [x] Aplicación Spring Boot funcional localmente
- [x] Repositorio en GitHub/GitLab
- [x] Base de datos configurada (Supabase o PostgreSQL en Render)

## ✅ Preparación Completada

- [x] **application-prod.properties** creado con configuración de Render
- [x] **pom.xml** actualizado con mainClass
- [x] **render.yaml** creado con configuración de despliegue
- [x] **.renderignore** creado para excluir archivos innecesarios
- [x] **RENDER_ENV_VARS.md** creado con documentación de variables de entorno
- [x] **Compilación verificada** - JAR generado exitosamente

## 🚀 Pasos para el Despliegue

### 1. Preparar la Aplicación

#### 1.1 Actualizar `application.properties`

Crea o modifica `src/main/resources/application-prod.properties`:

```properties
# Configuración de la base de datos
spring.datasource.url=${DATABASE_URL}
spring.datasource.username=${DB_USER}
spring.datasource.password=${DB_PASSWORD}

# Configuración del puerto para Render
server.port=${PORT:8080}

# Configuración de Telegram
bot.token=${TELEGRAM_BOT_TOKEN}

# Configuración de JPA/Hibernate
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

# Logging
logging.level.org.springframework=INFO
logging.level.com.alexia=DEBUG
```

#### 1.2 Actualizar `pom.xml`

Asegúrate de que el `pom.xml` incluya el plugin de Spring Boot Maven:

```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
            <configuration>
                <mainClass>com.alexia.AlexiaApplication</mainClass>
            </configuration>
        </plugin>
    </plugins>
</build>
```

### 2. Crear `render.yaml`

Crea un archivo `render.yaml` en la raíz del proyecto:

```yaml
# render.yaml
services:
  - type: web
    name: alexia
    env: java
    region: oregon  # o la región más cercana a ti
    plan: free  # o 'starter' para producción
    buildCommand: mvn clean package -DskipTests
    startCommand: java -jar target/*.jar
    envVars:
      - key: SPRING_PROFILES_ACTIVE
        value: prod
      - key: PORT
        value: 8080
      # Agrega otras variables de entorno aquí
    healthCheckPath: /actuator/health
    autoDeploy: true
```

### 3. Configurar Webhook de Telegram (Opcional)

Si usas webhooks, configura el endpoint de Telegram después de desplegar:

```bash
curl -X GET "https://api.telegram.org/bot<TU_TOKEN>/setWebhook?url=<URL_RENDER>/webhook"
```

### 4. Desplegar en Render

1. **Inicia sesión** en [Render Dashboard](https://dashboard.render.com/)
2. Haz clic en **New +** → **Web Service**
3. Conecta tu repositorio de GitHub/GitLab
4. Configura el servicio:
   - **Name**: alexia (o el nombre que prefieras)
   - **Region**: Elige la más cercana a tus usuarios
   - **Branch**: main (o la rama que uses)
   - **Runtime**: Java
   - **Build Command**: `mvn clean package -DskipTests`
   - **Start Command**: `java -jar target/*.jar`
   - **Instance Type**: Free (para empezar)

5. **Variables de Entorno**:
   ```
   SPRING_PROFILES_ACTIVE=prod
   DATABASE_URL=postgresql://user:password@host:5432/dbname
   DB_USER=usuario
   DB_PASSWORD=contraseña
   TELEGRAM_BOT_TOKEN=tu_token
   ```

6. Haz clic en **Create Web Service**

### 5. Configurar Base de Datos (si no usas Supabase)

1. En Render Dashboard, haz clic en **New +** → **PostgreSQL**
2. Configura la base de datos:
   - **Name**: alexia-db
   - **Database**: alexia
   - **User**: usuario (lo generará Render)
   - **Region**: Misma que tu web service

3. Usa las credenciales generadas en las variables de entorno del web service.

## 🔍 Solución de Problemas Comunes

### La aplicación no inicia
- Verifica los logs en el dashboard de Render
- Asegúrate de que el puerto esté configurado correctamente
- Revisa las variables de entorno

### Problemas de Conexión a la Base de Datos
- Verifica que la URL de conexión sea correcta
- Asegúrate de que la base de datos acepte conexiones desde la IP de Render

### La aplicación se desconecta después de inactividad
- El plan gratuito de Render duerme las aplicaciones después de 15 minutos de inactividad
- Considera usar un servicio de monitoreo gratuito como UptimeRobot para mantenerla activa

## 📈 Escalando tu Aplicación

1. **Actualiza el Plan**: Cambia de Free a Starter o Professional según necesites
2. **Escalado Horizontal**: Añade más instancias si el tráfico aumenta
3. **Base de Datos**: Considera actualizar el plan de la base de datos

## 🔒 Seguridad

- Nunca subas archivos `.env` o `application.properties` con credenciales
- Usa variables de entorno para datos sensibles
- Configura HTTPS en Render (viene por defecto)

## 📚 Recursos Adicionales

- [Documentación de Render para Java](https://render.com/docs/deploy-java-spring-boot)
- [Configuración de PostgreSQL en Render](https://render.com/docs/deploy-postgres)
- [Variables de Entorno en Render](https://render.com/docs/environment-variables)

---

## 🚀 Despliegue Rápido (Resumen)

### Paso 1: Subir cambios a GitHub
```bash
git add .
git commit -m "feat: Preparar aplicación para despliegue en Render"
git push origin main
```

### Paso 2: Crear servicio en Render
1. Ve a https://dashboard.render.com/
2. Click en **New +** → **Web Service**
3. Conecta tu repositorio
4. Render detectará automáticamente `render.yaml`
5. Click en **Apply** para usar la configuración

### Paso 3: Configurar variables de entorno
En el dashboard de Render, agrega estas variables (ver `RENDER_ENV_VARS.md` para detalles):
- `DATABASE_URL`
- `DB_USER`
- `DB_PASSWORD`
- `TELEGRAM_BOT_TOKEN`
- `SPRING_PROFILES_ACTIVE=prod`

### Paso 4: Desplegar
Click en **Create Web Service** y espera a que se complete el despliegue (5-10 minutos).

### Paso 5: Verificar
1. Abre la URL proporcionada por Render
2. Verifica que el dashboard cargue correctamente
3. Inicia el bot de Telegram desde la interfaz
4. Envía un mensaje de prueba al bot

---

## 📦 Archivos Creados

Los siguientes archivos fueron creados para el despliegue:

| Archivo | Descripción |
|---------|-------------|
| `render.yaml` | Configuración de despliegue de Render |
| `.renderignore` | Archivos a excluir del despliegue |
| `application-prod.properties` | Configuración de producción |
| `RENDER_ENV_VARS.md` | Documentación de variables de entorno |

---

**Nota**: Esta guía asume que ya tienes configurada tu aplicación Spring Boot localmente. Si necesitas ayuda con algún paso específico, no dudes en preguntar.
