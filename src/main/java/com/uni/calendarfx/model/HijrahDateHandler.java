package com.uni.calendarfx.model;

import java.time.chrono.ChronoLocalDate;
import java.time.chrono.HijrahDate;

import java.time.format.DateTimeFormatter;

import java.util.Locale;

import java.time.LocalDate;

public class HijrahDateHandler extends DateHandler{

    private HijrahDate hd;

    public HijrahDateHandler(ChronoLocalDate cld){
        this.hd=HijrahDate.from(cld);
    }

    @Override
    public String getMonthName(){
        return DateTimeFormatter.ofPattern("MMMM",new Locale("ar")).format(hd);
    }

    @Override
    public int getMonthLength(){
        return this.hd.lengthOfMonth();
    }

    @Override
    public int getYearLength(){
        return this.hd.lengthOfYear();
    }

    @Override
    public int[] getYMD(){
        String[] s=DateTimeFormatter.ofPattern("YYYY-MM-DD").format(hd).split("-");
        int[] ymd=new int[3];
        for(int i=0;i<s.length;i++)
            ymd[i]=Integer.parseInt(s[i]);
        return ymd;
    }

    @Override
    public int getStartYear(){return 1453;}

    @Override
    public ChronoLocalDate of(int y,int m,int d){
        return this.hd.of(y,m,d);
    }

    @Override
    public LocalDate getLocalDate(){
        return LocalDate.from(this.hd);
    }

}