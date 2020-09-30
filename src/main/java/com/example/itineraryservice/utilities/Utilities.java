package com.example.itineraryservice.utilities;

public class Utilities {

	public static String secondsToHourMinSeconds(Long seconds) {


        long hours = seconds / 3600;
        long minutes = (seconds%3600)/60;
        long secondsOutput = (seconds% 3600)%60;

        System.out.println("The time entered in hours,minutes and seconds is:");
        System.out.println(hours  + " hours :" + minutes + " minutes:" + secondsOutput +" seconds");
		
		return String.format("%02d:%02d:%02d", hours, minutes, secondsOutput);
	} 
	
}
