package ru.erlinve.Currency_1;

import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends BaseManageActivity implements SwipeRefreshLayout.OnRefreshListener, DatePickerFragment.DatePickerDialogListener {

    private static final String TAG = MainActivity.class.getName();

    private ProgressDialog downloadingDialog;
    private DialogFragment datePickerFragment;
    private MenuItem selectDateMenuItem;

    private DateStringCreator dateCreator;

    @Override
    protected void postServiceConnect() {

        IMainService mainService = getMainService();

        if(mainService!=null) {

            try {
                mainService.addServiceListener(serviceListener);
                if(!datePickerFragment.isAdded()) {
                    mainService.downloadValuta(dateCreator.getCurrentDateUrl());
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
        public void handleValutaParcel() {

            downloadingDialog.dismiss();
        }
    };


    @Override
    public void onRefresh() {

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
}
