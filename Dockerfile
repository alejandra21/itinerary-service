From openjdk:8
copy ./target/itinerary-service-0.0.1-SNAPSHOT.jar itinerary-service-0.0.1-SNAPSHOT.jar
CMD ["java","-jar","itinerary-service-0.0.1-SNAPSHOT.jar"]