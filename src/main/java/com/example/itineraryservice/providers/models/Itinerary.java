package com.example.itineraryservice.providers.models;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Itinerary<T,V> {
	
	private T origin;
	private List<V> path =  new ArrayList<>();
	private Integer steps = 0;
	private Long time = 0L;
}
