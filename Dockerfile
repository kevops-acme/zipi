FROM openjdk:11.0.5-jre-slim
RUN apt-get update
RUN apt-get install -y curl
COPY target/*.jar /app/kairosds-app.jar
WORKDIR /app
EXPOSE 8080
CMD ["java", "-jar", "kairosds-app.jar"]

RUN groupadd -r kairosds && useradd --no-log-init -r -g kairosds kairosds
USER kairosds