FROM adoptopenjdk/openjdk11:alpine-jre
EXPOSE 8080
COPY ./target/zooplus-assignment-0.0.1-SNAPSHOT.jar ./application.jar
ENTRYPOINT ["java","-jar","./application.jar"]
