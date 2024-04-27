package com.uni.calendarfx.model;

import java.time.LocalDate;

import java.time.chrono.ChronoLocalDate;
import java.time.chrono.HijrahDate;

import java.time.format.DateTimeFormatter;

import com.uni.calendarfx.Events;
import com.uni.calendarfx.App;

import java.util.List;
import java.util.ArrayList;

import com.uni.calendarfx.io.FileUtils;

import java.io.File;
import java.io.IOException;

import com.uni.calendarfx.model.DayNote;

import org.bardframework.time.LocalDateJalali;

/***
	State class holds all the variables required by controllers
	and also is an observable class. so wherever notifyAll() is called,
	all subscriber objects will be informed about the new event
	@see com.uni.calendarfx.model.Observable
*/
public final class State extends Observable{
	
	private static State instance=null;

	public static final DateTimeFormatter 
		STANDARD_DATETIME_FORMAT=DateTimeFormatter.ofPattern("YYYY_MM_dd");
	
	public static final String 
		JALALI="جلالی",
		HEJRAH="هجری قمری",
		GEORGIAN="Georgian";

	public static State getInstance(){
		if(instance==null)
			instance=new State();
		return instance;
	}

	// ----------------------------------------------- MEMBERS ------------------------------------------->
	private ChronoLocalDate chronoLocalDate; // current_date
	private String calendarType; // calendar type (jalali, georgian,etc.)

	private State(){ // is only called once during the whole program (singleton)
		this.chronoLocalDate=LocalDate.now();
		this.calendarType=JALALI;
	}

	// GETTERS
	public ChronoLocalDate getChronoLocalDate(){return this.chronoLocalDate;}
	public String getCalendarType(){return this.calendarType;}

	// SETTERS - who also update all subscribers to State singleton object
	public void setChronoLocalDate(ChronoLocalDate cld){
		this.chronoLocalDate=cld;
		super.notifyAll(Events.DATE_CHANGED,this.chronoLocalDate);
	}

	public void setCalendarType(String ct){

		if(ct==null){
			System.err.println("Got NULL calendarType");
			return;
		}
	
		// sometimes the choiceboxes make a bug where the old
		// and new values are the same - therefore we need
		// to make sure that's not the case here
		if(ct.equals(this.calendarType)) return;

		// only 3 types are allowed
		this.calendarType=ct;
		ChronoLocalDate oldDate=this.chronoLocalDate;

		if(ct.equals(HEJRAH))
			this.chronoLocalDate=HijrahDate.from(oldDate);
		else if(ct.equals(JALALI)) // TODO change to jalali
			this.chronoLocalDate=LocalDateJalali.from(oldDate);
		else if(ct.equals(GEORGIAN)){
			this.chronoLocalDate=LocalDate.from(oldDate);
		}
		// TODO make sure the correct type is used
		super.notifyAll(Events.CALENDAR_CHANGED,this.calendarType);
	}
	
}
