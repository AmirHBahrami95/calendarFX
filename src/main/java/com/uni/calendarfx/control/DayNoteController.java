package com.uni.calendarfx.control;

import com.uni.calendarfx.App;
import com.uni.calendarfx.Events;

import com.uni.calendarfx.model.State;
import com.uni.calendarfx.model.DayNote;
import com.uni.calendarfx.model.Observer;
import com.uni.calendarfx.model.DateHandler;
import com.uni.calendarfx.model.JalaliDateHandler;
import com.uni.calendarfx.model.HijrahDateHandler;
import com.uni.calendarfx.model.GeorgianDateHandler;

import javafx.fxml.Initializable;
import javafx.fxml.FXML;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.OverrunStyle;

import javafx.scene.layout.FlowPane;

import java.net.URL;

import java.time.chrono.HijrahDate;
import java.time.chrono.ChronoLocalDate;

import java.time.format.DateTimeFormatter;


import java.time.LocalDate;

import java.io.IOException;

import java.util.ResourceBundle;

import javafx.geometry.Insets;

import javafx.scene.text.Font;

import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import com.uni.calendarfx.io.FileUtils;

public class DayNoteController implements Controller, Initializable{

    @FXML private Button goBackButton;
    @FXML private TextField dateField;
    @FXML private RadioButton reminderRadio;
    @FXML private TextArea noteField;

    private DateHandler dateHandler;

		public void initialize(URL url,ResourceBundle resourceBundle){
        updateDateHandler();
        this.dateField.setText(
            String.valueOf(this.dateHandler.getYMD()[0])+"-"+
            this.dateHandler.getMonthName()+"-"+
            String.valueOf(this.dateHandler.getYMD()[2])
        );
				readFromDeserialized();
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

    @FXML public void goBack(){
        initSerFile();
        State.getInstance().notifyAll(Events.SCENE_CHANGED,App.MAIN_SCENE);
    }

    private void initSerFile(){

        // neither any text given, nor reminder is selected
        if( !reminderRadio.isSelected() 
        && this.noteField.getText().trim().isEmpty())
            return;
        
        // make an object and serialize it
        DayNote dn=new DayNote(
            this.dateHandler.getLocalDate()
            ,reminderRadio.isSelected()
            ,noteField.getText()
        );
        if(DayNote.serialize(dn))
            App.showInfo("Date Saved : "+dn.getSerializedFileName());
    }

    private void readFromDeserialized(){
        String notePath=DayNote.getSerFilePath(this.dateHandler.getLocalDate());
        DayNote dn=DayNote.deserialize(notePath);
        if(dn!=null){
            this.noteField.setText(dn.getNote());
            this.reminderRadio.setSelected(true);
        }
    }

}
