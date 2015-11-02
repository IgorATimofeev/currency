package ru.erlinve.Currency_1;

import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

public class MainActivity extends BaseManageActivity implements SwipeRefreshLayout.OnRefreshListener, DatePickerFragment.DatePickerDialogListener {

    private static final String TAG = MainActivity.class.getName();

    private static final String APP_PREFERENCE = "currencysettings";
    private static final String APP_PREFERENCE_DATE_URL = "currencydate";

    private ProgressDialog downloadingDialog;
    private DialogFragment datePickerFragment;
    private MenuItem selectDateMenuItem;
    private SwipeRefreshLayout swipeRefreshLayout;

    private SharedPreferences mainPreference;

    private DateStringCreator dateCreator;

    @Override
    protected void postServiceConnect() {

        IMainService mainService = getMainService();

        if(mainService!=null) {

            try {
                mainService.addServiceListener(serviceListener);
                if(!datePickerFragment.isAdded()) {


                    mainService.downloadValuta(mainPreference.getString(APP_PREFERENCE_DATE_URL,
                            // default value
                            dateCreator.getCurrentDateUrl()));
                }
                else {
                    downloadingDialog.dismiss();
                }
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

        mainPreference = getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        swipeRefreshLayout.setOnRefreshListener(this);

        downloadingDialog = new ProgressDialog(this);

        downloadingDialog.setMessage("Loading...");

        datePickerFragment = new DatePickerFragment();
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
//        if(datePickerFragment.isAdded()) {
//            datePickerFragment.dismiss();
//        }
        super.onPause();
    }



    private IServiceListener serviceListener = new IServiceListener.Stub() {

        @Override
        public void handleValutaParcel(List<ValuteDataParcel> listValuteData) {

            Log.e(TAG, String.valueOf(listValuteData.size()));

            downloadingDialog.dismiss();
        }
    };


    @Override
    public void onRefresh() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                swipeRefreshLayout.setRefreshing(false);

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
        }, 4000);

    }

    /**
     ************************************ MENU OPTIONS ***************************
     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        this.getMenuInflater().inflate(R.menu.action_bar_menu, menu);

        selectDateMenuItem = menu.findItem(R.id.date_picker_button);
        selectDateMenuItem.setTitle(dateCreator.getCurrentDateTitle());

        Log.e(TAG, "onCreateOptionsMenu");

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.date_picker_button:
                this.displayDatePicker();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * **************************** DIALOG-FRAGMENT'S OPTIONS ************************
     */

    private void displayDatePicker()
    {
        datePickerFragment.show(getFragmentManager(), "datePicker");
    }

    @Override
    public void onFinishEditDialog(int year, int month, int  day) {

        selectDateMenuItem.setTitle(dateCreator.getDateFormatTitle(year, month, day));

        downloadingDialog.show();

        this.saveSetting(APP_PREFERENCE_DATE_URL, dateCreator.getDateFormatUrl(year, month, day));

        IMainService mainService = getMainService();

        if(mainService!=null) {

            try {
                mainService.addServiceListener(serviceListener);
                mainService.downloadValuta(dateCreator.getDateFormatUrl(year, month, day));
            } catch (RemoteException e) {
                Log.e(TAG, e.toString() + " " + e.getMessage());
            }


        }


    }

    private void saveSetting(String key, String value) {

        SharedPreferences.Editor editor = mainPreference.edit();
        editor.putString(key, value);
        editor.apply();
    }
}
