package com.uni.calendarfx.control;

import com.uni.calendarfx.model.Observer;

import javafx.fxml.Initializable;
import javafx.fxml.FXML;

import java.net.URL;

import java.util.ResourceBundle;

import com.uni.calendarfx.Events;

public class MainController implements Controller, Initializable, Observer{

	public void initialize(URL url,ResourceBundle resourceBundle){
		// here you do what's necessary when the Controller is loaded	
	}

	@Override
	public void update(String msg,Object data){
		
	}

}
