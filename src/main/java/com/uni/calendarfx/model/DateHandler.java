package com.uni.calendarfx.model;

import java.time.chrono.ChronoLocalDate;

import java.time.LocalDate;

/**
 * The purpose of this class is to provide easy operations 
 * with ChronoLocalDate instances
 * */ 
public abstract class DateHandler{

    // 0->Y , 1->M, 2->D
    public abstract int[] getYMD();
    public abstract String getMonthName();
    public abstract int getMonthLength();
    public abstract int getYearLength();

    // start year for showing in the year drop-down box
    public abstract int getStartYear();

    // for getting a new value, based on it being hejri,etc...
    public abstract ChronoLocalDate of(int y,int m,int d);

    // sutiable for saving the notes a user takes, in a Day object and then serialize it to filesystem
    public abstract LocalDate getLocalDate();
}