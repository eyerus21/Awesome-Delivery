FROM maven:3.8.2-jdk-11-slim
WORKDIR /usr/src/app
COPY . /usr/src/app
RUN mvn package -Dmaven.test.skip
CMD [ "sh", "-c", "mvn spring-boot:run" ]