package com.example.myalarmreminder;


//import android.arch.persistence.room.Entity;
//import android.arch.persistence.room.PrimaryKey;
//import android.support.annotation.NonNull;

import java.util.Date;

//@Entity(tableName = "reminder")
public class Reminders {

//    @PrimaryKey(autoGenerate = true)
//    @NonNull
    public int id;

    String message;
    Date remindDate;

    public String getMessage() {
        return message;
    }

    public Date getRemindDate() {
        return remindDate;
    }

    public int getId() {
        return id;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setRemindDate(Date remindDate) {
        this.remindDate = remindDate;
    }

    public void setId(int id) {
        this.id = id;
    }
}
