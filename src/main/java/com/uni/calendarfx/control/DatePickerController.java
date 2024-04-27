package com.uni.calendarfx.control;

import com.uni.calendarfx.Events;

import com.uni.calendarfx.model.State;
import com.uni.calendarfx.model.Observer;
import com.uni.calendarfx.model.DateHandler;
import com.uni.calendarfx.model.JalaliDateHandler;
import com.uni.calendarfx.model.HijrahDateHandler;
import com.uni.calendarfx.model.GeorgianDateHandler;

import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

import java.net.URL;

import java.time.chrono.HijrahDate;
import java.time.chrono.ChronoLocalDate;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.Locale;
import java.util.ResourceBundle;

public class DatePickerController implements Controller, Initializable{

	@FXML ChoiceBox yearChoice;
	@FXML ChoiceBox monthChoice;
	@FXML ChoiceBox calendarChoice;
	@FXML Label monthLabel;

    private DateHandler dateHandler;

	public void initialize(URL url,ResourceBundle resourceBundle){
		// TODO fill the combo-boxes and labels with state's default values
        updateDateHandler();
		updateDate(); 
		initCalendarChoice();
	}
	
	@FXML public void changeDate(){
		State st=State.getInstance();
		ChronoLocalDate newDate=this.dateHandler.of(
			Integer.parseInt((String)this.yearChoice.getValue()),
			Integer.parseInt((String)this.monthChoice.getValue()),
			1 // just some trivial value, doesn't matter here
		);
		st.setChronoLocalDate(newDate); // inform the state about it
        updateDateHandler();
		updateMonthLabel();
        // updateDate();
	}

	@FXML public void changeCalendarType(){
		State st=State.getInstance();
		st.setCalendarType((String)this.calendarChoice.getValue());
		updateDate();
	}

	private void updateDate(){
		
		// updating displaued values
        updateDateHandler();
		clearChoiceBoxes();
		updateYearValues();
		updateMonthValues();
	}

	private void updateYearValues(){

		int currentYear=this.dateHandler.getYMD()[0];
        int startYear=this.dateHandler.getStartYear();
		populateYearValues(startYear,currentYear,5);
	}

	private void updateMonthValues(){

        int currentMonth=this.dateHandler.getYMD()[1];
		populateMonthValues(currentMonth);
		updateMonthLabel();
	}

    private void populateYearValues(int startYear,int currentYear,int yearsAhead){
		
		// lenOfYear is required to tell if a year is a leap year
		String values[]=new String[yearsAhead];
		for(int i=startYear,j=0;i<(startYear+yearsAhead);i++,j++)
			values[j]=String.valueOf(i);
		this.yearChoice.getItems().addAll(values);
		this.yearChoice.getSelectionModel().select(currentYear);
	}

	private void updateMonthLabel(){

		State st=State.getInstance();
		ChronoLocalDate currentDate=st.getChronoLocalDate();	

		// updating month lable (alphabetic)
		String currentMonthStr=this.dateHandler.getMonthName();
		monthLabel.setText(currentMonthStr);
	}

	private void populateMonthValues(int currentMonth){
		String values[]=new String[12];
		for (int i=1,j=0; i<=12; i++,j++)
			values[j]=String.valueOf(i);
		this.monthChoice.getItems().addAll(values);
		this.monthChoice.getSelectionModel().select(currentMonth-1);
	}

	private void clearChoiceBoxes(){
		this.yearChoice.getItems().clear();
		this.monthChoice.getItems().clear();
	}

	private void initCalendarChoice(){ // init* only for the first time in program
		this.calendarChoice.getItems().addAll(
			State.GEORGIAN,
			State.JALALI,
			State.HEJRAH
		);
		this.calendarChoice.getSelectionModel().select(State.getInstance().getCalendarType());
	}

    private void updateDateHandler(){
        String calendarType=State.getInstance().getCalendarType();
        ChronoLocalDate currentDate=State.getInstance().getChronoLocalDate();
        if(calendarType.equals(State.JALALI))
            this.dateHandler=new JalaliDateHandler(currentDate);
        else if(calendarType.equals(State.HEJRAH))
            this.dateHandler=new HijrahDateHandler(currentDate);
        else
            this.dateHandler=new GeorgianDateHandler(currentDate);
    }

}
