#-----------------------------------------------------------------------------------------------------------------------
# Docker Base
#-----------------------------------------------------------------------------------------------------------------------
FROM openjdk:21-slim-buster AS base
LABEL maintainer="bayudwiyansatria@gmail.com"
WORKDIR /app
  
#-----------------------------------------------------------------------------------------------------------------------
# Docker Build
#-----------------------------------------------------------------------------------------------------------------------
FROM maven:3.9.9-amazoncorretto-21 AS build
WORKDIR /build

ARG MAVEN_USERNAME
ARG MAVEN_PASSWORD

COPY . .

# Install Dependencies
RUN mvn \
    --no-transfer-progress \
    --settings settings.xml \
    --file pom.xml \
    -DskipTests \
    clean \
    package

#-----------------------------------------------------------------------------------------------------------------------
# Docker Release
#-----------------------------------------------------------------------------------------------------------------------
FROM base AS release

COPY --from=build /build/*.jar app.jar

EXPOSE 80

ENTRYPOINT [ "java", "-jar" ,"app.jar" ]
