FROM amazoncorretto:11 AS builder

WORKDIR /app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
RUN ./mvnw dependency:resolve-plugins dependency:resolve dependency:go-offline

COPY src src
RUN ./mvnw install -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)



ARG JAR_FILE=*.jar
COPY ${JAR_FILE} application.jar

ENTRYPOINT ["java", "-noverify", "-jar",  "application.jar"]