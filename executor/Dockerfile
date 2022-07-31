FROM openjdk:11
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} executor.jar
ENTRYPOINT ["java","-jar","/executor.jar"]