docker stop $(docker ps -a -q) &&
docker rm $(docker ps -a -q) &&
docker-compose -f docker-compose.yaml up -d