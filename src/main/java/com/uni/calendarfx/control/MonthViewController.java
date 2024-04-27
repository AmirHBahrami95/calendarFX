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

import java.util.Locale;
import java.util.ResourceBundle;

import javafx.geometry.Insets;

import javafx.scene.text.Font;


public class MonthViewController implements Controller, Initializable, Observer{

	@FXML FlowPane monthView;

    private DateHandler dateHandler;

	public void initialize(URL url,ResourceBundle resourceBundle){
        monthView.setVgap(25);
        monthView.setHgap(25);
        State.getInstance().subscribe(this);
        updateDateHandler();
        updateDisplay();
    }

    @Override
    public void update(String msg,Object data){
        if(msg.equals(Events.DATE_CHANGED) || msg.equals(Events.CALENDAR_CHANGED)){
            System.out.println("MonthViewController: "+msg);
            updateDateHandler();
            updateDisplay();
        }
    }

    private void updateDisplay(){
        int monthLength=dateHandler.getMonthLength();
        this.monthView.getChildren().clear();
        for(int i=1,j=1;i<monthLength;i++,j++)
            this.makeDayButton(j,getButtonLabel(i));
    }

    private Button makeDayButton(int dayNum,String note){
        String label=String.valueOf(dayNum);
        if(note!=null)
            label+="\n---\n"+note;
        Button btn=new Button(label);

        // customizing
        int inset=20;
        btn.setPadding(new Insets(inset,inset,inset,inset));

        btn.setFont(new Font(14));
        btn.setTextOverrun(OverrunStyle.CLIP);

        int pixels=100;
        btn.setMinSize(pixels,pixels);
        btn.setMaxWidth(pixels);
        btn.setMaxHeight(pixels);

        // setting what the button is supposed to do
        btn.setOnAction(e ->{
            changeDate(dayNum);
            State.getInstance().notifyAll(Events.SCENE_CHANGED,App.DAY_SCENE);
        });

        // adding to children (contentPane)
        this.monthView.getChildren().add(btn);
        return btn;
    }

    private String getButtonLabel(int i){
        LocalDate currentDate=this.dateHandler.getLocalDate();
        LocalDate temp=LocalDate.of(
            currentDate.getYear(),
            currentDate.getMonthValue(),
            i
        );
        DayNote dn=DayNote.deserialize(DayNote.getSerFilePath(temp));
        if(dn!=null){
            return dn.getHasReminder()?"[*]\n":""+dn.getNote();
        }
        return "";
    }

    private void changeDate(int day){
        State st=State.getInstance();
		ChronoLocalDate newDate=this.dateHandler.of(
			this.dateHandler.getYMD()[0],
			this.dateHandler.getYMD()[1],
            day
		);
		st.setChronoLocalDate(newDate); // inform the state about it
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
