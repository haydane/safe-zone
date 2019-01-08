package com.example.haydane.safezone;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiService {
    @Headers({"content-type:  multipart/form-data", "authorization-key: safezone"})
    @POST("/checkin/")
    Call<ResponseBody> postCheckin(@Body RequestBody requestBody);
}
