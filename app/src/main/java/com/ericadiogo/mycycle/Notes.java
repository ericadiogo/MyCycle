package com.ericadiogo.mycycle;

import java.sql.Time;
import java.util.Date;

public class Notes {
    private String noteText, noteDay;

    public Notes(String noteText, String noteDay) {
        this.noteText = noteText;
        this.noteDay = noteDay;
    }

    public Notes(){

    }

    public String getNoteText() {
        return noteText;
    }

    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }

    public String getNoteDay() {
        return noteDay;
    }

    public void setNoteDay(String noteDay) {
        this.noteDay = noteDay;
    }
}
