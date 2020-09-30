package com.example.itineraryservice.providers.models;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum SortEnum {

	TIME("TIME"),
	CONNECTION("CONNECTION");
	
	String id;
	
	public String getId() {
		return this.id;
	}
}
