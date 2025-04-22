#!/bin/bash
echo "🐳 Parando containers com Docker Compose..."
docker compose --profile dev down --volumes --rmi all

