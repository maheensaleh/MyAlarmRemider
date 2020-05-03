package com.example.myalarmreminder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.media.MediaPlayer;
import android.os.Bundle;


import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.provider.CalendarContract;
//import android.support.design.widget.FloatingActionButton;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
//import

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

  private FloatingActionButton add;
  private Dialog dialog;
  private RecyclerView recyclerView;
  private List<CalendarContract.Reminders> temp;
  private TextView empty;
  Date remind_date ;  // remind Date variable to save in database
  Date original_date ;  // original Date variable to save in database



  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    add = findViewById(R.id.floatingButton);
    add.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        addReminder();
      }
    });

;

  }

  public void addReminder(){

    dialog = new Dialog(MainActivity.this);
    dialog.setContentView(R.layout.floating_popup);

    final TextView textView = dialog.findViewById(R.id.date);
    Button select,add;
    select = dialog.findViewById(R.id.selectDate);
    add = dialog.findViewById(R.id.addButton);
    final EditText message = dialog.findViewById(R.id.message);



    // -------------------------------   setting date and time picker together -------------------------//
    final Calendar newCalender = Calendar.getInstance();
    select.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        DatePickerDialog dialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
          @Override
          public void onDateSet(DatePicker view, final int year, final int month, final int dayOfMonth) {

            final Calendar newDate = Calendar.getInstance();
            Calendar newTime = Calendar.getInstance();
            TimePickerDialog time = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
              @Override
              public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                newDate.set(year,month,dayOfMonth,hourOfDay,minute,0);
                Calendar tem = Calendar.getInstance();
                Log.w("TIME",System.currentTimeMillis()+"");
                if(newDate.getTimeInMillis()-tem.getTimeInMillis()>0){

                  textView.setText(newDate.getTime().toString());

                  original_date = newDate.getTime();  // original  date and time saved in Date variable


                  Calendar prev_date = Calendar.getInstance(); // calendar to save previuos date
                  prev_date.setTime(original_date); //
                  prev_date.add(newDate.DATE,-1); //get previous date from  original date
                  remind_date = prev_date.getTime();

                }

                else{
                  Toast.makeText(MainActivity.this,"Invalid time",Toast.LENGTH_SHORT).show();

              }}
            },newTime.get(Calendar.HOUR_OF_DAY),newTime.get(Calendar.MINUTE),true);
            time.show();

          }
        },newCalender.get(Calendar.YEAR),newCalender.get(Calendar.MONTH),newCalender.get(Calendar.DAY_OF_MONTH));
        dialog.getDatePicker().setMinDate(System.currentTimeMillis());
        dialog.show();


        //

      }
    });



    //setting alarm
    add.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
//        alarm date and time:
        System.out.println("orginal date is "+original_date);
        System.out.println("remind date is "+remind_date);

        Reminders reminders = new Reminders();
        reminders.setMessage(message.getText().toString().trim());
        Date remind = new Date(textView.getText().toString().trim());
        reminders.setRemindDate(remind);


        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+5:30"));
        calendar.setTime(remind);
        calendar.set(Calendar.SECOND,0);
        Intent intent = new Intent(MainActivity.this,NotifierAlarm.class);
        intent.putExtra("Message",reminders.getMessage());
        intent.putExtra("RemindDate",reminders.getRemindDate().toString());
        intent.putExtra("id",reminders.getId());
        PendingIntent intent1 = PendingIntent.getBroadcast(MainActivity.this,reminders.getId(),intent,PendingIntent.FLAG_UPDATE_CURRENT);
//        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
//        alarmManager.setExact(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),intent1);

        Toast.makeText(MainActivity.this,"Inserted Successfully",Toast.LENGTH_SHORT).show();
        setItemsInRecyclerView();
//        AppDatabase.destroyInstance();
        dialog.dismiss();

      }
    });


    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    dialog.show();

  }

  public void setItemsInRecyclerView(){

  }


}
