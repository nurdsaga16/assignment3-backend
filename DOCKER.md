# Docker инструкции для Spring Boot приложения

## Быстрый старт

### 1. Сборка Docker образа

```bash
# Сборка образа
docker build -t azharfund-backend:latest .

# Или с указанием тега
docker build -t azharfund-backend:1.0.0 .
```

### 2. Запуск с docker-compose (рекомендуется)

```bash
# Создайте файл .env для переменных окружения (опционально)
echo "JWT_SECRET_KEY=your_very_secure_secret_key_here" > .env

# Запуск всех сервисов (PostgreSQL + Backend)
docker-compose up -d

# Просмотр логов
docker-compose logs -f backend

# Остановка
docker-compose down

# Остановка с удалением volumes (удалит данные БД)
docker-compose down -v
```

### 3. Запуск только приложения (без docker-compose)

Если у вас уже есть PostgreSQL:

```bash
docker run -d \
  --name azharfund-backend \
  -p 8080:8080 \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://host.docker.internal:5432/assignment3 \
  -e SPRING_DATASOURCE_USERNAME=your_username \
  -e SPRING_DATASOURCE_PASSWORD=your_password \
  -e JWT_SECRET_KEY=your_secret_key \
  -v $(pwd)/images:/app/images \
  -v $(pwd)/avatars:/app/avatars \
  azharfund-backend:latest
```

## Переменные окружения

### Обязательные переменные:

- `SPRING_DATASOURCE_URL` - URL базы данных PostgreSQL
- `SPRING_DATASOURCE_USERNAME` - Имя пользователя БД
- `SPRING_DATASOURCE_PASSWORD` - Пароль БД
- `JWT_SECRET_KEY` - Секретный ключ для JWT (рекомендуется использовать сильный ключ)

### Опциональные переменные:

- `SPRING_PROFILES_ACTIVE` - Профиль Spring (по умолчанию: `prod`)
- `SERVER_PORT` - Порт приложения (по умолчанию: `8080`)
- `JAVA_OPTS` - Опции JVM (по умолчанию: `-Xmx512m -Xms256m`)
- `SPRING_JPA_HIBERNATE_DDL_AUTO` - Режим обновления схемы БД (по умолчанию: `update`)
- `APP_IMAGES_BASE_DIR` - Директория для хранения изображений (по умолчанию: `/app/images`)

## Volumes

Приложение использует volumes для хранения файлов:

- `./images` → `/app/images` - Изображения проектов
- `./avatars` → `/app/avatars` - Аватары пользователей

**Важно:** Убедитесь, что директории `images` и `avatars` существуют и имеют правильные права доступа.

## Health Check

Dockerfile включает health check, который проверяет доступность приложения:

```bash
# Проверка статуса
docker ps

# Ручная проверка health check
docker inspect --format='{{.State.Health.Status}}' azharfund-backend
```

## Полезные команды

### Просмотр логов

```bash
# Все логи
docker-compose logs -f

# Только backend
docker-compose logs -f backend

# Последние 100 строк
docker-compose logs --tail=100 backend
```

### Вход в контейнер

```bash
# В контейнер backend
docker exec -it azharfund-backend sh

# В контейнер PostgreSQL
docker exec -it azharfund-postgres psql -U azharfund_user -d assignment3
```

### Пересборка образа

```bash
# С пересборкой без кэша
docker-compose build --no-cache

# С перезапуском
docker-compose up -d --build
```

### Очистка

```bash
# Удаление остановленных контейнеров
docker container prune

# Удаление неиспользуемых образов
docker image prune

# Удаление всего (осторожно!)
docker system prune -a
```

## Production настройки

### 1. Используйте .env файл для секретов

Создайте `.env` файл (не коммитьте в Git!):

```env
JWT_SECRET_KEY=your_very_secure_secret_key_min_32_chars
SPRING_DATASOURCE_PASSWORD=secure_db_password
```

### 2. Настройте docker-compose для production

Обновите `docker-compose.yml`:

```yaml
services:
  backend:
    environment:
      SPRING_PROFILES_ACTIVE: prod
      JAVA_OPTS: "-Xmx1024m -Xms512m"
    restart: always
    logging:
      driver: "json-file"
      options:
        max-size: "10m"
        max-file: "3"
```

### 3. Используйте внешнюю БД

Для production рекомендуется использовать управляемую БД (AWS RDS, Google Cloud SQL и т.д.):

```yaml
services:
  backend:
    environment:
      SPRING_DATASOURCE_URL: jdbc:postgresql://your-db-host:5432/assignment3
      # Уберите depends_on: postgres
```

### 4. Настройте reverse proxy (Nginx)

```nginx
upstream backend {
    server localhost:8080;
}

server {
    listen 80;
    server_name your-domain.com;

    location /api/ {
        proxy_pass http://backend;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
    }
}
```

## Troubleshooting

### Приложение не запускается

1. Проверьте логи:
   ```bash
   docker-compose logs backend
   ```

2. Проверьте подключение к БД:
   ```bash
   docker-compose exec backend ping postgres
   ```

3. Проверьте переменные окружения:
   ```bash
   docker-compose exec backend env | grep SPRING
   ```

### Ошибки с правами доступа к файлам

```bash
# Установите правильные права
chmod -R 755 images avatars
chown -R $(id -u):$(id -g) images avatars
```

### Проблемы с памятью

Увеличьте лимиты памяти в `JAVA_OPTS`:

```yaml
environment:
  JAVA_OPTS: "-Xmx1024m -Xms512m"
```

## Безопасность

1. **Никогда не коммитьте секреты** в Git
2. Используйте Docker secrets или внешние системы управления секретами
3. Регулярно обновляйте базовые образы
4. Используйте минимальные образы (alpine)
5. Запускайте приложение от непривилегированного пользователя

## Примеры использования

### Разработка

```bash
# Запуск с hot reload (если настроен)
docker-compose -f docker-compose.yml -f docker-compose.dev.yml up
```

### Production

```bash
# Сборка production образа
docker build -t azharfund-backend:prod .

# Запуск
docker-compose -f docker-compose.prod.yml up -d
```

