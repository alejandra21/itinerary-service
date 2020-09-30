package com.example.itineraryservice.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.itineraryservice.providers.ItineraryProvider;
import com.example.itineraryservice.providers.models.Itinerary;
import com.example.itineraryservice.providers.models.SortEnum;

@RestController
public class ItineraryController {
	
	@Autowired
	ItineraryProvider itineraryProvider;
    
    @GetMapping("/itinerary")
    @CrossOrigin
    public List<Itinerary<String,String>> getItineraries(@RequestParam(name = "sortedBy") SortEnum sort,
    													 @RequestParam(name = "city") String city) {
    	
        return itineraryProvider.getItineraries(city,sort);
    }
	
	
}
