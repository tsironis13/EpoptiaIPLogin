package epoptia.iplogin.com.app;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import epoptia.iplogin.com.app.utils.KioskService;
import epoptia.iplogin.com.app.utils.LifecycleHandler;

/**
 * Created by giannis on 26/8/2017.
 */

public class MyApplication extends Application implements LifecycleHandler.AppStateListener {

    //region Private Properties

    private static final String debugTag = MyApplication.class.getSimpleName();
    private Intent intent;

    //endregion

    //region Lifecycle Methods

    @Override
    public void onCreate() {
        super.onCreate();

        intent = new Intent(this, KioskService.class);
        LifecycleHandler.init(this);
        LifecycleHandler.get(this).addListener(this);
//        registerActivityLifecycleCallbacks(new LifecycleHandler());
    }

    //endregion

    //region Public Methods

    @Override
    public void onBecameForeground() {
        stopService(intent);
    }

    @Override
    public void onBecameBackground() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        boolean locked = preferences.getBoolean("locked", false);
        if (locked) {
//            Intent intent = new Intent(getApplicationContext(), KioskModeActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(intent);
            startService(intent);
        }
    }

    //endregion

    //region Private Methods

    //endregion

}
