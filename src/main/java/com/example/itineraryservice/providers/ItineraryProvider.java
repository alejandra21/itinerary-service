package com.example.itineraryservice.providers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm.SingleSourcePaths;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.DirectedWeightedMultigraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.itineraryservice.providers.models.CityDto;
import com.example.itineraryservice.providers.models.Itinerary;
import com.example.itineraryservice.providers.models.SortEnum;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ItineraryProvider {

	@Autowired
	private CityProvider cityProvider;
	
	@Autowired
	private GraphProvider graphProvider;
	
    private List<Itinerary<String, String>> fallback(String city) {
        return new ArrayList<>();
    }
    
    private List<Itinerary<String, String>> convertToItineraries(
    			String origin,
    			boolean includeWeight,
				SingleSourcePaths<String, DefaultWeightedEdge> shortesPaths) {
		
    	
    	List<Itinerary<String, String>> itineraries = new ArrayList<>();
    	GraphPath<String, DefaultWeightedEdge> path;
    	Itinerary<String, String> itinerary;
    	Set<String> vertex;
    	List<String> vertexList;
    	Long weight;
    	
    	if ( shortesPaths == null || shortesPaths.getGraph() == null ) {
    		log.info(String.format("The city: %s does not have a shortest path to other cities", origin) );
    		return itineraries;
    	}
    	
    	vertex = shortesPaths.getGraph().vertexSet();
    	
        for (String destination : vertex) {
        	
        	if ( destination.equals(origin) ) {
        		continue;
        	}
        
        	path = shortesPaths.getPath(destination);
        	vertexList = path != null ? path.getVertexList(): null;
        	
        	if ( vertexList == null ) {
        		itinerary = new Itinerary<>();
        		itinerary.setOriginDestination(origin,destination);
        	}
        	else {
            	weight = includeWeight ? (long) shortesPaths.getWeight(destination) : -1;
            	itinerary = new Itinerary<>(origin,destination,vertexList,weight);
        	}
        	
        	itineraries.add(itinerary);
         }
        
		return itineraries;
	}
	
    @HystrixCommand(fallbackMethod = "fallback")
	public List<Itinerary<String, String>> getItinerariesSortedByTimeFrom(String city) {
		
    	CityDto cityDto = cityProvider.getCityByName(city);
   
    	if ( cityDto == null || cityDto.getConnections() == null || cityDto.getConnections().isEmpty() ) {
    		log.info(String.format("The city: %s is not available in city-service, or does not have connections", city) );
    		return new ArrayList<>();
    	}
    	
    	List<CityDto> cities = cityProvider.getCities();
    	DirectedWeightedMultigraph<String, DefaultWeightedEdge> multiGraph = graphProvider.convertToWeightedMultigraph(cities);
    	SingleSourcePaths<String, DefaultWeightedEdge> shortesPaths = graphProvider.dijkstraShortestPath(city,multiGraph);
    	Boolean includeWeight = true;
    	return convertToItineraries(city,includeWeight,shortesPaths);
    	
	}

	@HystrixCommand(fallbackMethod = "fallback")
	public List<Itinerary<String, String>> getItinerariesSortedByConnectionsFrom(String city) {
    	CityDto cityDto = cityProvider.getCityByName(city);
    	   
    	if ( cityDto == null || cityDto.getConnections() == null || cityDto.getConnections().isEmpty() ) {
    		log.info(String.format("The city: %s is not available in city-service, or does not have connections", city) );
    		return new ArrayList<>();
    	}
    	
    	List<CityDto> cities = cityProvider.getCities();
    	DirectedWeightedMultigraph<String, DefaultWeightedEdge> multiGraph = graphProvider.convertToWeightedMultigraph(cities);
    	SingleSourcePaths<String, DefaultWeightedEdge> shortesPaths = graphProvider.bfsShortestPath(city,multiGraph);
    	Boolean includeWeight = false;
    	return convertToItineraries(city,includeWeight,shortesPaths);
    	
	}

	public List<Itinerary<String, String>> getItineraries(String city, SortEnum sort) {
		
		log.info(String.format("We are going to find the itinerary of %s BY : %s ", city, sort.getId()) );
		
		switch(sort) {
		  case TIME:
		    return getItinerariesSortedByTimeFrom(city);
		  case CONNECTION:
		    return getItinerariesSortedByConnectionsFrom(city);
		  default:
		    return new ArrayList<>();
		}
		
	}

}
