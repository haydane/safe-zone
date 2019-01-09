package com.example.haydane.safezone;

import com.example.haydane.safezone.response.ResponseUser;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UserApi {

    @POST("/checkin/login/")
    Call<ResponseUser> getUser(@Body RequestBody body, @Header("authorization-key") String header);

}
