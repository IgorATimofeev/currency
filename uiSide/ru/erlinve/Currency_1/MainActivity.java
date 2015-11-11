package ru.erlinve.Currency_1;

import android.app.DialogFragment;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends BaseManageActivity implements SwipeRefreshLayout.OnRefreshListener, DatePickerFragment.DatePickerDialogListener {

    private static final String TAG = MainActivity.class.getName();
    private static final String DATE_URL_STATE_KEY = "datekey";

    private DialogFragment datePickerFragment;
    private MenuItem selectDateMenuItem;
    private SwipeRefreshLayout swipeRefreshLayout;

    private ListView listView;

    private DateStringCreator dateCreator;

    @Override
    protected void postServiceConnect() {


        this.onRefresh();

        swipeRefreshLayout.setRefreshing(true);
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

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        swipeRefreshLayout.setOnRefreshListener(this);

        listView = (ListView) findViewById(R.id.listView);

        datePickerFragment = new DatePickerFragment();
    }

    @Override
    protected void onResume() {

        super.onResume();

    }

    @Override
    protected void onPause() {

        super.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle saveInstanceState) {

        saveInstanceState.putString(DATE_URL_STATE_KEY, dateCreator.getDateFormatUrl());

        super.onSaveInstanceState(saveInstanceState);
    }
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {

        dateCreator.setDate(savedInstanceState.getString(DATE_URL_STATE_KEY));
    }



    private IServiceListener serviceListener = new IServiceListener.Stub() {

        @Override
        public void handleValutaParcel(final List<ValuteDataParcel> listValuteData) {

            Log.e(TAG, String.valueOf(listValuteData.size()));

            final List<ValuteDataParcel> data = listValuteData;

                    runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ViewAdapter viewAdapter = new ViewAdapter(MainActivity.this,  data);

                    listView.setAdapter(viewAdapter);
                    viewAdapter.notifyDataSetChanged();

                    swipeRefreshLayout.setRefreshing(false);
                }
            });
        }
    };


    @Override
    public void onRefresh() {

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
                IMainService mainService = getMainService();

                if (mainService != null) {

                    try {
                        mainService.addServiceListener(serviceListener);
                        mainService.downloadValuta(dateCreator.getDateFormatUrl());
                    } catch (RemoteException e) {
                        Log.e(TAG, e.toString() + " " + e.getMessage());
                    }
                }
//            }
//        }, 4000);

    }

    /**
     ************************************ MENU OPTIONS ***************************
     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        this.getMenuInflater().inflate(R.menu.action_bar_menu, menu);

        selectDateMenuItem = menu.findItem(R.id.date_picker_button);
        selectDateMenuItem.setTitle(dateCreator.getDateFormatTitle());

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

    private void displayDatePicker() {
        datePickerFragment.show(getFragmentManager(), "datePicker");
    }

    @Override
    public void onFinishEditDialog(int year, int month, int  day) {

        dateCreator.setDate(year, month, day);

        selectDateMenuItem.setTitle(dateCreator.getDateFormatTitle());

        this.onRefresh();

        swipeRefreshLayout.setRefreshing(true);

    }

}
