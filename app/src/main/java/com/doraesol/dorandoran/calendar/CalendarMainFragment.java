package com.doraesol.dorandoran.calendar;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.doraesol.dorandoran.R;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.google.android.gms.internal.zzt.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class CalendarMainFragment extends Fragment {

    final String LOG_TAG = CalendarMainFragment.class.getSimpleName();
    CompactCalendarView compactCalendarView;
    Calendar currentCalender = Calendar.getInstance(Locale.KOREAN);
    private SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("YYYY년 MM월", Locale.KOREAN);
    @BindView(R.id.iv_calendar_month_prev)  ImageView iv_calendar_moth_prev;
    @BindView(R.id.iv_calendar_month_next)  ImageView getIv_calendar_moth_next;
    @BindView(R.id.tv_calendar_month)       TextView tv_calendar_month;
    @BindView(R.id.lv_calendar_schedule)    ListView lv_calendar_schedule;

    public CalendarMainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final List<String> scheduleList = new ArrayList<>();
        final View rootView = inflater.inflate(R.layout.fragment_calendar_main, container, false);
        ButterKnife.bind(this, rootView);
        compactCalendarView = (CompactCalendarView)rootView.findViewById(R.id.compactcalendar_view);
        compactCalendarView.setLocale(TimeZone.getDefault(), Locale.KOREAN);

        //compactCalendarView.setFirstDayOfWeek(Calendar.MONDAY);


        //Date date = new Date(System.currentTimeMillis());
        //compactCalendarView.setCurrentDate(date);

        currentCalender.set(Calendar.ERA, GregorianCalendar.AD);
        currentCalender.set(Calendar.YEAR, 2017);
        currentCalender.set(Calendar.MONTH, Calendar.APRIL);
        currentCalender.add(Calendar.DATE, 5);

        Event event = new Event(Color.BLUE, currentCalender.getTimeInMillis(), "저장할 데이터");
        compactCalendarView.addEvent(event, true);
        //compactCalendarView.invalidate();

        //addEvents(Calendar.APRIL, 2017);


        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {

            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                tv_calendar_month.setText(dateFormatForMonth.format(firstDayOfNewMonth));
            }
        });

        return rootView;
    }

    @OnClick({R.id.iv_calendar_month_next, R.id.iv_calendar_month_prev})
    public void OnTitleImageButtonClicked(View view){
        switch (view.getId()){
            case R.id.iv_calendar_month_prev:
                compactCalendarView.showPreviousMonth();
                break;
            case R.id.iv_calendar_month_next:
                compactCalendarView.showNextMonth();
                break;
        }
    }

    private void addEvents(int month, int year) {

        currentCalender.setTime(new Date());
        currentCalender.set(Calendar.DAY_OF_MONTH, 1);
        Date firstDayOfMonth = currentCalender.getTime();

        for (int i = 0; i < 5; i++) {

            currentCalender.setTime(firstDayOfMonth);

            if (month > -1) {
                currentCalender.set(Calendar.MONTH, month);
            }

            if (year > -1) {
                currentCalender.set(Calendar.ERA, GregorianCalendar.AD);
                currentCalender.set(Calendar.YEAR, year);
            }
            currentCalender.add(Calendar.DATE, i);

            //setToMidnight(currentCalender);

            long timeInMillis = currentCalender.getTimeInMillis();

            List<Event> events = getEvents(timeInMillis, i);

            compactCalendarView.addEvents(events);
        }
    }

    private List<Event> getEvents(long timeInMillis, int day) {

        return Arrays.asList(   new Event(Color.argb(255, 169, 68, 65), timeInMillis, "스케쥴 : " + new Date(timeInMillis)),
                                new Event(Color.argb(255, 169, 68, 65), timeInMillis, "스케쥴 : " + new Date(timeInMillis)));

    }

    private void setToMidnight(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }


}
