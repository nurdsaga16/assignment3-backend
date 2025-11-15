# Environment Variables для Render.com

## Обязательные переменные окружения

Добавьте следующие переменные окружения в настройках вашего сервиса на Render.com:

### 1. База данных PostgreSQL

Если вы используете PostgreSQL от Render.com, Render автоматически создаст переменную `DATABASE_URL`. Вам нужно будет преобразовать её в нужный формат:

**Вариант A: Использовать DATABASE_URL от Render (рекомендуется)**

Render автоматически предоставляет `DATABASE_URL` в формате:
```
postgresql://user:password@host:port/database
```

Добавьте эти переменные:

```
SPRING_DATASOURCE_URL=${DATABASE_URL}
```

Или если нужно преобразовать формат:

```
SPRING_DATASOURCE_URL=jdbc:postgresql://[host]:[port]/[database]
SPRING_DATASOURCE_USERNAME=[username]
SPRING_DATASOURCE_PASSWORD=[password]
```

**Вариант B: Использовать внешнюю БД (как в вашем application.properties)**

```
SPRING_DATASOURCE_URL=jdbc:postgresql://dpg-d4c57r7diees738tt7ag-a.oregon-postgres.render.com:5432/assignment3_eejy
SPRING_DATASOURCE_USERNAME=assignment3_eejy_user
SPRING_DATASOURCE_PASSWORD=jIvAFeeO2FSAI9oa14WoAS4bah6LS7Bt
SPRING_DATASOURCE_DRIVER_CLASS_NAME=org.postgresql.Driver
```

### 2. JWT Секретный ключ (ОБЯЗАТЕЛЬНО!)

```
JWT_SECRET_KEY=your_very_secure_secret_key_minimum_32_characters_long
```

**⚠️ ВАЖНО:** 
- Используйте длинный случайный ключ (минимум 32 символа)
- НЕ используйте дефолтный ключ из application.properties
- Генерируйте новый ключ для production:
  ```bash
  openssl rand -base64 32
  ```

### 3. JPA настройки

```
SPRING_JPA_HIBERNATE_DDL_AUTO=update
SPRING_JPA_SHOW_SQL=false
SPRING_JPA_PROPERTIES_HIBERNATE_FORMAT_SQL=false
SPRING_JPA_PROPERTIES_HIBERNATE_DIALECT=org.hibernate.dialect.PostgreSQLDialect
```

### 4. Приложение

```
APP_IMAGES_BASE_DIR=/app/images
API_VERSION=/api/v1
SERVER_PORT=10000
```

**⚠️ ВАЖНО:** Render.com использует порт из переменной `PORT` или `10000` по умолчанию. Убедитесь, что приложение слушает правильный порт.

### 5. Java опции (опционально)

```
JAVA_OPTS=-Xmx512m -Xms256m
SPRING_PROFILES_ACTIVE=prod
```

### 6. CORS настройки (если нужно)

Если ваш фронтенд на другом домене, обновите CORS в коде или добавьте:

```
CORS_ALLOWED_ORIGINS=https://your-frontend-domain.com,https://www.your-frontend-domain.com
```

## Полный список переменных для копирования

Скопируйте и вставьте в Environment Variables на Render.com:

```
# Database
SPRING_DATASOURCE_URL=jdbc:postgresql://dpg-d4c57r7diees738tt7ag-a.oregon-postgres.render.com:5432/assignment3_eejy
SPRING_DATASOURCE_USERNAME=assignment3_eejy_user
SPRING_DATASOURCE_PASSWORD=jIvAFeeO2FSAI9oa14WoAS4bah6LS7Bt
SPRING_DATASOURCE_DRIVER_CLASS_NAME=org.postgresql.Driver

# JPA
SPRING_JPA_HIBERNATE_DDL_AUTO=update
SPRING_JPA_SHOW_SQL=false
SPRING_JPA_PROPERTIES_HIBERNATE_FORMAT_SQL=false
SPRING_JPA_PROPERTIES_HIBERNATE_DIALECT=org.hibernate.dialect.PostgreSQLDialect

# JWT (ОБЯЗАТЕЛЬНО ИЗМЕНИТЬ!)
JWT_SECRET_KEY=your_very_secure_secret_key_here_minimum_32_characters

# Application
APP_IMAGES_BASE_DIR=/app/images
API_VERSION=/api/v1
SERVER_PORT=10000

# Java
JAVA_OPTS=-Xmx512m -Xms256m
SPRING_PROFILES_ACTIVE=prod
```

## Как добавить переменные на Render.com

1. Перейдите в ваш сервис на Render.com
2. Откройте вкладку **"Environment"**
3. Нажмите **"Add Environment Variable"**
4. Добавьте каждую переменную по отдельности:
   - **Key**: название переменной (например, `JWT_SECRET_KEY`)
   - **Value**: значение переменной
5. Нажмите **"Save Changes"**
6. Render автоматически перезапустит сервис

## Важные замечания

### Порт приложения

Render.com может использовать переменную `PORT` для указания порта. Убедитесь, что ваше приложение может читать порт из переменной окружения:

В `application.properties` можно добавить:
```properties
server.port=${PORT:10000}
```

Или в переменных окружения Render:
```
PORT=10000
SERVER_PORT=10000
```

### Хранение файлов

На Render.com файлы в `/app/images` и `/app/avatars` будут храниться в контейнере, но **могут быть потеряны при перезапуске**. 

Для production рекомендуется:
1. Использовать внешнее хранилище (AWS S3, Google Cloud Storage)
2. Или использовать Persistent Disk на Render (если доступен)

### Безопасность

- ✅ Используйте **Secret** тип для чувствительных данных (пароли, ключи)
- ✅ НЕ коммитьте секреты в Git
- ✅ Регулярно обновляйте JWT_SECRET_KEY
- ✅ Используйте разные ключи для разных окружений

## Проверка переменных

После деплоя проверьте логи:

```bash
# В Render Dashboard -> Logs
# Ищите строки с "SPRING_DATASOURCE_URL" и другими переменными
```

Или добавьте endpoint для проверки (только для dev):

```java
@GetMapping("/env-check")
public Map<String, String> checkEnv() {
    Map<String, String> env = new HashMap<>();
    env.put("db_url", System.getenv("SPRING_DATASOURCE_URL"));
    env.put("jwt_key_set", System.getenv("JWT_SECRET_KEY") != null ? "***" : "NOT SET");
    return env;
}
```

## Troubleshooting

### Приложение не подключается к БД

1. Проверьте `SPRING_DATASOURCE_URL` - должен быть полный JDBC URL
2. Проверьте username и password
3. Убедитесь, что БД доступна из Render (проверьте firewall/security groups)

### Ошибки с портом

1. Убедитесь, что `SERVER_PORT` или `PORT` установлен
2. Проверьте, что приложение слушает `0.0.0.0`, а не `localhost`

### JWT ошибки

1. Убедитесь, что `JWT_SECRET_KEY` установлен
2. Проверьте, что ключ достаточно длинный (минимум 32 символа)
3. Убедитесь, что используется тот же ключ, что и при генерации токенов

