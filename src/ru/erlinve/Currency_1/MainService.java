package ru.erlinve.Currency_1;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by sebastian on 10/19/15.
 */
public class MainService extends Service {
    private static final String TAG = MainService.class.getName();

    private IMainService.Stub binder = null;

    private IMainService.Stub getBinder() {

        Log.e(TAG, "getBinder");

        if(binder==null)
        {

            Log.e(TAG, "getBinder new Binder");

            binder = new MainServiceRealization(getBaseContext());
        }
        return binder;
    }

    @Override
    public IBinder onBind(Intent intent) {

        Log.e(TAG, "onBind");

        return getBinder();
    }

    @Override
    public void onCreate()
    {
        Log.e(TAG, "Create Service");
    }

    @Override
    public void onDestroy()
    {
        Log.e(TAG, "Destroy Service");
    }

    @Override
    public boolean onUnbind(Intent intent)
    {

        Log.e(TAG, "Unbind Service");

        return super.onUnbind(intent);
    }
}
