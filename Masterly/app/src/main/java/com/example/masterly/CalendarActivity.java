package com.example.masterly;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CalendarActivity extends AppCompatActivity {

    CompactCalendarView compactCalendar;
    private SimpleDateFormat dateFormatMonth = new SimpleDateFormat("MMMM- yyyy", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        Intent intent = getIntent();

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setTitle("January- 2018");

        compactCalendar = (CompactCalendarView) findViewById(R.id.compactcalendar_view);
        compactCalendar.setUseThreeLetterAbbreviation(true);

        //Set an event - Nav's birthday - January 15
        Event ev1 = new Event(Color.GREEN, 1433701251000L, "Nav's 22nd Birthday");
        compactCalendar.addEvent(ev1);

        compactCalendar.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                Context context = getApplicationContext();

                final String dateChosen = dateClicked.toString();

                if(CalendarDatesList.eventList.containsKey(dateChosen)){
                    TextView currentEvents = (TextView) findViewById(R.id.currentEventsText);
                    currentEvents.setText(CalendarDatesList.eventList.get(dateChosen));
                }else{
                    TextView currentEvents = (TextView) findViewById(R.id.currentEventsText);
                    currentEvents.setText("No events planned.");
                }

                Button createButton = (Button) findViewById(R.id.addEventButton);
                final EditText newEventText = (EditText) findViewById(R.id.addEventEditText);

                createButton.setOnClickListener(
                        new View.OnClickListener(){
                            public void onClick(View view){
                                String eventText = newEventText.getText().toString();
                                CalendarDatesList.eventList.put(dateChosen, eventText);
                                newEventText.setText("");
                            }
                        }
                );
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                actionBar.setTitle(dateFormatMonth.format(firstDayOfNewMonth));
                TextView currentEvents = (TextView) findViewById(R.id.currentEventsText);
                currentEvents.setText(CalendarDatesList.eventList.get(" "));
            }

        });

    }
}
