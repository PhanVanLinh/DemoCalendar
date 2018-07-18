package com.example.nguyenminhduong.democalendar

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import com.example.nguyenminhduong.democalendar.adapter.HolidayCalendarAdapter
import com.example.nguyenminhduong.democalendar.model.HolidayCalendar
import com.example.nguyenminhduong.democalendar.model.HolidayCalendarDate
import com.example.nguyenminhduong.democalendar.widget.holidaycalendar.HolidayDateClickListener

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val adapter = HolidayCalendarAdapter(this)
    val recylerView = findViewById<RecyclerView>(R.id.recylerview)
    recylerView.layoutManager = LinearLayoutManager(this)
    recylerView.adapter = adapter
    adapter.updateData(getListHoliday())
    adapter.setHolidayDateClickListener {
      Toast.makeText(applicationContext,it.dateString,Toast.LENGTH_SHORT).show()
    }

  }

  fun getListHoliday(): List<HolidayCalendar> {
    val listHolidayCalendar = arrayListOf<HolidayCalendar>()
    val listHolidayCalendarDate = arrayListOf<HolidayCalendarDate>()
    listHolidayCalendarDate.add(HolidayCalendarDate("2018-01-20"))
    listHolidayCalendarDate.add(HolidayCalendarDate("2018-01-21"))
    listHolidayCalendarDate.add(HolidayCalendarDate("2018-01-22"))
    for (i in 1..12) {
      listHolidayCalendar.add(HolidayCalendar(i, 2018, listHolidayCalendarDate))
    }
    return listHolidayCalendar
  }
}
