package com.uni.calendarfx.model;

import java.time.chrono.ChronoLocalDate;
import java.time.chrono.HijrahDate;

import java.time.format.DateTimeFormatter;

import java.util.Locale;

import java.time.LocalDate;

import org.bardframework.time.LocalDateJalali;

public class JalaliDateHandler extends DateHandler{

    private LocalDateJalali ldj;
    private static final String[] MONTHS={
        "فروردین",
        "اردیبهشت",
        "خرداد",
        "تیر",
        "مرداد",
        "شهریور",
        "مهر",
        "آبان",
        "آذر", 
        "دی",
        "بهمن",
        "اسفند"
    };

    public JalaliDateHandler(ChronoLocalDate cld){
        this.ldj=LocalDateJalali.from(cld);
    }

    @Override
    public String getMonthName(){
        return MONTHS[this.getYMD()[1]-1];
    }

    @Override
    public int getMonthLength(){
        return this.ldj.lengthOfMonth();
    }

    @Override
    public int getYearLength(){
        return this.ldj.lengthOfYear();
    }

    @Override
    public int[] getYMD(){
       int[] ymd={
        ldj.getYear(),
        ldj.getMonthValue(),
        ldj.getDayOfMonth()
       };

       return ymd;
    }

    @Override
    public int getStartYear(){return 1402;}

    @Override
    public ChronoLocalDate of(int y,int m,int d){
        return this.ldj.of(y,m,d);
    }

    @Override
    public LocalDate getLocalDate(){
		return this.ldj.toLocalDate();
    }

}
