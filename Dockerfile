FROM openjdk:11-jre
ADD build/libs/parkinglot-0.1-all.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]