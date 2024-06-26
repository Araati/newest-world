#!/usr/bin/env bash

case $1 in
--help|-h)
  help
  ;;

infra)
  docker compose --profile infra up -d
  ;;

rebuild)
  docker rm newest-world_content_1 || true
  docker rm newest-world_executor_1 || true
  docker rm newest-world_scheduler_1 || true
  docker rm newest-world_validator_1 || true
  docker rmi content || true
  docker rmi executor || true
  docker rmi scheduler || true
  docker rmi validator || true
  mvn clean compile package -DskipTests=true
  ;;

*);;
esac

exit 0

