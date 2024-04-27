package com.uni.calendarfx.model;

/**
* All objects who are supposed to react to state changes in program
* should implement this interface

* the method "update" receives one msg string and multiple Objects
* so if you want to use those Objects you need to cast them explicitly:
	Day d= (Day) data[0];
*/
public interface Observer{
	public void update(String msg,Object data);
}
