package com.example.itineraryservice.utilities;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Utilities {

	public static String secondsToHourMinSeconds(Long seconds) {

        long hours = seconds / 3600;
        long minutes = (seconds%3600)/60;
        long secondsOutput = (seconds% 3600)%60;
        
        String result = String.format("%02d:%02d:%02d", hours, minutes, secondsOutput);        
        log.info("This is the time convertion: %d ---> %s",seconds,result);
		return result;
		
	} 
	
}
