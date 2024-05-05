FROM amazoncorretto:21
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} validator.jar
ENTRYPOINT ["java","-jar","/validator.jar"]