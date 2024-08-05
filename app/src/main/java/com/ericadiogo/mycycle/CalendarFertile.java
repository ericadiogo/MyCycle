package com.ericadiogo.mycycle;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import android.util.Log;

public class CalendarFertile {
    private static final String TAG = "CalendarDate";

    public static Collection<CalendarDay> getFertileDates(Date startDate, int periodLength, int cycleLength) {
        if (startDate == null) {
            throw new IllegalArgumentException("startDate cannot be null");
        }

        List<CalendarDay> dates = new ArrayList<>();
        List<CalendarDay> additionalDates = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.add(Calendar.DAY_OF_MONTH,(int)((cycleLength/2)-3));

        for (int i = 0; i < 7  ; i++) {
            CalendarDay calendarDay = CalendarDay.from(calendar.getTime());
            dates.add(calendarDay);
            Log.d(TAG, "Adding date: " + calendarDay.getDate());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        for (CalendarDay date : dates) {
            Calendar calendarTemp = Calendar.getInstance();
            calendarTemp.setTime(date.getDate());
            calendarTemp.add(Calendar.DAY_OF_MONTH, cycleLength);
            additionalDates.add(CalendarDay.from(calendarTemp.getTime()));
        }
        dates.addAll(additionalDates);

        for (CalendarDay date : dates) {
            Calendar calendarTemp = Calendar.getInstance();
            calendarTemp.setTime(date.getDate());
            calendarTemp.add(Calendar.DAY_OF_MONTH, cycleLength);
            additionalDates.add(CalendarDay.from(calendarTemp.getTime()));
        }
        dates.addAll(additionalDates);

        for (CalendarDay date : dates) {
            Calendar calendarTemp = Calendar.getInstance();
            calendarTemp.setTime(date.getDate());
            calendarTemp.add(Calendar.DAY_OF_MONTH, cycleLength);
            additionalDates.add(CalendarDay.from(calendarTemp.getTime()));
        }
        dates.addAll(additionalDates);

        return dates;
    }
}