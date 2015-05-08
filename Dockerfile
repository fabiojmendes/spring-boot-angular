FROM java:8

RUN apt-get update && apt-get install -y --no-install-recommends \
		xmlstarlet \
	&& rm -rf /var/lib/apt/lists/*

WORKDIR /tmp/

COPY pom.xml ./
COPY docker/script.sh ./

RUN ./script.sh && rm *

EXPOSE 8080
