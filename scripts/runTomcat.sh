mvn clean install -DskipTests &&
mvn compile -pl com.cities.database:database flyway:clean &&
mvn compile -pl com.cities.database:database flyway:migrate &&
mvn tomcat7:undeploy -DskipTests "-DtomcatPort=$1" &&
mvn tomcat7:deploy -DskipTests "-DtomcatPort=$1"