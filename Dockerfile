FROM maven:latest AS MAVEN_TOOLCHAIN
COPY pom.xml /tmp/
COPY Dockerfile /tmp/
COPY src /tmp/src/
WORKDIR /tmp/
RUN mvn package

FROM openjdk:12
VOLUME /tmp
EXPOSE 8081
COPY --from=MAVEN_TOOLCHAIN /tmp/target/*.jar /target/
ENTRYPOINT ["java","-jar","/target/generation-service-1.0.0.jar"]
