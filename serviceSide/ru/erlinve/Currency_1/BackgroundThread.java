package ru.erlinve.Currency_1;

import android.os.*;
import android.os.Process;
import android.util.Log;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EncodingUtils;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sebastian on 10/22/15.
 */
public class BackgroundThread extends HandlerThread {

    private static final String TAG = BackgroundThread.class.getName();

    private static final String URL_PREFORM = "http://www.cbr.ru/scripts/XML_daily.asp?date_req=";

    private static final int SECOND_ARG = 0;

    private static final int INDEX_DOWNLOAD_DATA = 0;
    private static final int INDEX_PARCE_DATA = 1;


    private Handler mBackgroundHandler;

    private final ArrayList<IServiceListener> serviceListeners = new ArrayList<>();

    public BackgroundThread(String name) {
        super(name);

        this.setPriority(Process.THREAD_PRIORITY_BACKGROUND);
    }

    public void prepareHandler() {

        mBackgroundHandler = new Handler(getLooper(), new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {

                switch (msg.what) {
                    case INDEX_DOWNLOAD_DATA:
                        try {
                            downloadData((String) msg.obj, msg.arg1);
                        } catch (InterruptedException e) {
                            Log.e(TAG, e.toString() + " " + e.getMessage());
                        } catch (RemoteException e) {
                            Log.e(TAG, e.toString() + " " + e.getMessage());
                        } catch (IOException e) {
                            Log.e(TAG, e.toString() + " " + e.getMessage());
                        }
                        break;
                    case INDEX_PARCE_DATA:
                        try {
                            parseData((String) msg.obj, msg.arg1);
                        } catch (InterruptedException e) {
                            Log.e(TAG, e.toString() + " " + e.getMessage());
                        } catch (RemoteException e) {
                            Log.e(TAG, e.toString() + " " + e.getMessage());
                        }
                        break;
                }


                return true;
            }
        });
    }

    private void downloadData(String date, int indexListener) throws InterruptedException, RemoteException, IOException {

        String finalUrlString = URL_PREFORM+date;

        String resultString = new String();

        HttpClient client = new DefaultHttpClient();
        HttpGet getRequest = new HttpGet(finalUrlString);

        HttpResponse response  = client.execute(getRequest);

        resultString = EncodingUtils.getString(EntityUtils.toByteArray(response.getEntity()), "windows-1251");

        mBackgroundHandler.obtainMessage(INDEX_PARCE_DATA, indexListener, SECOND_ARG, resultString).sendToTarget();
    }

    private void parseData(String date, int indexListener) throws InterruptedException, RemoteException {

        ParserXml parserXml = new ParserXml();

        List<ValuteDataParcel> data = parserXml.getListValuteParcel(date);
        Log.e(TAG, String.valueOf(data.size()));

//        TimeUnit.SECONDS.sleep(4);

        serviceListeners.get(indexListener).handleValutaParcel(data);

        serviceListeners.remove(indexListener);

    }

    public void loading(IServiceListener serviceListener,  String date) {

        serviceListeners.add(serviceListener);
        int indexListener = serviceListeners.indexOf(serviceListener);

        mBackgroundHandler.obtainMessage(INDEX_DOWNLOAD_DATA, indexListener, SECOND_ARG, date).sendToTarget();
    }

    public void prepareQuit() {

        mBackgroundHandler.removeCallbacksAndMessages(null);
    }
}
