package com.example.itineraryservice.providers;

import java.util.ArrayList;
import java.util.List;

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
	
	public List<Itinerary<String, String>> getItinerariesSortedByTimeFrom(String city) {
		
    	CityDto cityDto = cityProvider.getCityByName(city);
   
    	if ( cityDto == null || cityDto.getConnections() == null || cityDto.getConnections().isEmpty() ) {
    		return new ArrayList<>();
    	}
    	
    	List<CityDto> cities = cityProvider.getCities();
    	DirectedWeightedMultigraph<String, DefaultWeightedEdge> multiGraph = graphProvider.convertToWeightedMultigraph(cities);
    	
    	return new ArrayList<>();
	}

	@HystrixCommand(fallbackMethod = "fallback")
	public List<Itinerary<String, String>> getItinerariesSortedByConnectionsFrom(String city) {
		return new ArrayList<>();
	}

}
