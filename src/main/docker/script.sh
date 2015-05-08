#!/bin/bash

set -u -e

GROUP=$(xmlstarlet sel -N x="http://maven.apache.org/POM/4.0.0" -t -v '/x:project/x:groupId' pom.xml | sed 's/\./\//g')
ARTIFACT=$(xmlstarlet sel -N x="http://maven.apache.org/POM/4.0.0" -t -v '/x:project/x:artifactId' pom.xml)
VERSION=$(xmlstarlet sel -N x="http://maven.apache.org/POM/4.0.0" -t -v '/x:project/x:version' pom.xml)
SHORT_VERSION=${VERSION%-SNAPSHOT}

if [[ "$VERSION" == *"SNAPSHOT" ]]; then
	BASE_URL=$(xmlstarlet sel -N x="http://maven.apache.org/POM/4.0.0" -t -v '/x:project/x:distributionManagement/x:snapshotRepository/x:url' pom.xml)
else
	BASE_URL=$(xmlstarlet sel -N x="http://maven.apache.org/POM/4.0.0" -t -v '/x:project/x:distributionManagement/x:repository/x:url' pom.xml)
fi


echo "Downloading file metadata..."
curl -s -O $BASE_URL/$GROUP/$ARTIFACT/$VERSION/maven-metadata.xml

TIMESTAMP=$(xmlstarlet sel -t -v  '/metadata/versioning/snapshot/timestamp' maven-metadata.xml)
BUILD_NUMBER=$(xmlstarlet sel -t -v  '/metadata/versioning/snapshot/buildNumber' maven-metadata.xml)


FILE="${ARTIFACT}-${SHORT_VERSION}-${TIMESTAMP}-${BUILD_NUMBER}.jar"

echo "Downloading jar file..."
curl -s $BASE_URL/$GROUP/$ARTIFACT/$VERSION/$FILE -o /opt/angular-app.jar

echo "Downloading shasum..."
SHASUM=$(curl -s $BASE_URL/$GROUP/$ARTIFACT/$VERSION/$FILE.sha1)

echo "$SHASUM  /opt/angular-app.jar" > $FILE.sha1

shasum -c $FILE.sha1 ||  { echo "The jar file is corrupted!"; exit 1; }

echo "Deploy successful"
