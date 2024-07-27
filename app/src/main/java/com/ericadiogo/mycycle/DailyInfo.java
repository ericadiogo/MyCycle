package com.ericadiogo.mycycle;

import java.io.Serializable;

public class DailyInfo implements Serializable {
    private String date;
    private Boolean perStart;

    public DailyInfo(String date, Boolean perStart) {
        this.date = date;
        this.perStart = perStart;
    }

    public DailyInfo(){

    }

    public DailyInfo(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Boolean getPerStart() {
        return perStart;
    }

    public void setPerStart(Boolean perStart) {
        this.perStart = perStart;
    }
}