FROM openjdk:17
EXPOSE 8081
COPY /build/libs/tournament-0.0.1-SNAPSHOT.jar tournament.jar
ENTRYPOINT ["java","-jar","tournament.jar"]