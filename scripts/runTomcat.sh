mvn compile -pl database/ flyway:clean &&
mvn compile -pl database/ flyway:migrate &&
mvn tomcat7:deploy -DskipTests "-DtomcatPort=8082"