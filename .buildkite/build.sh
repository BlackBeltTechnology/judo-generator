#!/bin/bash

set -ex
MAVEN_PROJECT_VERSION=$(python -c "import xml.etree.ElementTree as ET; print(ET.parse(open('pom.xml')).getroot().find('{http://maven.apache.org/POM/4.0.0}version').text)")
PROJECT_VERSION=$(echo $MAVEN_PROJECT_VERSION | sed "s/-SNAPSHOT/-$BUILDKITE_BUILD_NUMBER/")
mvn versions:set -DnewVersion=$PROJECT_VERSION
mvn versions:set -DnewVersion=$PROJECT_VERSION -f hu.blackbelt.judo.generator.parent
mvn clean deploy -DaltDeploymentRepository=blackbelt-nexus-distribution::default::${NEXUS3_URL}/repository/maven-judo-generator-release
git tag -a ${PROJECT_VERSION} -m '${PROJECT_VERSION}' && git push origin ${PROJECT_VERSION}
