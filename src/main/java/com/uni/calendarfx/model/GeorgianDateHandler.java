package com.uni.calendarfx.model;

import java.time.LocalDate;

import java.time.chrono.ChronoLocalDate;

import java.time.format.DateTimeFormatter;

import java.util.Locale;

public class GeorgianDateHandler extends DateHandler{

    private LocalDate ld;

    public GeorgianDateHandler(ChronoLocalDate cld){
        this.ld=LocalDate.from(cld);
    }

    @Override
    public String getMonthName(){
        return DateTimeFormatter.ofPattern("MMMM").format(ld);
    }

    @Override
    public int getMonthLength(){
        return this.ld.lengthOfMonth();
    }

    @Override
    public int getYearLength(){
        return this.ld.lengthOfYear();
    }

    @Override
    public int[] getYMD(){
        int[] ymd={
            ld.getYear(),
            ld.getMonthValue(),
            ld.getDayOfMonth()
        };
        return ymd;
    }

    @Override
    public int getStartYear(){ return 2024;}

    @Override
    public ChronoLocalDate of(int y,int m,int d){
        return this.ld.of(y,m,d);
    }

    @Override
    public LocalDate getLocalDate(){
        return this.ld;
    }

}