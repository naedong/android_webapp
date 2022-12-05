package kr.co.project.api;

import kr.co.project.vo.MembSign;
import kr.co.project.vo.SignDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

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





}
