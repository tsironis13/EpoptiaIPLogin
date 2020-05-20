package epoptia.iplogin.com;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import epoptia.iplogin.com.admin.WorkStationsActivity;
import epoptia.iplogin.com.kioskmodetablet.KioskModeActivity;
import epoptia.iplogin.com.utls.SharedPrefsUtl;

public class PackageReplacedReceiver extends BroadcastReceiver {

    private static final String debugTag = PackageReplacedReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_PACKAGE_REPLACED)) {
            boolean locked = SharedPrefsUtl.getBooleanFlag(context, context.getResources().getString(R.string.device_locked));

            PackageManager p = context.getPackageManager();
            Intent i;
//todo disable this process avoiding problems when internet cable is unplugged or internet signal is lost
            if (locked) {
                ComponentName cN = new ComponentName(context, KioskModeActivity.class);
                p.setComponentEnabledSetting(cN, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);

                //i = new Intent(context, KioskModeActivity.class);
                //i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            } else {
                ComponentName cN = new ComponentName(context, WorkStationsActivity.class);
                p.setComponentEnabledSetting(cN, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);

                //i = new Intent(context, WorkStationsActivity.class);
                //i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            }

            //context.startActivity(i);
        }
    }
}
