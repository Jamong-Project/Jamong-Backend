FROM centos:7
RUN yum update -y && \
yum install -y wget && \
yum install -y java-1.8.0-openjdk java-1.8.0-openjdk-devel && \
yum clean all
RUN sh -c "$(wget -O- https://github.com/deluan/zsh-in-docker/releases/download/v1.1.1/zsh-in-docker.sh)"
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Dspring.config.location = classpath:/application-real-db.properties, classpath:/application-rds.properties","-Dspring.profiles.active = rds", "-Xms2048m", "-Xmx4096m", "-jar","/app.jar"]
EXPOSE 8080