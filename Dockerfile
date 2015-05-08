FROM java:8

ADD ./buildoutput/*.jar /opt/

WORKDIR /opt/

EXPOSE 8080
