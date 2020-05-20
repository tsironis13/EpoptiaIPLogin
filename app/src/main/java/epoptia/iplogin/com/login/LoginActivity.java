package epoptia.iplogin.com.login;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.snackbar.Snackbar;

import epoptia.iplogin.com.R;
import epoptia.iplogin.com.admin.WorkStationsActivity;
import epoptia.iplogin.com.base.BaseActivity;
import epoptia.iplogin.com.databinding.ActivityLoginAdminBinding;
import epoptia.iplogin.com.pojo.ValidateAdminRequest;
import epoptia.iplogin.com.pojo.ValidateAdminResponse;
import epoptia.iplogin.com.pojo.ValidateCustomerDomainRequest;
import epoptia.iplogin.com.pojo.ValidateCustomerDomainResponse;
import epoptia.iplogin.com.retrofit.APIClient;
import epoptia.iplogin.com.retrofit.APIInterface;
import epoptia.iplogin.com.utls.SharedPrefsUtl;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by giannis on 5/9/2017.
 */

public class LoginActivity extends BaseActivity {

    //region Injections

    //endregion

    //region Private Properties

    private static final String debugTag = LoginActivity.class.getSimpleName();
    private ActivityLoginAdminBinding mBinding;
    private boolean isIpRegistered;
    private APIInterface apiInterface;
    private InputConnection ic;

    //endregion

