#!/bin/bash

# Скрипт для сборки Docker образа с правильной платформой
# Использование: ./build-docker.sh [version]

set -e

VERSION=${1:-latest}
IMAGE_NAME="assignment3-backend"

echo "🔨 Собираю Docker образ для платформы linux/amd64..."
echo "Версия: ${VERSION}"
echo ""

# Сборка с указанием платформы
docker build --platform linux/amd64 -t ${IMAGE_NAME}:${VERSION} .

echo ""
echo "✅ Образ успешно собран!"
echo "Имя образа: ${IMAGE_NAME}:${VERSION}"
echo ""
echo "Для публикации в Docker Hub:"
echo "  docker tag ${IMAGE_NAME}:${VERSION} YOUR_USERNAME/${IMAGE_NAME}:${VERSION}"
echo "  docker push YOUR_USERNAME/${IMAGE_NAME}:${VERSION}"

