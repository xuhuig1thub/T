package com.a.notification;

import android.os.RemoteException;
import android.util.Log;

class IBinderImpl extends IMyAidlInterface.Stub{

    private static final String TAG = "IBinderImpl";

    RemoteService remoteService;

   public IBinderImpl(){
       Log.d(TAG, "IBinderImpl() called");
    }

   public  IBinderImpl(RemoteService remoteService){
       Log.d(TAG, "IBinderImpl() called with: remoteService = [" + remoteService + "]");
        this.remoteService=remoteService;
    }

    @Override
    public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

    }

    @Override
    public String getValue() throws RemoteException {

        return "从AIDL服务获取的值";
    }

    public int getAidlInt() throws RemoteException{
        return remoteService.getInt();
    }
}