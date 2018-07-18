package com.example.nguyenminhduong.democalendar.adapter;

import android.databinding.BaseObservable;
import com.example.nguyenminhduong.democalendar.model.HolidayCalendar;
import com.example.nguyenminhduong.democalendar.model.HolidayCalendarDate;
import com.example.nguyenminhduong.democalendar.widget.holidaycalendar.HolidayDateClickListener;
import java.util.List;

/**
 * Created by Duong on 17/07/2018.
 */
public class ItemHolidayCalendarViewModel extends BaseObservable {
    private HolidayCalendar mHolidayCalendar;
    private HolidayDateClickListener mHolidayDateClickListener;

    public ItemHolidayCalendarViewModel(HolidayCalendar holidayCalendar,
            HolidayDateClickListener holidayDateClickListener) {
        mHolidayCalendar = holidayCalendar;
        mHolidayDateClickListener = holidayDateClickListener;
    }

    public int getYear() {
        return mHolidayCalendar.getYear();
    }

    public int getMonth() {
        return mHolidayCalendar.getMonth();
    }

    public List<HolidayCalendarDate> getListHolidayDate() {
        return mHolidayCalendar.getHolidayCalendarDates();
    }

    public void onDayClicked(HolidayCalendarDate holidayCalendarDate) {
        if (mHolidayDateClickListener == null) {
            return;
        }
        mHolidayDateClickListener.onDayClick(holidayCalendarDate);
    }
}
