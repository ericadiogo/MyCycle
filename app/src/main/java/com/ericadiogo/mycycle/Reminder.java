package com.ericadiogo.mycycle;

public class Reminder {
    private String title;
    private String dateTime;
    private String repetitionInfo;
    private String reminderId;

    public Reminder() {
    }

    public Reminder(String title, String dateTime, String repetitionInfo) {
        this.title = title;
        this.dateTime = dateTime;
        this.repetitionInfo = repetitionInfo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getRepetitionInfo() {
        return repetitionInfo;
    }

    public void setRepetitionInfo(String repetitionInfo) {
        this.repetitionInfo = repetitionInfo;
    }

    public String getReminderId() {
        return reminderId;
    }

    public void setReminderId(String reminderId) {
        this.reminderId = reminderId;
    }
}
