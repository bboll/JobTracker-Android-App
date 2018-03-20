package com.brianboll.jobtracker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

class JobDetail {
    String detail, label;

    JobDetail() { detail = ""; label = ""; }

    JobDetail(String label, String detail){
        this.detail = detail;
        this.label = label;
    }

    String formatDate(String date)
    {
        Date JobDate = new Date();
        SimpleDateFormat newDateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");

        int day = valiDate(date);

        try {
            JobDate = newDateFormat.parse(date);
        } catch (ParseException e) {
            //TODO: Implement fallback parsing method independent of SimpleDateFormat
            //Throwable.printStackTrace for testing only
            e.printStackTrace();
        }
        if(day<11 && day>13 && day!=-1) {
            switch (day % 10) {
                case 1:
                    newDateFormat.applyPattern("EEE., MMM. d'st' hh:mm aaa");
                    break;

                case 2:
                    newDateFormat.applyPattern("EEE., MMM. d'nd' hh:mm aaa");
                    break;

                case 3:
                    newDateFormat.applyPattern("EEE., MMM. d'rd' hh:mm aaa");
                    break;

                default:
                    newDateFormat.applyPattern("EEE., MMM. d'th' hh:mm aaa");
                    break;

            }
        }
        else if (day==-1){ return "Invalid date.\nPlease contact supervisor."; }
        else { newDateFormat.applyPattern("EEE., MMM. d'th' hh:mm aaa"); }
        String DateString = newDateFormat.format(JobDate);

        return DateString;
    }

    private int valiDate(String date) {
        if (Pattern.matches("[a-zA-Z]+", date) == false && date.length() > 12) {
            try {
                String parts[] = date.split("[\\W]");
                int day = Integer.parseInt(parts[2]);

                return day;
            } catch (NumberFormatException e) {
                return -1;
            }
        }
        else { return -1; }
    }
}
