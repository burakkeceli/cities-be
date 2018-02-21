{
    docker stop $(docker ps -a -q) &&
    docker-compose -f docker-compose-local.yaml up -d
} || {
    docker-compose -f docker-compose-local.yaml up -d
}