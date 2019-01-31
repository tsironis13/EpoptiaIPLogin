package epoptia.iplogin.com.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by giannis on 27/8/2017.
 */

public abstract class BaseActivity extends AppCompatActivity {

    //region Private Methods



    //endregion

    //region Lifecycle Methods

    /**
     * Get instance of MyApplication and check if it implements HasActivityInjector.
     * Invoke AndroidInjector<Activity> activityInjector() to get instance of DispatchingAndroidInjector<Activity>,
     * look for injector for activity in the map of injectors stored in DispatchingAndroidInjector<Activity>.
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    //endregion

    //region Public Methods

    public void showSnackBrMsg(String msg, View container, int length) {
        Snackbar snackbar = Snackbar.make(container, msg, length);
        snackbar.show();
    }

    //endregion

}
