package com.ericadiogo.mycycle;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.style.ForegroundColorSpan;
import android.util.Log;

import java.util.Collection;
import java.util.HashSet;

public class EventDecorator implements DayViewDecorator {

    private static final String TAG = "EventDecorator";
    private final Drawable drawable;
    private final HashSet<CalendarDay> dates;
    private final int textColor;

    public EventDecorator(Drawable drawable, Collection<CalendarDay> dates, int textColor) {
        this.drawable = drawable;
        this.dates = new HashSet<>(dates);
        this.textColor = textColor;
        for (CalendarDay date : dates) {
            Log.d(TAG, "Decorating date: " + date.getDate()); // Log para cada data decorada
        }
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return dates.contains(day);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setBackgroundDrawable(drawable);
        view.addSpan(new ForegroundColorSpan(textColor)); // Adiciona a mudança de cor do texto
    }
}