    //region Lifecycle Methods

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_login_admin);

        if (getResources().getConfiguration().smallestScreenWidthDp >= 600) {
            mBinding.deviceIsTabletTtv.setText(getResources().getString(R.string.device_is_tablet));
        } else {
            mBinding.deviceIsTabletTtv.setText(getResources().getString(R.string.device_is_phone));
        }

        if (getSupportActionBar() != null) getSupportActionBar().hide();

        if (savedInstanceState != null) {
            isIpRegistered = savedInstanceState.getBoolean(getResources().getString(R.string.ip_registered));
            mBinding.setIsIpRegistered(isIpRegistered);
        }

        mBinding.submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitForm();
            }
        });

        // prevent system keyboard from appearing when EditText is tapped
        mBinding.ipEdt.setRawInputType(InputType.TYPE_CLASS_TEXT);
        mBinding.ipEdt.setTextIsSelectable(true);

        mBinding.admnusernameEdt.setRawInputType(InputType.TYPE_CLASS_TEXT);
        mBinding.admnusernameEdt.setTextIsSelectable(true);

        mBinding.admnpasswordEdt.setRawInputType(InputType.TYPE_CLASS_TEXT);
        mBinding.admnpasswordEdt.setTextIsSelectable(true);

        // pass the InputConnection from the EditText to the keyboard
        ic = mBinding.ipEdt.onCreateInputConnection(new EditorInfo());
        mBinding.keyboard.setInputConnection(ic);

        mBinding.ipEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBinding.keyboard.setVisibility(View.VISIBLE);
            }
        });

        mBinding.admnusernameEdt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                ic = mBinding.admnusernameEdt.onCreateInputConnection(new EditorInfo());
                mBinding.keyboard.setInputConnection(ic);

                mBinding.keyboard.setVisibility(View.VISIBLE);

                return false;
            }
        });

        mBinding.admnpasswordEdt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                ic = mBinding.admnpasswordEdt.onCreateInputConnection(new EditorInfo());
                mBinding.keyboard.setInputConnection(ic);

                mBinding.keyboard.setVisibility(View.VISIBLE);

                return false;
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(getResources().getString(R.string.ip_registered), mBinding.getIsIpRegistered());
    }

    @Override
    public void onBackPressed() {
        if (mBinding.keyboard.getVisibility() == View.VISIBLE) {
            mBinding.keyboard.setVisibility(View.GONE);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    //endregion

    //region Public Methods

    //endregion

    //region Private Methods

    private void submitForm() {
        if (mBinding.keyboard.getVisibility() == View.VISIBLE) {
            mBinding.keyboard.setVisibility(View.GONE);
        }


        if (isIpRegistered) {
            if (mBinding.admnusernameEdt.getText().toString().isEmpty() || mBinding.admnpasswordEdt.getText().toString().isEmpty()) {
                showSnackBrMsg(getResources().getString(R.string.username_password_required), mBinding.containerLnlt, Snackbar.LENGTH_SHORT);
            } else {
                if (isNetworkAvailable()) {
                    mBinding.setProcessing(true);
                    ValidateAdminRequest validateAdminRequest = new ValidateAdminRequest();
                    validateAdminRequest.setAction("validate_admin");
                    validateAdminRequest.setUsername(mBinding.admnusernameEdt.getText().toString());
                    validateAdminRequest.setPassword(mBinding.admnpasswordEdt.getText().toString());
                    //validateAdminRequest.setCustomer_domain(mBinding.subdomainEdt.getText().toString());
                    /**
                     GET List Resources
                     **/
                    apiInterface = APIClient.getClient(SharedPrefsUtl.getStringFlag(this, getResources().getString(R.string.server_ip))).create(APIInterface.class);
                    Call<ValidateAdminResponse> responseCall = apiInterface.validateAdmin(validateAdminRequest);
                    responseCall.enqueue(new Callback<ValidateAdminResponse>() {
                        @Override
                        public void onResponse(@NonNull Call<ValidateAdminResponse> call, @NonNull Response<ValidateAdminResponse> response) {
                            mBinding.setProcessing(false);
                            if (response.body() != null) {
                                if (response.body().getCode() == 200) {
                                    SharedPrefsUtl.setBooleanPref(LoginActivity.this, getResources().getString(R.string.ip_registered), true);
                                    SharedPrefsUtl.setStringPref(LoginActivity.this, getResources().getString(R.string.access_token), response.body().getAccess_token());
//                                    SharedPrefsUtl.setStringPref(LoginActivity.this, getResources().getString(R.string.admin_username), mBinding.admnusernameEdt.getText().toString());
//                                    SharedPrefsUtl.setStringPref(LoginActivity.this, getResources().getString(R.string.admin_password), mBinding.admnpasswordEdt.getText().toString());
                                    startActivity(new Intent(LoginActivity.this, WorkStationsActivity.class));
                                    finish();
                                } else {
                                    showSnackBrMsg(getResources().getString(R.string.error), mBinding.containerLnlt, Snackbar.LENGTH_SHORT);
                                }
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<ValidateAdminResponse> call, @NonNull Throwable t) {
                            mBinding.setProcessing(false);
                            showSnackBrMsg(getResources().getString(R.string.error), mBinding.containerLnlt, Snackbar.LENGTH_SHORT);
                        }
                    });
//                if (mBinding.admnusernameEdt.getText().toString().equals("provider_paths") && mBinding.admnpasswordEdt.getText().toString().equals("provider_paths")) {
//                    SharedPrefsUtl.setBooleanPref(this, getResources().getString(R.string.domain_authenticated), true);
//                    startActivity(new Intent(this, WorkStationsActivity.class));
//                    finish();
//                }
                } else {
                    showSnackBrMsg(getResources().getString(R.string.no_connection), mBinding.containerLnlt, Snackbar.LENGTH_SHORT);
                }
            }
        } else {
            //todo uncomment
            //mViewModel.validateCustomerDomain(mBinding.subdomainEdt.getText().toString());
//filesdemo.epoptia.com
  //      filesdemo
  //      filesdemo

            if (mBinding.ipEdt.getText().toString().isEmpty()) {
                showSnackBrMsg(getResources().getString(R.string.ip_required), mBinding.containerLnlt, Snackbar.LENGTH_SHORT);
            } else {
                SharedPrefsUtl.setStringPref(LoginActivity.this, getResources().getString(R.string.server_ip), mBinding.ipEdt.getText().toString());
                //SharedPrefsUtl.setBooleanPref(LoginActivity.this, getResources().getString(R.string.ip_registered), true);
                mBinding.setIsIpRegistered(true);
                isIpRegistered = true;

//                if (isNetworkAvailable()) {
//                    apiInterface = APIClient.getClient(mBinding.ipEdt.getText().toString()).create(APIInterface.class);
//                    mBinding.setProcessing(true);
//                    ValidateCustomerDomainRequest request = new ValidateCustomerDomainRequest();
//                    request.setAction("validate_customer_domain");
//                    request.setCustomer_domain(mBinding.subdomainEdt.getText().toString());
//                    /**
//                     GET List Resources
//                     **/
//                    Call<ValidateCustomerDomainResponse> responseCall = apiInterface.validateCstmrDomain(request);
//                    responseCall.enqueue(new Callback<ValidateCustomerDomainResponse>() {
//                        @Override
//                        public void onResponse(@NonNull Call<ValidateCustomerDomainResponse> call, @NonNull Response<ValidateCustomerDomainResponse> response) {
//                            mBinding.setProcessing(false);
//                            if (response.body() != null) {
//                                if (response.body().getCode() == 200) {
//                                    SharedPrefsUtl.setStringPref(LoginActivity.this, getResources().getString(R.string.subdomain), mBinding.subdomainEdt.getText().toString());
//                                    if (response.body().getAccess_token() != null) {
//                                        SharedPrefsUtl.setStringPref(LoginActivity.this, getResources().getString(R.string.access_token), response.body().getAccess_token());
//                                        SharedPrefsUtl.setBooleanPref(LoginActivity.this, getResources().getString(R.string.domain_authenticated), true);
//                                        startActivity(new Intent(LoginActivity.this, WorkStationsActivity.class));
//                                        finish();
//                                    } else {
////                                    mBinding.subdomainEdt.setEnabled(false);
//                                        mBinding.setIsdomainValid(true);
//                                        isDomainAuthenticated = true;
//                                    }
//                                } else {
//                                    showSnackBrMsg(getResources().getString(R.string.subdomain_not_valid), mBinding.containerLnlt, Snackbar.LENGTH_SHORT);
//                                }
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(@NonNull Call<ValidateCustomerDomainResponse> call, @NonNull Throwable t) {
//                            mBinding.setProcessing(false);
//                            showSnackBrMsg(getResources().getString(R.string.error), mBinding.containerLnlt, Snackbar.LENGTH_SHORT);
//                        }
//                    });
//                } else {
//                    showSnackBrMsg(getResources().getString(R.string.no_connection), mBinding.containerLnlt, Snackbar.LENGTH_SHORT);
//                }
            }
        }
    }

    private void hideSoftKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    //endregion

}
