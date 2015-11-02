package ru.erlinve.Currency_1;

import ru.erlinve.Currency_1.ValuteDataParcel;

/**
 * Created by sebastian on 10/19/15.
 */
interface IServiceListener {

    void handleValutaParcel( in List<ValuteDataParcel> listValuteData);
}
