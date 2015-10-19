package ru.erlinve.Currency_1;

import ru.erlinve.Currency_1.IServiceListener;

/**
 * Created by sebastian on 10/19/15.
 */
interface IMainService {

    oneway void addServiceListener(in IServiceListener listener);
    oneway void removeServiceListener (in IServiceListener listener);

    oneway void downloadValuta(String date);
    oneway void abortDownloading();
}
