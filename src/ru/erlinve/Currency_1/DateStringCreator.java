package ru.erlinve.Currency_1;

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

    private SimpleDateFormat dateFormatTitle;
    private SimpleDateFormat dateFormatUrl;
    private Calendar calendar;

    DateStringCreator()
    {
        dateFormatTitle = new SimpleDateFormat(DATE_FORMAT_TITLE);
        dateFormatUrl = new SimpleDateFormat(DATE_FORMAT_URL);
        calendar = Calendar.getInstance();
    }

    protected String getCurrentDateTitle()
    {
        return dateFormatTitle.format(new Date());
    }
    protected String getCurrentDateUrl()
    {
        return dateFormatUrl.format(new Date());
    }
    protected String getDateFormatTitle(int year, int month, int day)
    {

        calendar.set(year, month, day);
        return dateFormatTitle.format(calendar.getTime());
    }
    protected String getDateFormatUrl(int year, int month, int day)
    {
        calendar.set(year, month, day);
        return dateFormatUrl.format(calendar.getTime());
    }
}
