package epoptia.iplogin.com.admin;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import epoptia.iplogin.com.R;
import epoptia.iplogin.com.adapters.RecyclerViewAdapter;
import epoptia.iplogin.com.base.BaseActivity;
import epoptia.iplogin.com.databinding.ActivityWorkStationsBinding;
import epoptia.iplogin.com.kioskmodetablet.KioskModeActivity;
import epoptia.iplogin.com.pojo.GetWorkStationsRequest;
import epoptia.iplogin.com.pojo.GetWorkStationsResponse;
import epoptia.iplogin.com.pojo.WorkStation;
import epoptia.iplogin.com.retrofit.APIClient;
import epoptia.iplogin.com.retrofit.APIInterface;
import epoptia.iplogin.com.utls.SharedPrefsUtl;
import fr.bmartel.speedtest.SpeedTestReport;
import fr.bmartel.speedtest.SpeedTestSocket;
import fr.bmartel.speedtest.inter.ISpeedTestListener;
import fr.bmartel.speedtest.model.SpeedTestError;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by giannis on 5/9/2017.
 */

public class WorkStationsActivity extends BaseActivity implements WordStationsContract.View {

    private static final String debugTag = WorkStationsActivity.class.getSimpleName();
    private static final int PHONE_STATE = 1020;
    public static final int OVERLAY_PERMISSION_REQ_CODE = 4545;
    private ActivityWorkStationsBinding mBinding;
    private RecyclerViewAdapter rcvAdapter;
    private LinearLayoutManager linearLayoutManager;
    private List<WorkStation> workStations = new ArrayList<>();
    private WorkStationsPresenter workStationsPresenter;
    private Menu menu;
    private int actionType, stationId;
    private APIInterface apiInterface;
    private String stationName;
    //private Handler networkStatusHandler;
    private SpeedTestSocket speedTestSocket;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_work_stations);
        setSupportActionBar(mBinding.incltoolbar.toolbar);
        mBinding.incltoolbar.toolbarTitle.setText(getResources().getString(R.string.work_stations_title));

        speedTestSocket = new SpeedTestSocket();
        addSpeedTestListener();

        workStationsPresenter = new WorkStationsPresenter(this);
        apiInterface = APIClient.getClient(SharedPrefsUtl.getStringFlag(this, getResources().getString(R.string.server_ip))).create(APIInterface.class);
        if (savedInstanceState != null) {
            stationId = savedInstanceState.getInt(getResources().getString(R.string.workstation_id));
        }
        actionType = getIntent().getIntExtra(getResources().getString(R.string.action_type), 0);
        if (actionType == 1020) { //UNLOCK SCREEN
            PackageManager p = getPackageManager();
            ComponentName cN = new ComponentName(getApplicationContext(), KioskModeActivity.class);
            p.setComponentEnabledSetting(cN, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
        }
        check();
        mBinding.retryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        //checkNetworkStateEveryMinute();
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter("networkState"));
    }

    @Override
    protected void onPause() {
        //networkStatusHandler.removeCallbacksAndMessages(null);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        super.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(getResources().getString(R.string.workstation_id), stationId);
    }

    private void check() {
        if (isNetworkAvailable()) {
            if (mBinding.getHaserror()) mBinding.setHaserror(false);
            initializeWorkStations();
        } else {
            mBinding.setHaserror(true);
            mBinding.setErrortext(getResources().getString(R.string.no_connection));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.work_stations_menu, menu);
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBaseViewClick(View view) {
        stationId = workStations.get((int)view.getTag()).getId();
        stationName = workStations.get((int)view.getTag()).getName();

        //tablet
        if (getResources().getConfiguration().smallestScreenWidthDp >= 600) {
            lockDeviceDialog();
        } else {
            Bundle bundle = new Bundle();
            bundle.putInt("station_id", stationId);
            bundle.putString("station_name", stationName);
            startActivity(new Intent(this, epoptia.iplogin.com.kioskmodephone.KioskModeActivity.class).putExtras(bundle));

            finish();
        }
    }

    private void initializeWorkStations() {
        final GetWorkStationsRequest request = new GetWorkStationsRequest();
        request.setAction("get_workstations");
        String accessToken = SharedPrefsUtl.getStringFlag(this, getResources().getString(R.string.access_token));
        request.setAccess_token(accessToken);
        mBinding.setProcessing(true);
        /**
         GET List Resources
         **/
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Call<GetWorkStationsResponse> responseCall = apiInterface.getWorkStations(request);
                responseCall.enqueue(new Callback<GetWorkStationsResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<GetWorkStationsResponse> call, @NonNull Response<GetWorkStationsResponse> response) {
                        mBinding.setProcessing(false);
                        if (response.body() != null) {
                            if (response.body().getCode() == 200) {
                                workStations = response.body().getData();
                                linearLayoutManager = new LinearLayoutManager(WorkStationsActivity.this);
                                if (rcvAdapter == null)mBinding.rcv.addItemDecoration(new DividerItemDecoration(WorkStationsActivity.this, DividerItemDecoration.VERTICAL));
                                rcvAdapter = new RecyclerViewAdapter(R.layout.work_stations_rcv_row) {
                                    @Override
                                    protected Object getObjForPosition(int position, ViewDataBinding mBinding) {
                                        return workStations.get(position);
                                    }
                                    @Override
                                    protected int getLayoutIdForPosition(int position) {
                                        return R.layout.work_stations_rcv_row;

                                    }
                                    @Override
                                    protected int getTotalItems() {
                                        return workStations.size();
                                    }

                                    @Override
                                    protected Object getClickListenerObject() {
                                        return workStationsPresenter;
                                    }
                                };
                                mBinding.rcv.setLayoutManager(linearLayoutManager);
                                mBinding.rcv.setNestedScrollingEnabled(false);
                                mBinding.rcv.setAdapter(rcvAdapter);
                            } else {
                                mBinding.setHaserror(true);
                                mBinding.setErrortext(getResources().getString(R.string.error));
//                        showSnackBrMsg(getResources().getString(R.string.error), mBinding.containerLnlt, Snackbar.LENGTH_SHORT);
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<GetWorkStationsResponse> call, @NonNull Throwable t) {
                        mBinding.setProcessing(false);
                        mBinding.setHaserror(true);
                        mBinding.setErrortext(getResources().getString(R.string.error));
                    }
                });
            }
        }, 600);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PHONE_STATE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                        canDrawOverlays();
                    }
                } else {
                    Toast.makeText(this, "PHONE STATE permission denied! App cannot be locked!", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case OVERLAY_PERMISSION_REQ_CODE:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (!Settings.canDrawOverlays(this)) {
                        Toast.makeText(this, "Client cannot access system settings without this permission!App cannot be locked", Toast.LENGTH_SHORT).show();
                    } else {
                        checkAppIsDefaultLauncher();
                    }
                }
                break;
        }
    }

    private void lockDeviceDialog() {
        new AlertDialog.Builder(this)
                .setTitle(getResources().getString(R.string.lock_device_dialog_title))
                .setMessage(getResources().getString(R.string.lock_device_dialog_msg))
                .setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        lockDevice();
                    }
                }).setNegativeButton(getResources().getString(R.string.no), null).show();
    }

    private void lockDevice() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            checkAppIsDefaultLauncher();
        } else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_PHONE_STATE)) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, PHONE_STATE);
                } else {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, PHONE_STATE);
                }
            } else {
                canDrawOverlays();
            }
        }
    }

    private boolean isMyAppLauncherDefault() {
        final IntentFilter filter = new IntentFilter(Intent.ACTION_MAIN);
        filter.addCategory(Intent.CATEGORY_HOME);

        List<IntentFilter> filters = new ArrayList<IntentFilter>();
        filters.add(filter);

        final String myPackageName = getPackageName();
        List<ComponentName> activities = new ArrayList<ComponentName>();
        final PackageManager packageManager = (PackageManager) getPackageManager();

        // You can use name of your package here as third argument
        packageManager.getPreferredActivities(filters, activities, null);

        for (ComponentName activity : activities) {
            if (myPackageName.equals(activity.getPackageName())) {
                return true;
            }
        }
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void canDrawOverlays() {
        if (!Settings.canDrawOverlays(this)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, OVERLAY_PERMISSION_REQ_CODE);
        } else {
            checkAppIsDefaultLauncher();
        }
    }

    private void checkAppIsDefaultLauncher() {
        if (!isMyAppLauncherDefault()) {
            PackageManager p = getPackageManager();
            ComponentName cN = new ComponentName(getApplicationContext(), KioskModeActivity.class);
            p.setComponentEnabledSetting(cN, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);

            Intent selector = new Intent(Intent.ACTION_MAIN);
            selector.addCategory(Intent.CATEGORY_HOME);

            PackageManager localPackageManager = getPackageManager();
            Intent intent = new Intent("android.intent.action.MAIN");
            intent.addCategory("android.intent.category.HOME");
            String str = localPackageManager.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY).activityInfo.packageName;

            if (!str.equals("kioskmode.com.epoptia")) {
//                Intent intnt = new Intent(Intent.ACTION_MAIN);
//                intnt.addCategory(Intent.CATEGORY_HOME);
//                intnt.addCategory(Intent.CATEGORY_DEFAULT);
//                intnt.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//                startActivity(Intent.createChooser(intnt, "Set as default to enable Kiosk Mode"));
            }
            Bundle bundle = new Bundle();
            bundle.putInt("station_id", stationId);
            bundle.putString("station_name", stationName);
            selector.putExtras(bundle);
            startActivity(selector);
        } else {
            PackageManager p = getPackageManager();
            ComponentName cN = new ComponentName(getApplicationContext(), KioskModeActivity.class);
            p.setComponentEnabledSetting(cN, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);

            Bundle bundle = new Bundle();
            bundle.putInt("station_id", stationId);
            bundle.putString("station_name", stationName);
            startActivity(new Intent(this, KioskModeActivity.class).putExtras(bundle));
        }
        finish();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return (cm != null ? cm.getActiveNetworkInfo() : null) != null && cm.getActiveNetworkInfo().isConnected();
    }

    private void checkNetworkStateEveryMinute() {
//        networkStatusHandler = new Handler();
//        networkStatusHandler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                setNetworkStatusAndShowNetworkRelatedSnackbar(isNetworkAvailable());
//                networkStatusHandler.postDelayed(this, 60000);
//            }
//        }, 1000);
    }

    private int getNetworkLinkSpeed() {
        WifiManager wifiManager = (WifiManager) getApplicationContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager != null ? wifiManager.getConnectionInfo() : null;
        if (wifiInfo != null) {
            return wifiInfo.getLinkSpeed(); //measured using WifiInfo.LINK_SPEED_UNITS
        }
        return -1;
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            boolean networkStatus = intent.getBooleanExtra("networkState", false);
            setNetworkStatusAndShowNetworkRelatedSnackbar(networkStatus);
        }
    };

    private void setNetworkStatusAndShowNetworkRelatedSnackbar(boolean networkState) {
        if (!networkState) {
            mBinding.setNetworkState("NO INTERNET CONNECTION");

            return;
        }

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                speedTestSocket.startDownload("http://ipv4.ikoula.testdebit.info/1M.iso");
            }
        });

        thread.start();
    }

    private void addSpeedTestListener() {
        speedTestSocket.addSpeedTestListener(new ISpeedTestListener() {

            @Override
            public void onCompletion(final SpeedTestReport report) {
                // called when download/upload is complete
                //System.out.println("[COMPLETED] rate in octet/s : " + report.getTransferRateOctet());
                //System.out.println("[COMPLETED] rate in bit/s   : " + report.getTransferRateBit());

                displayNetworkQuality(convertBitPerSecondToMegabitPerSecond(report.getTransferRateBit().doubleValue()));
            }

            @Override
            public void onError(final SpeedTestError speedTestError, final String errorMessage) {
                int x = 10;
                // called when a download/upload error occur
            }

            @Override
            public void onProgress(float percent, SpeedTestReport report) {
                // called to notify download/upload progress
                //System.out.println("[PROGRESS] progress : " + percent + "%");
                //System.out.println("[PROGRESS] rate in octet/s : " + report.getTransferRateOctet());
                //System.out.println("[PROGRESS] rate in bit/s   : " + report.getTransferRateBit());
            }
        });
    }

    private double convertBitPerSecondToMegabitPerSecond(double bitPerSecond) {
        return bitPerSecond * 0.000001;
    }

    private void displayNetworkQuality(double downloadRateInMegabitPerSecond) {
        if (getNetworkLinkSpeed() <= 15 && downloadRateInMegabitPerSecond <= 0.5) {
            mBinding.setNetworkState("Low NetworkUtility Connection <100Mbps (Please check out Wifi and Lan) \n Low Internet Connection <15 Mbps");
        } else if (downloadRateInMegabitPerSecond <= 0.5) {
            mBinding.setNetworkState("Low Internet Connection <15 Mbps");
        } else if (getNetworkLinkSpeed() <= 15) {
            mBinding.setNetworkState("Low NetworkUtility Connection <100Mbps (Please check out Wifi and Lan)");
        } else {
            mBinding.setNetworkState(null);
        }
    }
}
