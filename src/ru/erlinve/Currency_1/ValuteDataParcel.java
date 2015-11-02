package ru.erlinve.Currency_1;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sebastian on 10/23/15.
 */
public class ValuteDataParcel implements Parcelable {

    private static final String TAG = ValuteDataParcel.class.getName();

    private String id;
    private int numcode;
    private String charcode;
    private int nominal;
    private String name;
    private double value;
    private long dateup;

// Constructor

    public  ValuteDataParcel (String c_id, int c_numcode, String c_charcode, int c_nominal,
                              String c_name, double c_value, long c_dateup)
    {
        id = c_id;
        numcode = c_numcode;
        charcode = c_charcode;
        nominal = c_nominal;
        name = c_name;
        value = c_value;
        dateup = c_dateup;
    }

// Set Parameter functions

    public void  setId(String c_id)
    {
        id = c_id;
    }
    public void  setNumCode(int c_numcode)
    {
        numcode = c_numcode;
    }
    public void  setCharCode(String c_charcode)
    {
        charcode = c_charcode;
    }
    public void  setNominal(int c_nominal)
    {
        nominal = c_nominal;
    }
    public void  setName(String c_name)
    {
        name = c_name;
    }
    public void  setValue(double c_value)
    {
        value = c_value;
    }
    public void  setDateUp(long c_dateup)
    {
        dateup = c_dateup;
    }

// Get Parameter functions

    public String getId()
    {
        return id;
    }
    public int getNumCode()
    {
        return numcode;
    }
    public String getCharCode()
    {
        return charcode;
    }
    public int getNominal()
    {
        return nominal;
    }
    public String getName()
    {
        return name;
    }
    public double getValue()
    {
        return value;
    }
    public long getDateUp()
    {
        return dateup;
    }

    public static final Parcelable.Creator<ValuteDataParcel> CREATOR = new Parcelable.Creator<ValuteDataParcel> ()
    {
        @Override
        public ValuteDataParcel createFromParcel( Parcel source)
        {
            return new ValuteDataParcel(source);
        }

        @Override
        public ValuteDataParcel[] newArray(int size) {
            return new ValuteDataParcel[size];
        }
    };

    public ValuteDataParcel(Parcel in)
    {
        readFromParcel(in);
    }
    public void  readFromParcel(Parcel in)
    {
        id = in.readString();
        numcode = in.readInt();
        charcode = in.readString();
        nominal = in.readInt();
        name = in.readString();
        value = in.readDouble();
        dateup = in.readLong();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(id);
        dest.writeInt(numcode);
        dest.writeString(charcode);
        dest.writeInt(nominal);
        dest.writeString(name);
        dest.writeDouble(value);
        dest.writeLong(dateup);
    }
}