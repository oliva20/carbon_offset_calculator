package com.example.offsetcalculator.model.emission;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

//This object will return the date now in string instead
public class CreateDate {
    private String DATE_PATTERN = "dd/MM/yyyy";

    public String now(){
        DateFormat df = new SimpleDateFormat(DATE_PATTERN);
        Date today = Calendar.getInstance().getTime();
        return df.format(today);
    }
}
