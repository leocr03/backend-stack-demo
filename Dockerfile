FROM openjdk:16
ENV PATH="/usr/sbin:${PATH}"
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]