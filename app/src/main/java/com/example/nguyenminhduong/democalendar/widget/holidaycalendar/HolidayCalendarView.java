package com.example.nguyenminhduong.democalendar.widget.holidaycalendar;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import com.example.nguyenminhduong.democalendar.R;
import com.example.nguyenminhduong.democalendar.model.HolidayCalendarDate;
import com.example.nguyenminhduong.democalendar.utils.DateTimeUtils;
import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HolidayCalendarView extends View {
    private int daySelectedCircleSize;
    private int dayNumberTextSize;
    private int dayStringTextSize;
    private int monthHeaderSize;
    private int monthStringTextSize;
    private int monthNumberTextSize;
    private int numRows;

    private Paint dayNumberPaint;
    private Paint dayStringPaint;
    private Paint monthStringPaint;
    private Paint monthNumberPaint;
    private Paint selectedCirclePaint;

    private int weekStart;
    private int numDays = 7;
    private int numCells;
    private int dayOfWeekStart;
    private int height;
    private int width;
    private int year;
    private int month;

    private Calendar calendar;

    private DateFormatSymbols mDateFormatSymbols = new DateFormatSymbols(Locale.US);

    private HolidayDateClickListener mOnDayClickListener;

    private List<HolidayCalendarDate> mHolidayCalendarDates;

    public HolidayCalendarView(Context context) {
        this(context, null);
    }

    public HolidayCalendarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HolidayCalendarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray =
                getContext().obtainStyledAttributes(attrs, R.styleable.HolidayCalendarView);

        Resources resources = context.getResources();
        calendar = Calendar.getInstance(Locale.US);

        dayNumberTextSize =
                typedArray.getDimensionPixelSize(R.styleable.HolidayCalendarView_textSizeDay,
                        resources.getDimensionPixelSize(R.dimen.sp_16));
        dayStringTextSize =
                typedArray.getDimensionPixelSize(R.styleable.HolidayCalendarView_textSizeDay,
                        resources.getDimensionPixelSize(R.dimen.sp_11));
        monthStringTextSize =
                typedArray.getDimensionPixelSize(R.styleable.HolidayCalendarView_textSizeMonth,
                        resources.getDimensionPixelSize(R.dimen.sp_14));
        monthNumberTextSize =
                typedArray.getDimensionPixelSize(R.styleable.HolidayCalendarView_textSizeMonth,
                        resources.getDimensionPixelSize(R.dimen.sp_25));
        monthHeaderSize = typedArray.getDimensionPixelOffset(
                R.styleable.HolidayCalendarView_headerMonthHeight,
                resources.getDimensionPixelOffset(R.dimen.dp_60));
        daySelectedCircleSize =
                typedArray.getDimensionPixelSize(R.styleable.HolidayCalendarView_selectedDayRadius,
                        resources.getDimensionPixelOffset(R.dimen.dp_20));

        height = ((typedArray.getDimensionPixelSize(R.styleable.HolidayCalendarView_calendarHeight,
                resources.getDimensionPixelOffset(R.dimen.dp_300)) - monthHeaderSize) / 5);

        typedArray.recycle();

        initView();
        setBackgroundColor(Color.parseColor("#FFF4F4F4"));
    }

    private void drawMonthTitle(Canvas canvas) {
        int x = width / 2;
        int y = monthHeaderSize;
        canvas.drawText(String.valueOf(month + 1), x, y / 3, monthNumberPaint);
        canvas.drawText(getMonthName(), x, y / 2 + monthNumberTextSize / 2, monthStringPaint);
    }

    private int findDayOffset() {
        return (dayOfWeekStart < weekStart ? (dayOfWeekStart + numDays) : dayOfWeekStart)
                - weekStart;
    }

    private String getMonthName() {
        Date date = calendar.getTime();
        return DateTimeUtils.convertToString(date, DateTimeUtils.DATE_FORMAT_MMMM);
    }

    protected void drawMonthNums(Canvas canvas) {
        int y = (height + dayNumberTextSize) / 3 + monthHeaderSize;
        int paddingDay = width / (2 * numDays);
        int dayOffset = findDayOffset();
        int day = 1;
        while (day <= numCells) {
            int x = paddingDay * (1 + dayOffset * 2);
            calendar.set(year, month, day, 0, 0, 0);
            Date date = new Date(year - 1900, month, day);
            dayNumberPaint.setColor(Color.GRAY);
            dayStringPaint.setColor(Color.GRAY);
            selectedCirclePaint.setColor(Color.TRANSPARENT);
            if (mHolidayCalendarDates != null && mHolidayCalendarDates.size() > 0) {
                for (HolidayCalendarDate holidayCalendarDate : mHolidayCalendarDates) {
                    Date datet = holidayCalendarDate.getDate();
                    if (date.equals(holidayCalendarDate.getDate())) {
                        if (holidayCalendarDate.isSelected()) {
                            dayNumberPaint.setColor(Color.WHITE);
                            dayStringPaint.setColor(Color.WHITE);
                            selectedCirclePaint.setColor(Color.RED);
                        } else {
                            dayNumberPaint.setColor(Color.BLACK);
                            dayStringPaint.setColor(Color.BLACK);
                            selectedCirclePaint.setColor(Color.WHITE);
                        }
                        break;
                    }
                }
            }
            drawDayLabelNomal(canvas, y, day, x);
            dayOffset++;
            if (dayOffset == numDays) {
                dayOffset = 0;
                y += height;
            }
            day++;
        }
    }

    private void drawDayLabelNomal(Canvas canvas, int y, int day, int x) {
        selectedCirclePaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(x, y, daySelectedCircleSize, selectedCirclePaint);
        canvas.drawText(String.format(Locale.US, "%d",
                day > DateTimeUtils.getDaysInMonth(month, year) ? day
                        - DateTimeUtils.getDaysInMonth(month, year) : day), x, y, dayNumberPaint);
        calendar.set(Calendar.DATE, day);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.YEAR, year);

        String dayOfWeek = mDateFormatSymbols.getShortWeekdays()[calendar.get(
                Calendar.DAY_OF_WEEK)].toLowerCase(Locale.US);
        canvas.drawText(dayOfWeek, x, y + dayStringTextSize, dayStringPaint);
    }

    public Date getDayFromLocation(float x, float y) {
        int padding = 10;
        if ((x < padding) || (x > width)) {
            return null;
        }
        int yDay = (int) (y - monthHeaderSize) / height;
        int day = 1
                + ((int) ((x - padding) * numDays / (width - padding)) - findDayOffset())
                + yDay * numDays;
        if (month > 11 || month < 0 || DateTimeUtils.getDaysInMonth(month, year) < day || day < 1) {
            return null;
        }
        return new Date(year - 1900, month, day);
    }

    protected void initView() {
        monthStringPaint = new Paint();
        monthStringPaint.setFakeBoldText(true);
        monthStringPaint.setAntiAlias(true);
        monthStringPaint.setTextSize(monthStringTextSize);
        monthStringPaint.setColor(Color.RED);
        monthStringPaint.setTextAlign(Paint.Align.CENTER);
        monthStringPaint.setStyle(Paint.Style.FILL);

        monthNumberPaint = new Paint();
        monthNumberPaint.setFakeBoldText(true);
        monthNumberPaint.setAntiAlias(true);
        monthNumberPaint.setTextSize(monthNumberTextSize);
        monthNumberPaint.setColor(Color.RED);
        monthNumberPaint.setTextAlign(Paint.Align.CENTER);
        monthNumberPaint.setStyle(Paint.Style.FILL);

        selectedCirclePaint = new Paint();
        selectedCirclePaint.setColor(Color.TRANSPARENT);
        selectedCirclePaint.setFakeBoldText(true);
        selectedCirclePaint.setAntiAlias(true);
        selectedCirclePaint.setTextAlign(Paint.Align.CENTER);
        selectedCirclePaint.setStyle(Paint.Style.FILL);

        dayNumberPaint = new Paint();
        dayNumberPaint.setAntiAlias(true);
        dayNumberPaint.setFakeBoldText(true);
        dayNumberPaint.setTextSize(dayNumberTextSize);
        dayNumberPaint.setStyle(Paint.Style.FILL);
        dayNumberPaint.setTextAlign(Paint.Align.CENTER);
        dayNumberPaint.setFakeBoldText(false);

        dayStringPaint = new Paint();
        dayStringPaint.setAntiAlias(true);
        dayStringPaint.setFakeBoldText(true);
        dayStringPaint.setTextSize(dayStringTextSize);
        dayStringPaint.setStyle(Paint.Style.FILL);
        dayStringPaint.setTextAlign(Paint.Align.CENTER);
        dayStringPaint.setFakeBoldText(false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawMonthTitle(canvas);
        drawMonthNums(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec),
                height * numRows + monthHeaderSize + monthHeaderSize / 8);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        width = w;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            performClick();
            Date date = getDayFromLocation(event.getX(), event.getY());
            if (date == null) {
                return false;
            }
            for (HolidayCalendarDate holidayCalendarDate : mHolidayCalendarDates) {
                if (date.equals(holidayCalendarDate.getDate()) && mOnDayClickListener != null) {
                    changeSelectedDay(holidayCalendarDate);
                    mOnDayClickListener.onDayClick(holidayCalendarDate);
                    invalidate();
                }
            }
        }
        return true;
    }

    private void changeSelectedDay(HolidayCalendarDate selectedDay) {
        for (HolidayCalendarDate holidayCalendarDate : mHolidayCalendarDates) {
            if (holidayCalendarDate.isSelected()) {
                holidayCalendarDate.setSelected(false);
                mOnDayClickListener.onDayClick(holidayCalendarDate);
            }
        }
        selectedDay.setSelected(true);
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    private int calculateNumRows() {
        int offset = findDayOffset();
        int dividend = (offset + numCells) / numDays;
        int remainder = (offset + numCells) % numDays;
        return (dividend + (remainder > 0 ? 1 : 0));
    }

    public void reuse() {
        numRows = calculateNumRows();
        requestLayout();
    }

    public void setTime(int month, int year) {
        this.month = month - 1;
        this.year = year;
        calendar.set(Calendar.MONTH, this.month);
        calendar.set(Calendar.YEAR, this.year);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        dayOfWeekStart = calendar.get(Calendar.DAY_OF_WEEK);
        weekStart = calendar.getFirstDayOfWeek();
        numCells = DateTimeUtils.getDaysInMonth(this.month, this.year);
        numRows = calculateNumRows();
    }

    public void setOnDayClickListener(HolidayDateClickListener onDayClickListener) {
        mOnDayClickListener = onDayClickListener;
    }

    public void setHolidayCalendarDates(List<HolidayCalendarDate> holidayCalendarDates) {
        mHolidayCalendarDates = holidayCalendarDates;
    }
}
