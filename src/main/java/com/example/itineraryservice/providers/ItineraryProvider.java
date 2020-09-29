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
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Component
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
				SingleSourcePaths<String, DefaultWeightedEdge> shortesPaths) {
		
    	
    	List<Itinerary<String, String>> itineraries = new ArrayList<>();
    	GraphPath<String, DefaultWeightedEdge> path;
    	Itinerary<String, String> itinerary;
    	Set<String> vertex;
    	List<String> vertexList;
    	Long weight;
    	
    	if ( shortesPaths == null || shortesPaths.getGraph() == null ) {
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
            	weight = (long) shortesPaths.getWeight(destination);
            	itinerary = new Itinerary<>(origin,destination,vertexList,weight);
        	}
        	
        	itineraries.add(itinerary);
         }
        
		return itineraries;
	}
	
	public List<Itinerary<String, String>> getItinerariesSortedByTimeFrom(String city) {
		
    	CityDto cityDto = cityProvider.getCityByName(city);
   
    	if ( cityDto == null || cityDto.getConnections() == null || cityDto.getConnections().isEmpty() ) {
    		return new ArrayList<>();
    	}
    	
    	List<CityDto> cities = cityProvider.getCities();
    	DirectedWeightedMultigraph<String, DefaultWeightedEdge> multiGraph = graphProvider.convertToWeightedMultigraph(cities);
    	SingleSourcePaths<String, DefaultWeightedEdge> shortesPaths = graphProvider.dijkstraShortestPath(city,multiGraph);
    	
    	return convertToItineraries(city,shortesPaths);
    	
	}

	@HystrixCommand(fallbackMethod = "fallback")
	public List<Itinerary<String, String>> getItinerariesSortedByConnectionsFrom(String city) {
		return new ArrayList<>();
	}

}
