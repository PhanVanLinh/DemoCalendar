package com.example.nguyenminhduong.democalendar.model;

import java.util.List;

/**
 * Created by minhd on 6/28/2017.
 */

public class HolidayCalendar {
    private int mMonth;
    private int mYear;
    private List<HolidayCalendarDate> mHolidayCalendarDates;

    public HolidayCalendar(int month, int year, List<HolidayCalendarDate> holidayCalendarDates) {
        mMonth = month;
        mYear = year;
        mHolidayCalendarDates = holidayCalendarDates;
    }

    public int getMonth() {
        return mMonth;
    }

    public void setMonth(int month) {
        mMonth = month;
    }

    public int getYear() {
        return mYear;
    }

    public void setYear(int year) {
        mYear = year;
    }

    public List<HolidayCalendarDate> getHolidayCalendarDates() {
        return mHolidayCalendarDates;
    }

    public void setHolidayCalendarDates(List<HolidayCalendarDate> holidayCalendarDates) {
        mHolidayCalendarDates = holidayCalendarDates;
    }
}
