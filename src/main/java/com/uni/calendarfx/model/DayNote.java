package com.uni.calendarfx.model;

import java.io.Serializable;

import java.time.LocalDate;

import java.time.format.DateTimeFormatter;

import com.uni.calendarfx.io.FileUtils;

import com.uni.calendarfx.App;

import java.io.IOException;


/**
 * This is a class purely to be (de)serialized and display the notes of users,
 * when the according date is selected. name of the file will be "date.toString()"
 * value, so that searching for it is easier, when trying to read it from filesystem
 * 
 * Note that this class uses LocalDate to make conversion and serialazions and display
 * of those days easier and more universal
 */
public class DayNote implements Serializable{
    
    private static final long serialVersionUID = 1L; // for serialization

    public static boolean serialize(DayNote dn){
        String filename=App.serDirectoryName+System.getProperty("file.separator")+dn.getSerializedFileName()+".ser";
        System.out.println("serializing '"+filename+"'");
        try{
            FileUtils.serialize(filename,dn);
            return true;
        }catch(IOException ioe){
            System.err.println("Could not serialize 'DayNote' object");
        }
        return false;
    }

    public static DayNote deserialize(String filename){
        String relPath=App.serDirectoryName+System.getProperty("file.separator")+filename+".ser";
        DayNote dn=null;
        try{
            dn=(DayNote)FileUtils.deserialize(relPath);
        }catch(IOException | ClassNotFoundException exp){
            System.err.println("Could not deserialize '"+relPath+"'");
        }
        System.err.println("================= SERIALIZED: '"+relPath+"'");
        return dn;
    }

    public static String getSerFilePath(LocalDate ld){
        return String.valueOf(ld.getYear())+"-"+String.valueOf(ld.getMonthValue())+"-"+String.valueOf(ld.getDayOfMonth());
    }

    // ----------------------------------------------- MEMBERS ------------------------------------------->

    private LocalDate ld;
    private boolean hasReminder;
    private String note;

    public DayNote(LocalDate ld,boolean hasReminder,String note){
        this.ld=ld;
        this.hasReminder=hasReminder;
        this.note=note;
    }

    public String getSerializedFileName(){ // without the '.ser' part at the end
        return getSerFilePath(ld);
    }

    public LocalDate getLocalDate(){return ld;}
    public boolean getHasReminder(){return hasReminder;}
    public String getNote(){return note;}
}
