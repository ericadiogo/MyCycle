package com.ericadiogo.mycycle;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import android.util.Log;

public class CalendarDate {
    private static final String TAG = "CalendarDate";

    public static Collection<CalendarDay> getPeriodDates(Date startDate, int periodLength, int cycleLength) {
        if (startDate == null) {
            throw new IllegalArgumentException("startDate cannot be null");
        }

        List<CalendarDay> dates = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);

        for (int i = 0; i < periodLength ; i++) {
            CalendarDay calendarDay = CalendarDay.from(calendar.getTime());
            dates.add(calendarDay);
            Log.d(TAG, "Adding date: " + calendarDay.getDate());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        return dates;
    }
}
