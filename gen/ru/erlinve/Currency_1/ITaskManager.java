/*___Generated_by_IDEA___*/

/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: /home/sebastian/Projects/Erlinve/Currency_1/aidl/ru/erlinve/Currency_1/ITaskManager.aidl
 */
package ru.erlinve.Currency_1;
/**
 * Created by sebastian on 10/21/15.
 */
public interface ITaskManager extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements ru.erlinve.Currency_1.ITaskManager
{
private static final java.lang.String DESCRIPTOR = "ru.erlinve.Currency_1.ITaskManager";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an ru.erlinve.Currency_1.ITaskManager interface,
 * generating a proxy if needed.
 */
public static ru.erlinve.Currency_1.ITaskManager asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof ru.erlinve.Currency_1.ITaskManager))) {
return ((ru.erlinve.Currency_1.ITaskManager)iin);
}
return new ru.erlinve.Currency_1.ITaskManager.Stub.Proxy(obj);
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
case TRANSACTION_startParcer:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.startParcer(_arg0);
return true;
}
case TRANSACTION_getParcelable:
{
data.enforceInterface(DESCRIPTOR);
this.getParcelable();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements ru.erlinve.Currency_1.ITaskManager
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
@Override public void startParcer(java.lang.String httpResponse) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(httpResponse);
mRemote.transact(Stub.TRANSACTION_startParcer, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
@Override public void getParcelable() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getParcelable, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
}
static final int TRANSACTION_startParcer = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_getParcelable = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
}
public void startParcer(java.lang.String httpResponse) throws android.os.RemoteException;
public void getParcelable() throws android.os.RemoteException;
}
