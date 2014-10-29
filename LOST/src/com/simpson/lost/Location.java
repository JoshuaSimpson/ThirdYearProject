package com.simpson.lost;

public class Location {

	private String locationName;
	private String MACOne;
	private String MACTwo;
	private String MACThree;
	
	public Location(String nameOf){
		this.locationName = nameOf;
		MACOne = "String";
		MACTwo = "String";
		MACThree = "String";
	}
	
	public String locName()
	{
		return this.locationName;
	}
}
