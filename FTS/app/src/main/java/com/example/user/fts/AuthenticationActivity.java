package com.example.user.fts;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AuthenticationActivity extends AppCompatActivity {

    MyPreferences preferences;
    Switch isAutoEnter;
    EditText phoneNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autentication);
        preferences = new MyPreferences(this, "tablevar");
        phoneNum = findViewById(R.id.edPhoneNumber);
    }

    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.btnEnter:
                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(MyContants.BASE_URL) // Адрес сервера
                        .addConverterFactory(GsonConverterFactory.create(gson)) // говорим ретрофиту что для сериализации необходимо использовать GSON
                        .build();

                API api = retrofit.create(API.class);

                Call<ServerResponse> call = api.authentication(phoneNum.getText().toString());

                if (MyMethods.isNetworkOnline(this)) {
                    try {
                        call.enqueue(new Callback<ServerResponse>() {
                            @Override
                            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                                if (response.isSuccessful()) {
                                    if (response.body().getResponse().equals("error")) {
                                        showToast(getResources().getString(R.string.auth_error));
                                    } else if (response.body().getResponse().equals("denied")) {
                                        showToast(getResources().getString(R.string.auth_false));
//                                    } else if (response.body().getResponse().equals("allowed")) {
                                    } else {
                                        enterToApp(response.body().getResponse());
                                    }
                                } else {
                                    Log.d(MyContants.TAG, "failure response is: " + response.raw().toString());
                                }
                            }

                            @Override
                            public void onFailure(Call<ServerResponse> call, Throwable t) {

                            }
                        });
                    } catch (Exception e) {
                        Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(this, getResources().getString(R.string.inet_off), Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.btnReg:
                intent = new Intent(this, RegistrationActivity.class);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    private void enterToApp(String id) {
        Intent intent;
        preferences.setVariable(MyContants.DB_TABLE_IS_ENTERED, "1");
        preferences.setVariable(MyContants.DB_TABLE_MY_ID, id);
        if (preferences.getVariable(MyContants.DB_TABLE_IS_ORDERED).equals("1")) {
            intent = new Intent(this, OrderAcceptedActivity.class);
            startActivity(intent);
            finish();
        } else {
            intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        preferences.setVariable(MyContants.DB_TABLE_IS_ENTERED, "1");
    }
}
