FROM amazoncorretto:11-alpine-jdk

COPY collections-microservice/target/collections-microservice-0.0.1-SNAPSHOT.jar collections-microservice-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java","-jar","/collections-microservice-0.0.1-SNAPSHOT.jar"]


