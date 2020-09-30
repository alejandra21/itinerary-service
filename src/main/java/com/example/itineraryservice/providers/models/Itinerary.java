package com.example.itineraryservice.providers.models;

import java.util.ArrayList;
import java.util.List;

import com.example.itineraryservice.utilities.Utilities;

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
		return path.isEmpty() ? 0 : path.size() - 1;
	}
	
	public void setOriginDestination(T origin,T destination) {
		this.origin = origin;
		this.destination = destination;
	}
	
	public String getTotalTime() {
		
		if ( this.time <= 0 ) {
			return "-";
		}
		
		return Utilities.secondsToHourMinSeconds(this.time);
		
	}
	
}
