FROM java:8

RUN apt-get update && apt-get install -y --no-install-recommends \
		xmlstarlet \
	&& rm -rf /var/lib/apt/lists/*

RUN mkdir /tmp/install

WORKDIR /tmp/install

COPY pom.xml ./
COPY docker/script.sh ./

RUN ./script.sh
WORKDIR /
RUN rm -rf /tmp/install

EXPOSE 8080
