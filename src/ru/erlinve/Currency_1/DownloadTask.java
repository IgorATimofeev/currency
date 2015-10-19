package ru.erlinve.Currency_1;

import android.os.RemoteException;
import android.util.Log;

import java.util.concurrent.TimeUnit;

/**
 * Created by sebastian on 10/19/15.
 */
public class DownloadTask implements Runnable {

    private static final String TAG = DownloadTask.class.getName();

    private IServiceListener mListener;
    private String mDate;

    public DownloadTask(IServiceListener serviceListener, String date) {

        mListener = serviceListener;
        mDate = date;

    }

    @Override
    public void run() {

        try {
            this.download(mDate);
        } catch (InterruptedException e) {
            Log.e(TAG, e.toString()+" "+e.getMessage());
        } catch (RemoteException e) {
            Log.e(TAG, e.toString() + " " + e.getMessage());
        }

        try {
            mListener.handleValutaParcel();
        } catch (RemoteException e) {
            Log.e(TAG, e.toString() + " " + e.getMessage());
        }

    }

    private void download(String date) throws InterruptedException, RemoteException {

        TimeUnit.SECONDS.sleep(2);
        Log.e(TAG, date+"0");
        TimeUnit.SECONDS.sleep(2);
        Log.e(TAG, date+"1");
        TimeUnit.SECONDS.sleep(2);
        Log.e(TAG, date+"2");
        TimeUnit.SECONDS.sleep(2);
        Log.e(TAG, date+"3");
        TimeUnit.SECONDS.sleep(2);
        Log.e(TAG, date+"4");
        TimeUnit.SECONDS.sleep(2);
        Log.e(TAG, date+"5");

    }
}
