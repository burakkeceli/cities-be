mvn clean install -DskipTests &&
mvn compile -pl database/ flyway:migrate -Dflyway.configFile=src/test/resources/flyway.properties