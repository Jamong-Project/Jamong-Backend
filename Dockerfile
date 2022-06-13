FROM openjdk:8-alpine
RUN sh -c "$(wget -O- https://github.com/deluan/zsh-in-docker/releases/download/v1.1.1/zsh-in-docker.sh)"
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Dspring.config.location = classpath:/**/application-real-db.properties, classpath:/application-rds.properties","-Dspring.profiles.active = rds","-jar","/app.jar"]
EXPOSE 8080