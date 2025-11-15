# Публикация образа в Docker Hub

## Шаг 1: Регистрация/Вход в Docker Hub

1. Зарегистрируйтесь на [Docker Hub](https://hub.docker.com/) (если еще не зарегистрированы)
2. Создайте репозиторий на Docker Hub:
   - Перейдите на https://hub.docker.com/repositories
   - Нажмите "Create Repository"
   - Имя репозитория: `assignment3-backend` (или любое другое)
   - Видимость: Public или Private (на ваш выбор)

## Шаг 2: Вход в Docker Hub через командную строку

```bash
docker login
```

Введите ваш Docker Hub username и password.

## Шаг 3: Пометить образ правильным тегом

Образ должен быть помечен как `username/repository-name:tag`

```bash
# Замените YOUR_DOCKERHUB_USERNAME на ваш username
docker tag assignment3-backend:latest YOUR_DOCKERHUB_USERNAME/assignment3-backend:latest

# Или с версией
docker tag assignment3-backend:latest YOUR_DOCKERHUB_USERNAME/assignment3-backend:1.0.0
```

## Шаг 4: Загрузить образ в Docker Hub

```bash
# Загрузить latest версию
docker push YOUR_DOCKERHUB_USERNAME/assignment3-backend:latest

# Или с версией
docker push YOUR_DOCKERHUB_USERNAME/assignment3-backend:1.0.0
```

## Пример полного процесса

```bash
# 1. Войти в Docker Hub
docker login

# 2. Пометить образ (замените nurdauletsagnadin на ваш username)
docker tag assignment3-backend:latest nurdauletsagnadin/assignment3-backend:latest

# 3. Загрузить образ
docker push nurdauletsagnadin/assignment3-backend:latest
```

## После загрузки

Ваш образ будет доступен по адресу:
- `https://hub.docker.com/r/YOUR_DOCKERHUB_USERNAME/assignment3-backend`

Использование образа:
```bash
docker pull YOUR_DOCKERHUB_USERNAME/assignment3-backend:latest
docker run -p 8080:8080 YOUR_DOCKERHUB_USERNAME/assignment3-backend:latest
```

## Обновление образа

При обновлении приложения:

```bash
# 1. Пересобрать образ
docker build -t assignment3-backend:latest .

# 2. Пометить новую версию
docker tag assignment3-backend:latest YOUR_DOCKERHUB_USERNAME/assignment3-backend:latest

# 3. Загрузить обновление
docker push YOUR_DOCKERHUB_USERNAME/assignment3-backend:latest
```

## Автоматизация через скрипт

См. файл `publish-to-dockerhub.sh` для автоматизации процесса.

