package com.nethergrim.bashorg.purchases;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.android.vending.billing.IInAppBillingService;
import com.nethergrim.bashorg.App;

/**
 * @author andrej on 24.06.15.
 */
public class InAppBillingServiceHolder {

    private static IInAppBillingService mService;

    private static ServiceConnection mServiceConn = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
        }

        @Override
        public void onServiceConnected(ComponentName name,
                                       IBinder service) {
            mService = IInAppBillingService.Stub.asInterface(service);
        }
    };

    public static void bindToService() {
        Context context = App.getInstance().getApplicationContext();

        Intent serviceIntent = new Intent("com.android.vending.billing.InAppBillingService.BIND");
        serviceIntent.setPackage("com.android.vending");
        context.bindService(serviceIntent, mServiceConn, Context.BIND_AUTO_CREATE);
    }

    public static void unbindService() {
        Context context = App.getInstance().getApplicationContext();
        context.unbindService(mServiceConn);
    }

    public static IInAppBillingService getService() {
        return mService;
    }

    public static boolean isConnected() {
        return mService != null;
    }
}
