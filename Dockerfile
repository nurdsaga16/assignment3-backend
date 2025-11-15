# Multi-stage build для Spring Boot приложения
# Указываем платформу для совместимости
# syntax=docker/dockerfile:1

# Stage 1: Build stage
FROM --platform=linux/amd64 gradle:8.5-jdk21 AS build

WORKDIR /app

# Копируем файлы Gradle для кэширования зависимостей
COPY build.gradle.kts settings.gradle.kts ./
COPY gradle ./gradle

# Копируем исходный код
COPY src ./src

# Собираем приложение (без тестов для ускорения)
RUN gradle clean build -x test --no-daemon

# Stage 2: Runtime stage
FROM --platform=linux/amd64 eclipse-temurin:21-jre-alpine

# Устанавливаем необходимые пакеты
RUN apk add --no-cache \
    tzdata \
    && rm -rf /var/cache/apk/*

# Устанавливаем часовой пояс
ENV TZ=Asia/Almaty
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

# Создаем пользователя для запуска приложения (безопасность)
RUN addgroup -S spring && adduser -S spring -G spring

WORKDIR /app

# Копируем JAR файл из build stage
COPY --from=build /app/build/libs/*.jar app.jar

# Создаем директории для изображений
RUN mkdir -p /app/images /app/avatars && \
    chown -R spring:spring /app

# Переключаемся на пользователя spring
USER spring:spring

# Открываем порт
EXPOSE 8080

# Переменные окружения по умолчанию
ENV JAVA_OPTS="-Xmx512m -Xms256m" \
    SPRING_PROFILES_ACTIVE=prod

# Health check - используем переменную PORT или 10000 (Render.com использует свой health check)
# HEALTHCHECK отключен, так как Render.com использует свой механизм проверки
# HEALTHCHECK --interval=30s --timeout=3s --start-period=40s --retries=3 \
#     CMD wget --no-verbose --tries=1 --spider http://localhost:${PORT:-10000}/api/v1/projects || exit 1

# Запускаем приложение
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]

