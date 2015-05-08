#!/bin/bash

BASE_URL='http://janine.navita.com.br/archiva/repository/snapshots'
GROUP='org/test'
ARTIFACT='single'

VERSION=$(xmlstarlet sel -N x="http://maven.apache.org/POM/4.0.0" -t -v '/x:project/x:version' pom.xml)

echo "Downloading file metadata..."
METADATA=$(curl $BASE_URL/$GROUP/$ARTIFACT/$VERSION/maven-metadata.xml)

TIMESTAMP=$(echo $METADATA | xmlstarlet sel -t -v  '/metadata/versioning/snapshot/timestamp')
BUILD_NUMBER=$(echo $METADATA | xmlstarlet sel -t -v  '/metadata/versioning/snapshot/buildNumber')
SHORT_VERSION=$(echo $VERSION | sed 's/-SNAPSHOT//g')


FILE="${ARTIFACT}-${SHORT_VERSION}-${TIMESTAMP}-${BUILD_NUMBER}.jar"

echo "Downloading jar file..."
curl $BASE_URL/$GROUP/$ARTIFACT/$VERSION/$FILE -o /opt/angular-app.jar

echo "Downloading shasum..."
SHASUM=$(curl $BASE_URL/$GROUP/$ARTIFACT/$VERSION/$FILE.sha1)

echo "$SHASUM /opt/angular-app.jar" > $FILE.sha1

shasum -c $FILE.sha1
RESULT=$?

if [ $RESULT -ne 0 ]; then
	echo "The jar file is corrupted!"
	exit $RESULT
fi

echo "Deploy successful"
