# ITINERARY SERVICE

Given a city, this microservice shows the shortest paths by time or steps to the rest of the cities that are stored in the DB

## Connections required

To work correctly this microservice required the following services:

* [Eureka server](https://github.com/alejandra21/discovery-server): Netflix Eureka server
* [City server](https://github.com/alejandra21/city-service): Microservice in charge of providing information about cities and their connections

## Solution approach

This microservce get the information of city-service connections and time, and return the shortest paths.

Finding the shortest path is a very recurring problem in graph theory, that is why I decided to aim my solution in that direction.

So, basically, what I am doing in this service is:

* Get the information of the city-service
* Transform the information got into a graph with weights
* Execute some algorithm, already studied to find shorter paths
* Transform the graph into the object that will be returned by the controller

This microservice has two modes: the search for shorter routes by time, or the search for shorter routes by steps.

To calculate the shortest paths by time, I used the Dijstrak algorithm. On the other hand, to calculate the shortest paths in steps I used the BFS algorithm.

`The graphs were implemented with the help of [jgrapht](https://jgrapht.org/)`

## Dependencies

* Java 8 or Docker
    
## Instructions to run the whole project

Here are the instructions to run the project (without docker):

1. Clone **this** repository, in your PC (git clone)
2. Execute the following command to export env variable an run the project: 
```
cd itinerary-service/config
./mvnw spring-boot:run
```
3. You will be able to access its api in `http://<itinerary-service>:8080/swagger-ui.html`
