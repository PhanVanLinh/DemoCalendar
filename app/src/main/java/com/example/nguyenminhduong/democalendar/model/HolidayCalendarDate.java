package com.example.nguyenminhduong.democalendar.model;

import com.example.nguyenminhduong.democalendar.utils.DateTimeUtils;
import java.util.Date;

/**
 * Created by minhd on 6/28/2017.
 */

public class HolidayCalendarDate {
    private String mDate;
    private boolean selected;

    public HolidayCalendarDate(String date) {
        mDate = date;
    }

    public Date getDate() {
        return DateTimeUtils.convertStringToDate(mDate, DateTimeUtils.DATE_FORMAT_YYYY_MM_DD_2);
    }

    public String getDateString() {
        return mDate;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
