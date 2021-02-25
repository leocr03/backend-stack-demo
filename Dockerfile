FROM openjdk:17-oraclelinux8
#RUN apt-get update
#RUN apt-get install -y curl
ENV PATH="/usr/sbin:${PATH}"
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]