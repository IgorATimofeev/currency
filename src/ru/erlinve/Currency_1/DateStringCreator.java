package ru.erlinve.Currency_1;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by sebastian on 10/20/15.
 */
public class DateStringCreator {

    private static final String TAG = DateStringCreator.class.getName();
    private static final String DATE_FORMAT_URL = "dd/MM/yyyy";
    private static final String DATE_FORMAT_TITLE = "dd MMM yy";

    private String DATE_URL;
    private String DATE_TITLE;

    private SimpleDateFormat dateFormatTitle;
    private SimpleDateFormat dateFormatUrl;
    private Calendar calendar;

    DateStringCreator()
    {
        dateFormatTitle = new SimpleDateFormat(DATE_FORMAT_TITLE);
        dateFormatUrl = new SimpleDateFormat(DATE_FORMAT_URL);
        calendar = Calendar.getInstance();

        DATE_URL = this.getCurrentDateUrl();
        DATE_TITLE = this.getCurrentDateTitle();
    }

    private String getCurrentDateTitle()
    {
        return dateFormatTitle.format(new Date());
    }
    private String getCurrentDateUrl()
    {
        return dateFormatUrl.format(new Date());
    }

    protected String getDateFormatUrl() {

        return DATE_URL;
    }

    protected String getDateFormatTitle() {
        return DATE_TITLE;
    }

    protected void setDate(int year, int month, int day) {

        calendar.set(year, month, day);

        DATE_TITLE = dateFormatTitle.format(calendar.getTime());
        DATE_URL = dateFormatUrl.format(calendar.getTime());
    }

    protected void setDate(String dateUrlString) {

        try {
            calendar.setTime(dateFormatUrl.parse(dateUrlString));

            DATE_TITLE = dateFormatTitle.format(calendar.getTime());
            DATE_URL = dateFormatUrl.format(calendar.getTime());

        } catch (ParseException e) {
            Log.e(TAG, e.toString());
        }
    }
}
