#!/bin/bash

SERVICES=("account-service" "message-service")

if docker ps > /dev/null 2>&1; then
    DOCKER_CMD="docker"
    MVN_CMD="mvn"
else
    DOCKER_CMD="sudo docker"
    MVN_CMD="sudo mvn"
    echo "Warning: Using sudo for Docker and mvn commands"
fi

for service in "${SERVICES[@]}"; do
    echo "Removing container and image for: $service"
    $DOCKER_CMD stop "$service" 2>/dev/null
    $DOCKER_CMD rm "$service" 2>/dev/null
    $DOCKER_CMD rmi "alishoumar/$service:1.0.0" 2>/dev/null || echo "Failed to remove image for $service"
done

echo "Current Docker images:"
$DOCKER_CMD images

echo "Creating new images:"

for service in "${SERVICES[@]}"; do
    echo "Creating image: alishoumar/$service:1.0.0"
    if cd "$service"; then
        $MVN_CMD compile jib:dockerBuild
        cd ..
    else
        echo "Failed to enter directory for $service"
    fi
done

$DOCKER_CMD images