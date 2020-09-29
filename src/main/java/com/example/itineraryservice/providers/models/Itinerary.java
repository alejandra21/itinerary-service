package com.example.itineraryservice.providers.models;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Itinerary<T,V> {
	
	private T origin;
	private T destination;
	private List<V> path =  new ArrayList<>();
	private Long time = 0L;
	
	public Integer getSteps() {
		return path.size();
	}
	
	public void setOriginDestination(T origin,T destination) {
		this.origin = origin;
		this.destination = destination;
	}
}
