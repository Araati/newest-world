#!/usr/bin/env bash

docker rm newest-world_content_1 || true
docker rm newest-world_executor_1 || true
ocker rm newest-world_scheduler_1 || true
docker rmi content || true
docker rmi executor || true
docker rmi scheduler || true
mvn clean compile package -DskipTests=true

exit 0

