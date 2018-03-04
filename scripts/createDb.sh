mvn clean install -DskipTests &&
mvn compile -pl com.cities.database:database flyway:migrate -Dflyway.configFile=src/test/resources/flyway.properties