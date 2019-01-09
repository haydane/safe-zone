package com.example.haydane.safezone;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.haydane.safezone.response.ResponseUser;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    String TAG = "";
    EditText email,password;
    UserApi api;
    Button btn_signin;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initRetrofit("http://192.168.123.8:8000/");
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.signin_input_email);
        password = findViewById(R.id.signin_input_password);
        btn_signin = findViewById(R.id.btn_signin);
        btn_signin.setOnClickListener(this);

        findViewById(R.id.login_layout).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
                return true;
            }
        });

    }

    // OKay done please use that method

    private Call<ResponseUser> checkUser(String user, String pass) {
        MultipartBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("username",user)
                .addFormDataPart("password",pass)
                .build();
        return api.getUser(body,"safezone");
    }

    private void initRetrofit(String baseurl) {
        Retrofit retrofit  = new Retrofit.Builder()
                .baseUrl(baseurl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(UserApi.class);

    }

    @Override
    public void onClick(final View v) {
        checkUser(email.getText().toString(),password.getText().toString()).enqueue(new Callback<ResponseUser>() {
            @Override
            public void onResponse(Call<ResponseUser> call, Response<ResponseUser> response) {
                Log.d("ResponseUser",response.body().toString());
                ResponseUser user = response.body();
                if(user != null){
                    if(user.getSuccess().equals("True"))
                    startActivity(new Intent(v.getContext(),MainActivity.class));
                }

            }

            @Override
            public void onFailure(Call<ResponseUser> call, Throwable t) {
                Log.wtf("Error",t.getLocalizedMessage());
            }
        });
    }
}
