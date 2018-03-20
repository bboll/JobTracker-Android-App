package com.brianboll.jobtracker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

class Job
{
    int ID;
    String jobName, contactName, contactPhone, address, scope, date, time_in, time_out;

    Job() { ID = 0; jobName = ""; date = ""; time_in = ""; time_out = ""; }

    String formatDate(String date)
    {
        Date JobDate = new Date();
        SimpleDateFormat newDateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
        try {
            JobDate = newDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return "Invalid";
        }
        newDateFormat.applyPattern("EEE.\nMMM. d");
        String DateString = newDateFormat.format(JobDate);

        return DateString;
    }
}
