#!/bin/bash
echo "🐳 Subindo containers com Docker Compose..."
docker compose --profile dev up --build -d
