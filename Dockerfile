FROM java:8

COPY target/*.jar /opt/angular-app.jar
WORKDIR /opt

EXPOSE 8080
