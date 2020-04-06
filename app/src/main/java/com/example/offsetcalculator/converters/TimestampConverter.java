package com.example.offsetcalculator.converters;

import android.net.ParseException;

import androidx.room.TypeConverter;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class TimestampConverter {
    static DateFormat df = new SimpleDateFormat("HH:mm:ss");

    @TypeConverter
    public static Date fromTimestamp(String value) {
        if (value != null) {
            try {
                return (Date) df.parse(value);
            } catch (ParseException | java.text.ParseException e) {
                e.printStackTrace();
            }
            return null;
        } else {
            return null;
        }
    }
}