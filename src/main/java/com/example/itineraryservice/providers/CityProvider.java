package com.example.itineraryservice.providers;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.itineraryservice.providers.models.CityDto;

@FeignClient("city-service")
public interface CityProvider {

    @GetMapping(value = "/city/{name}")
    @CrossOrigin
    CityDto getCityByName(@PathVariable("name") String name);
    
    @GetMapping(value = "/cities")
    @CrossOrigin
    List<CityDto> getCities();
    
}
