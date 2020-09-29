package com.example.itineraryservice.providers.models;

import java.util.Date;

import lombok.Data;

@Data
public class ConnectionsDto {

	private Date arrival;
	private Date departure;
	private String cityDestination;
	private Long totalTime;
}
