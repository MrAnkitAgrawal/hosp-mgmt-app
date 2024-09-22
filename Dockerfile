FROM openjdk:17-jdk-alpine

LABEL Maintainer="Ankit Agrawal (agrawal.ank83@gmail.com)"

EXPOSE 8080

# Prerequisite: take latest API code and build app jar file
COPY target/hosp-mgmt-app-0.0.1-SNAPSHOT.jar hosp-mgmt-app.jar

ENV DB_PROFILE=h2

ENTRYPOINT ["sh", "-c", "java -jar -Dspring.profiles.active=${DB_PROFILE} hosp-mgmt-app.jar"]
