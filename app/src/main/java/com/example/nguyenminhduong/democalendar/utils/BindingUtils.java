package com.example.nguyenminhduong.democalendar.utils;

import android.databinding.BindingAdapter;
import com.example.nguyenminhduong.democalendar.model.HolidayCalendarDate;
import com.example.nguyenminhduong.democalendar.widget.holidaycalendar.HolidayCalendarView;
import com.example.nguyenminhduong.democalendar.widget.holidaycalendar.HolidayDateClickListener;
import java.util.List;

/**
 * Created by Duong on 17/07/2018.
 */
public class BindingUtils {
    private BindingUtils() {
        // No-op
    }

    @BindingAdapter({ "holidayCalendarDates", "month", "year" })
    public static void setHolidayCalendarDates(HolidayCalendarView holidayCalendarView,
            List<HolidayCalendarDate> holidayCalendarDates, int month, int year) {
        holidayCalendarView.setTime(month, year);
        holidayCalendarView.reuse();
        holidayCalendarView.invalidate();
        holidayCalendarView.setHolidayCalendarDates(holidayCalendarDates);
    }

    @BindingAdapter({ "onHolidayDayClick" })
    public static void setHolidayOnDayClick(HolidayCalendarView holidayCalendarView,
            HolidayDateClickListener onDayClickListener) {
        holidayCalendarView.setOnDayClickListener(onDayClickListener);
    }
}
