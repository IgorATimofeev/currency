package ru.erlinve.Currency_1;

import android.content.Context;
import android.util.Log;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by sebastian on 10/19/15.
 */
public class MainServiceRealization extends IMainService.Stub {

    private static final String TAG = MainServiceRealization.class.getName();

    private final Set<IServiceListener> serviceListeners = new HashSet<IServiceListener>();

    private BackgroundThread backgroundThread;

    public MainServiceRealization (Context context) {

        Log.e(TAG, "MSRealization_Constructor " + context.getPackageCodePath());

        backgroundThread = new BackgroundThread("newThread");
        backgroundThread.start();
        backgroundThread.prepareHandler();

    }

    @Override
    public void addServiceListener(IServiceListener listener) {

        synchronized (serviceListeners) {
            serviceListeners.add(listener);
        }

    }

    @Override
    public void removeServiceListener (IServiceListener listener) {

        Log.e(TAG, "removeServiceListener");

        this.abortDownloading();

        synchronized (serviceListeners) {
            serviceListeners.remove(listener);
        }

    }

    @Override
    public void downloadValuta(String date) {

        synchronized (serviceListeners) {

            for (final IServiceListener serviceListener : serviceListeners) {

                Log.e(TAG, Thread.currentThread().getName());

                backgroundThread.loading(serviceListener, date);


            }
        }

    }

    @Override
    public void abortDownloading()
    {
        backgroundThread.prepareQuit();
        backgroundThread.quit();
        backgroundThread.interrupt();
    }
}
