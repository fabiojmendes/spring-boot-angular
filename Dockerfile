FROM java:8

ENV VERSION=single-0.0.3

ADD https://github.com/fabiojmendes/spring-boot-angular/releases/download/$VERSION/angular-app.jar /opt/

EXPOSE 8080
