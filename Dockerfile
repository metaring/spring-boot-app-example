# Start with a base image containing Java runtime
FROM openjdk:8-jdk-alpine
# Add Maintainer Info
MAINTAINER MetaRing <info@metaring.com>
# The application's jar file
ARG MAIN_CLASS
ARG BIN_FOLDER
ARG EA_ASYNC_VERSION
## Environment variables
ENV EA_ASYNC_VERSION=${EA_ASYNC_VERSION}
ENV MAIN_CLASS=${MAIN_CLASS}
# Working directory point to /app
WORKDIR /app
VOLUME ./config
# Copy application jar to image
COPY ${BIN_FOLDER} ./bin
# Run the jar file
ENTRYPOINT ["sh","-c","java -Djava.security.egd=file:/dev/./urandom -Djava.net.preferIPv4Stack=true -javaagent:./bin/ea-async-$EA_ASYNC_VERSION.jar -cp ./bin/*:./config $MAIN_CLASS"]