package epoptia.iplogin.com.app.utils;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import epoptia.iplogin.com.services.ConnectivityChangeService;

public class ConnectivityChangeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        ComponentName comp = new ComponentName(context.getPackageName(), ConnectivityChangeService.class.getName());

        intent.putExtra("networkStatus",isConnected(context));
        context.startService(intent.setComponent(comp));
    }

    public boolean isConnected(Context context) {
        ConnectivityManager connectivityManager = ((ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE));
        NetworkInfo networkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;



        return networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();
    }
}
