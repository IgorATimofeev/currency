package ru.erlinve.Currency_1;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by sebastian on 10/19/15.
 */
public abstract class BaseManageActivity extends Activity {

    private static final String TAG = BaseManageActivity.class.getName();

    protected abstract void postServiceConnect();
    protected abstract void preServiceDisconnect();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.e(TAG, "onResume");

        if (mainService == null) {

            Log.e(TAG, "onResume binding");

            Intent i = new Intent(this, MainService.class);
            bindService(i, serviceConnection, Context.BIND_AUTO_CREATE);
        }
    }

    @Override
    protected void onPause() {

        Log.e(TAG, "onPause");

        preServiceDisconnect();

        if(mainService!=null)
        {
            Log.e(TAG, "onPause unbinding");

            unbindService(serviceConnection);
            mainService = null;
        }

        super.onPause();

    }


    /*
    ********** SERVICE CONNECTING ***********
     */

    private IMainService mainService = null;

    private final ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            mainService = IMainService.Stub.asInterface(service);

            postServiceConnect();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

            preServiceDisconnect();

            mainService = null;
            unbindService(this);
        }
    };

    protected IMainService getMainService() {

        return mainService;
    }
}