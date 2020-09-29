package com.example.itineraryservice.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.itineraryservice.providers.ItineraryProvider;
import com.example.itineraryservice.providers.models.Itinerary;

@RestController
public class ItineraryController {
	
	@Autowired
	ItineraryProvider itineraryProvider;

    @GetMapping("/itinerary-time")
    @CrossOrigin
    public List<Itinerary<String,String>> getItinerariesSortedByTime(@RequestParam(name = "city") String city) {
        return itineraryProvider.getItinerariesSortedByTimeFrom(city);
    }

    @GetMapping("/itinerary-steps")
    @CrossOrigin
    public List<Itinerary<String,String>> getItinerariesSortedByConnections(@RequestParam(name = "city") String city) {
        return itineraryProvider.getItinerariesSortedByConnectionsFrom(city);
    }
	
	
}
