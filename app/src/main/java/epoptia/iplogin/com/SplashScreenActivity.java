package epoptia.iplogin.com;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import epoptia.iplogin.com.admin.WorkStationsActivity;
import epoptia.iplogin.com.base.BaseActivity;
import epoptia.iplogin.com.databinding.SplashScreenBinding;
import epoptia.iplogin.com.kioskmodetablet.KioskModeActivity;
import epoptia.iplogin.com.login.LoginActivity;
import epoptia.iplogin.com.utls.SharedPrefsUtl;

public class SplashScreenActivity extends BaseActivity {

    private static final String debugTag = SplashScreenActivity.class.getSimpleName();
    private SplashScreenBinding mBinding;
//    private OverlayDialog mOverlayDialog;
    private Intent intent;
    private DevicePolicyManager devicePolicyManager;
    private ComponentName mDPM;
    private int actionType;
//    private static final int CALL_PHONE = 1030;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.splash_screen);
        initializeApp();
        intent = getIntent();

        actionType = intent.getIntExtra(getResources().getString(R.string.action_type), 0);
        if (actionType == 1020) { //UNLOCK SCREEN
            PackageManager p = getPackageManager();
            ComponentName cN = new ComponentName(getApplicationContext(), KioskModeActivity.class);
            p.setComponentEnabledSetting(cN, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null) {
//            actionType = intent.getExtras().getInt(getResources().getString(R.string.action_type));
            actionType = intent.getIntExtra(getResources().getString(R.string.action_type), 0);
            if (actionType == 1020) { //UNLOCK SCREEN
                PackageManager p = getPackageManager();
                ComponentName cN = new ComponentName(getApplicationContext(), KioskModeActivity.class);
                p.setComponentEnabledSetting(cN, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
            }
        }
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    private void initializeApp() {
        //final boolean authenticated = SharedPrefsUtl.getBooleanFlag(this, getResources().getString(R.string.domain_authenticated));
        final boolean ipRegistered = SharedPrefsUtl.getBooleanFlag(this, getResources().getString(R.string.ip_registered));
        final boolean locked = SharedPrefsUtl.getBooleanFlag(this, getResources().getString(R.string.device_locked));
        startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
        if (!ipRegistered) {
            startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
        } else if (!locked) {
            startActivity(new Intent(SplashScreenActivity.this, WorkStationsActivity.class));
        } else {
            if (getResources().getConfiguration().smallestScreenWidthDp >= 600) {
                startActivity(new Intent(this, KioskModeActivity.class));
            } else {
                startActivity(new Intent(this, epoptia.iplogin.com.kioskmodephone.KioskModeActivity.class));
            }
        }
    }

}
