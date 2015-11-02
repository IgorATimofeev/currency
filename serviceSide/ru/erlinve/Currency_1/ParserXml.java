package ru.erlinve.Currency_1;

import android.util.Log;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by sebastian on 10/23/15.
 */
public class ParserXml {

    private static final String TAG = ParserXml.class.getName();

    private static final String TAG_DATE_FORMAT = "dd.MM.yyyy";

// tags of xml

    private static final String TAG_VALCURS = "ValCurs";
    private static final String TAG_VALUTE = "Valute";
    private static final String TAG_NUMCODE = "NumCode";
    private static final String TAG_CHARCODE = "CharCode";
    private static final String TAG_NOMINAL = "Nominal";
    private static final String TAG_NAME_VALUTE = "Name";
    private static final String TAG_VALUE = "Value";

    private long dateup = new Date().getTime();

    public ParserXml () {

    }

    public List<ValuteDataParcel> getListValuteParcel (String xmlString) {

        return this.handleXmlString(xmlString);
    }

    private List<ValuteDataParcel> handleXmlString(String xmlString) {

        List<ValuteDataParcel> resultList = new ArrayList<ValuteDataParcel>();

        XmlPullParserFactory factory = null;

        try {
            factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);

            XmlPullParser xmlParser = factory.newPullParser();

            xmlParser.setInput(new StringReader(xmlString));

            int eventType = xmlParser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT)
            {
                if(eventType == XmlPullParser.START_TAG)
                {
                    if(xmlParser.getName().equals(TAG_VALUTE))
                    {
//                        Log.e(TAG, TAG_VALUTE);

                        resultList.add(readValute(xmlParser));
                    }
                    else if(xmlParser.getName().equals(TAG_VALCURS))
                    {

//                        Log.e(TAG, TAG_VALCURS);

                        SimpleDateFormat newDateFormat = new SimpleDateFormat(TAG_DATE_FORMAT);
                        String dateString = xmlParser.getAttributeValue(0);

                        dateString = dateString.replaceAll("/",".");

                        dateup = newDateFormat.parse(dateString).getTime();

                        Log.e(TAG, dateString);
                    }


                }
                eventType = xmlParser.next();
            }

        } catch (XmlPullParserException e) {
            Log.e(TAG, e.toString());
        } catch (IOException e) {
            Log.e(TAG, e.toString());
        } catch (ParseException e) {
            Log.e(TAG, e.toString());
        }

        return resultList;
    }

    private  ValuteDataParcel  readValute (XmlPullParser parser) throws IOException, XmlPullParserException {

        String id = parser.getAttributeValue(0);
        int numcode = 0;
        String charcode = new String();
        int nominal = 0;
        String name = new String();
        double value = 0;

        parser.require(XmlPullParser.START_TAG, null, TAG_VALUTE);

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String nameParser = parser.getName();
            if (nameParser.equals(TAG_NUMCODE))
            {
                numcode = Integer.parseInt(getTagValue(parser, TAG_NUMCODE));
            }
            else if (nameParser.equals(TAG_CHARCODE))
            {
                charcode = getTagValue(parser, TAG_CHARCODE);
            }
            else if (nameParser.equals(TAG_NOMINAL))
            {
                nominal = Integer.parseInt(getTagValue(parser, TAG_NOMINAL));
            }
            else if (nameParser.equals(TAG_NAME_VALUTE))
            {
                name = getTagValue(parser, TAG_NAME_VALUTE);

            }
            else if (nameParser.equals(TAG_VALUE))
            {
                value = Double.parseDouble(getTagValue(parser, TAG_VALUE).replace(",","."));
            }
        }

        return new ValuteDataParcel(id, numcode, charcode, nominal, name, value, dateup);
    }

    private  String getTagValue(XmlPullParser parser, String tagName) throws IOException, XmlPullParserException {
        String result = new String();

        parser.require(XmlPullParser.START_TAG, null, tagName);
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        parser.require(XmlPullParser.END_TAG, null, tagName);
        return result;
    }

}
