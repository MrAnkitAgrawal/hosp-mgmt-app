FROM openjdk:17-jdk-alpine

LABEL Maintainer="Ankit Agrawal (agrawal.ank83@gmail.com)"

EXPOSE 8080

COPY target/hosp-mgmt-app-0.0.1-SNAPSHOT.jar hosp-mgmt-app.jar

#ENV PROFILE=postgres
ENV PROFILE=h2

ENTRYPOINT ["sh", "-c", "java -jar -Dspring.profiles.active=${PROFILE} hosp-mgmt-app.jar"]
