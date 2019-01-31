package epoptia.iplogin.com.retrofit;

import epoptia.iplogin.com.pojo.GetWorkStationsRequest;
import epoptia.iplogin.com.pojo.GetWorkStationsResponse;
import epoptia.iplogin.com.pojo.GetWorkersRequest;
import epoptia.iplogin.com.pojo.GetWorkersResponse;
import epoptia.iplogin.com.pojo.LogoutWorkerRequest;
import epoptia.iplogin.com.pojo.UnlockDeviceRequest;
import epoptia.iplogin.com.pojo.UnlockDeviceResponse;
import epoptia.iplogin.com.pojo.UploadImageResponse;
import epoptia.iplogin.com.pojo.ValidateAdminRequest;
import epoptia.iplogin.com.pojo.ValidateAdminResponse;
import epoptia.iplogin.com.pojo.ValidateCustomerDomainRequest;
import epoptia.iplogin.com.pojo.ValidateCustomerDomainResponse;
import epoptia.iplogin.com.pojo.ValidateWorkerRequest;
import epoptia.iplogin.com.pojo.ValidateWorkerResponse;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by giannis on 18/9/2017.
 */

public interface APIInterface {

    @POST("actions.php")
    Call<ValidateCustomerDomainResponse> validateCstmrDomain(@Body ValidateCustomerDomainRequest request);

    @POST("actions.php")
    Call<ValidateAdminResponse> validateAdmin(@Body ValidateAdminRequest request);

    @POST("actions.php")
    Call<GetWorkStationsResponse> getWorkStations(@Body GetWorkStationsRequest request);

    @POST("actions.php")
    Call<GetWorkersResponse> getWorkers(@Body GetWorkersRequest request);

    @POST("actions.php")
    Call<ValidateWorkerResponse> validateWorker(@Body ValidateWorkerRequest request);

    @POST("actions.php")
    Call<ValidateAdminResponse> logoutWorker(@Body LogoutWorkerRequest request);

    @POST("actions.php")
    Call<UnlockDeviceResponse> unlockDevice(@Body UnlockDeviceRequest request);

    @Multipart
    @POST("actions.php")
    Call<UploadImageResponse> uploadImage(@Part("action") RequestBody action, @Part("access_token") RequestBody token, @Part("order_line_track_id") RequestBody id, @Part MultipartBody.Part image);
}
