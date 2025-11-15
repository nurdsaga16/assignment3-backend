#!/bin/bash

# Скрипт для публикации образа в Docker Hub
# Использование: ./publish-to-dockerhub.sh [version]
# Пример: ./publish-to-dockerhub.sh 1.0.0

set -e

# Цвета для вывода
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Проверка аргументов
VERSION=${1:-latest}
DOCKERHUB_USERNAME=${DOCKERHUB_USERNAME:-""}

if [ -z "$DOCKERHUB_USERNAME" ]; then
    echo -e "${YELLOW}Переменная окружения DOCKERHUB_USERNAME не установлена${NC}"
    read -p "Введите ваш Docker Hub username: " DOCKERHUB_USERNAME
fi

IMAGE_NAME="assignment3-backend"
REPOSITORY="${DOCKERHUB_USERNAME}/${IMAGE_NAME}"
TAG="${REPOSITORY}:${VERSION}"
LATEST_TAG="${REPOSITORY}:latest"

echo -e "${GREEN}🚀 Начинаю публикацию образа в Docker Hub${NC}"
echo -e "Username: ${DOCKERHUB_USERNAME}"
echo -e "Repository: ${REPOSITORY}"
echo -e "Tag: ${VERSION}"
echo ""

# Проверка, залогинен ли пользователь
if ! docker info | grep -q "Username"; then
    echo -e "${YELLOW}⚠️  Вы не залогинены в Docker Hub${NC}"
    echo "Выполняю docker login..."
    docker login
fi

# Сборка образа (если нужно)
echo -e "${GREEN}📦 Собираю образ для платформы linux/amd64...${NC}"
docker build --platform linux/amd64 -t ${IMAGE_NAME}:${VERSION} .

# Помечаем образ для Docker Hub
echo -e "${GREEN}🏷️  Помечаю образ...${NC}"
docker tag ${IMAGE_NAME}:${VERSION} ${TAG}

# Если версия не latest, также помечаем как latest
if [ "$VERSION" != "latest" ]; then
    echo -e "${GREEN}🏷️  Помечаю как latest...${NC}"
    docker tag ${IMAGE_NAME}:${VERSION} ${LATEST_TAG}
fi

# Загружаем образ
echo -e "${GREEN}📤 Загружаю образ в Docker Hub...${NC}"
docker push ${TAG}

if [ "$VERSION" != "latest" ]; then
    echo -e "${GREEN}📤 Загружаю latest версию...${NC}"
    docker push ${LATEST_TAG}
fi

echo ""
echo -e "${GREEN}✅ Образ успешно загружен в Docker Hub!${NC}"
echo -e "URL: https://hub.docker.com/r/${REPOSITORY}"
echo ""
echo -e "Использование:"
echo -e "  docker pull ${TAG}"
echo -e "  docker run -p 8080:8080 ${TAG}"

