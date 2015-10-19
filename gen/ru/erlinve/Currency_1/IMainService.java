/*___Generated_by_IDEA___*/

/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: /home/sebastian/Projects/Erlinve/Currency_1/aidl/ru/erlinve/Currency_1/IMainService.aidl
 */
package ru.erlinve.Currency_1;
/**
 * Created by sebastian on 10/19/15.
 */
public interface IMainService extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements ru.erlinve.Currency_1.IMainService
{
private static final java.lang.String DESCRIPTOR = "ru.erlinve.Currency_1.IMainService";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an ru.erlinve.Currency_1.IMainService interface,
 * generating a proxy if needed.
 */
public static ru.erlinve.Currency_1.IMainService asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof ru.erlinve.Currency_1.IMainService))) {
return ((ru.erlinve.Currency_1.IMainService)iin);
}
return new ru.erlinve.Currency_1.IMainService.Stub.Proxy(obj);
}
@Override public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(DESCRIPTOR);
return true;
}
case TRANSACTION_addServiceListener:
{
data.enforceInterface(DESCRIPTOR);
ru.erlinve.Currency_1.IServiceListener _arg0;
_arg0 = ru.erlinve.Currency_1.IServiceListener.Stub.asInterface(data.readStrongBinder());
this.addServiceListener(_arg0);
return true;
}
case TRANSACTION_removeServiceListener:
{
data.enforceInterface(DESCRIPTOR);
ru.erlinve.Currency_1.IServiceListener _arg0;
_arg0 = ru.erlinve.Currency_1.IServiceListener.Stub.asInterface(data.readStrongBinder());
this.removeServiceListener(_arg0);
return true;
}
case TRANSACTION_downloadValuta:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.downloadValuta(_arg0);
return true;
}
case TRANSACTION_abortDownloading:
{
data.enforceInterface(DESCRIPTOR);
this.abortDownloading();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements ru.erlinve.Currency_1.IMainService
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
@Override public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
@Override public void addServiceListener(ru.erlinve.Currency_1.IServiceListener listener) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((listener!=null))?(listener.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_addServiceListener, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
@Override public void removeServiceListener(ru.erlinve.Currency_1.IServiceListener listener) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((listener!=null))?(listener.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_removeServiceListener, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
@Override public void downloadValuta(java.lang.String date) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(date);
mRemote.transact(Stub.TRANSACTION_downloadValuta, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
@Override public void abortDownloading() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_abortDownloading, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
}
static final int TRANSACTION_addServiceListener = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_removeServiceListener = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_downloadValuta = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_abortDownloading = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
}
public void addServiceListener(ru.erlinve.Currency_1.IServiceListener listener) throws android.os.RemoteException;
public void removeServiceListener(ru.erlinve.Currency_1.IServiceListener listener) throws android.os.RemoteException;
public void downloadValuta(java.lang.String date) throws android.os.RemoteException;
public void abortDownloading() throws android.os.RemoteException;
}
