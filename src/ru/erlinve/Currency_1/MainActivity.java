package ru.erlinve.Currency_1;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;

public class MainActivity extends BaseManageActivity {

    private static final String TAG = MainActivity.class.getName();

    private ProgressDialog downloadingDialog;
    private DateStringCreator dateCreator;

    @Override
    protected void postServiceConnect() {

        IMainService mainService = getMainService();

        if(mainService!=null) {

            try {
                mainService.addServiceListener(serviceListener);
                mainService.downloadValuta(dateCreator.getCurrentDateUrl());
            } catch (RemoteException e) {
                Log.e(TAG, e.toString() + " " + e.getMessage());
            }


        }
    }

    @Override
    protected void preServiceDisconnect() {

        IMainService mainService = getMainService();

        if(mainService!=null) {

            try {

                Log.e(TAG, "onPreDisconnect");
                mainService.removeServiceListener(serviceListener);
            } catch (RemoteException e) {
                Log.e(TAG, e.toString() + " " + e.getMessage());
            }
        }

    }

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        dateCreator = new DateStringCreator();

        downloadingDialog = new ProgressDialog(this);
        downloadingDialog.setMessage("Loading...");
    }

    @Override
    protected void onResume() {

        super.onResume();

        downloadingDialog.show();


    }

    @Override
    protected void onPause() {

        if(downloadingDialog.isShowing()) {

            downloadingDialog.dismiss();

        }
        super.onPause();
    }



    private IServiceListener serviceListener = new IServiceListener.Stub() {

        @Override
        public void handleValutaParcel() {

            downloadingDialog.dismiss();
        }
    };
}
