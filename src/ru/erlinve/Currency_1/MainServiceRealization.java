package ru.erlinve.Currency_1;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by sebastian on 10/19/15.
 */
public class MainServiceRealization extends IMainService.Stub {

    private static final String TAG = MainServiceRealization.class.getName();

    private final Set<IServiceListener> serviceListeners = new HashSet<IServiceListener>();

    private HandlerThread backgroundThread;
    private Handler backgroundHandler;

    public MainServiceRealization (Context context) {

        Log.e(TAG, "MSRealization_Constructor " + context.getPackageCodePath());

        backgroundThread = new HandlerThread("handlerThread");
        backgroundThread.start();

        backgroundHandler = new Handler(backgroundThread.getLooper());

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

                DownloadTask downloadTask = new DownloadTask(serviceListener, date);
                DownloadTask testTask = new DownloadTask(serviceListener, "test");
                backgroundHandler.post(downloadTask);

                backgroundHandler.post(testTask);
            }
        }

    }

    @Override
    public void abortDownloading()
    {
        backgroundHandler.removeCallbacksAndMessages(null);
        backgroundThread.quit();
        backgroundThread.interrupt();
    }
}
