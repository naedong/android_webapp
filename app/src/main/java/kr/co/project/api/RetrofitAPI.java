package kr.co.project.api;


import kr.co.project.vo.FCMTo;
import kr.co.project.vo.MembLogin;
import kr.co.project.vo.MembSign;
import kr.co.project.vo.SignDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RetrofitAPI {

    @GET("login/checkid/{membId}")
    Call<Boolean> IdChk(
            @Path("membId") String membId);

    @POST("login/send-one")
    Call<Boolean> sendSms(
            @Body SignDTO signDTO
            );

    @POST("login/send-two")
    Call<Boolean> SmsVerification(
            @Body SignDTO signDTO
    );


    @POST("login/sign")
    Call<Boolean> insertUser(
            @Body MembSign membSign
    );

    @POST("login/login")
    Call<MembSign> webLogin(
        @Body MembLogin.MembLogins membLogin
    );

    @Headers({"Authorization: key=AAAAUjMVNdI:APA91bE_Ck9U30Vn3uQ4iX8pFq3nDILK269d41-mQtdS3Civ7dxJ4QdB6W7q7Lo6i9uf6nptfRVjKZwW5DjTFqX7UKRPtzVsmEw3YdHyYbQU7dt_tP-5-ClVbGt-SpxHNFP3tr62T39D"})
    @POST("fcm/send")
    Call<String> sendFCM(@Body FCMTo sendtoken);

}
