FROM maven:3.8.2-jdk-11-slim
#WORKDIR /usr/src/app
#
#COPY . /usr/src/app
#RUN mvn package
COPY /target/notification-svc-0.0.1-SNAPSHOT.jar /app.jar
CMD ["sh", "-c", "java -jar /app.jar"]
#CMD1 [ "sh", "-c", "mvn spring-boot:run" ]