package com.ericadiogo.mycycle;

import android.util.Log;

import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class CalendarFertile {
    private static final String TAG = "CalendarDate";

    public static Collection<CalendarDay> getPeriodDates(Date startDate, int periodLength, int cycleLength) {
        List<CalendarDay> dates = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();

        for (int j = 0; j < periodLength; j++) {
            CalendarDay calendarDay = CalendarDay.from(calendar.getTime());
            dates.add(calendarDay);
            Log.d(TAG, "Adding date: " + calendarDay.getDate());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        return dates;
    }
}
