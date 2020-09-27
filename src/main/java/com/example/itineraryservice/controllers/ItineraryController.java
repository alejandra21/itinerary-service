package com.example.itineraryservice.controllers;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
public class ItineraryController {

	@FeignClient("city-service")
	interface CityClient {
	    @GetMapping(value = "/bye")
	    @CrossOrigin
	    String getBye();    
	}
	
    private final CityClient cityClient;

    public ItineraryController(CityClient cityClient) {
        this.cityClient = cityClient;
    }

    private String fallback() {
        return null;
    }

    @GetMapping("/get-bye")
    @CrossOrigin
    @HystrixCommand(fallbackMethod = "fallback")
    public String getBye() {
        return cityClient.getBye();
    }

	
	
}
