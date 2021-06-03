package com.techthinker.airlinesafety;

import java.math.BigDecimal;

public class Airline {

	String name;
	int incidents;
	int accidents;
	int fatalities;
	long availSeatKmWeek;
	BigDecimal fatalitiesRatio;

	public BigDecimal getFatalitiesRatio() {
		return fatalitiesRatio;
	}
	public void setFatalitiesRatio(BigDecimal fatalitiesRatio) {
		this.fatalitiesRatio = fatalitiesRatio;
	}
	public long getAvailSeatKmWeek() {
		return availSeatKmWeek;
	}
	public void setAvailSeatKmWeek(long availSeatKmWeek) {
		this.availSeatKmWeek = availSeatKmWeek;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getIncidents() {
		return incidents;
	}
	public void setIncidents(int incidents) {
		this.incidents = incidents;
	}
	public int getAccidents() {
		return accidents;
	}
	public void setAccidents(int accidents) {
		this.accidents = accidents;
	}
	public int getFatalities() {
		return fatalities;
	}
	public void setFatalities(int fatalities) {
		this.fatalities = fatalities;
	}

}
