FROM java:8

RUN apt-get update && apt-get upgrade -y
RUN apt-get install -y --no-install-recommends \
		xmlstarlet

RUN mkdir /tmp/install

WORKDIR /tmp/install

COPY pom.xml ./
COPY src/main/docker ./

RUN bash script.sh

WORKDIR /opt

RUN apt-get autoremove -y
RUN rm -rf /tmp/install /var/lib/apt/lists/*

EXPOSE 8080
