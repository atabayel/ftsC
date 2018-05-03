package com.example.user.fts;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class SplashScreenActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        MyPreferences preferences = new MyPreferences(this, "tablevar");
        if (getIntent().getBooleanExtra("just_ordered", false)) {
            preferences.setVariable(MyContants.DB_TABLE_JUST_ORDERED, "0");
        }


        if (preferences.getVariable(MyContants.DB_TABLE_IS_ENTERED).equals("1")) {
            if (preferences.getVariable(MyContants.DB_TABLE_IS_ORDERED).equals("1")) {
                Intent intent = new Intent(this, OrderAcceptedActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
        } else {
            preferences.setVariable(MyContants.DB_TABLE_IS_RUNED, "0");
            Intent intent = new Intent(this, AuthenticationActivity.class);
            startActivity(intent);
        }

        finish();

//        SharedPreferences sPref = getSharedPreferences(MyContants.PREFERENCE_USER_IS_ENTERED, MODE_PRIVATE);
//        boolean isEntered = sPref.getBoolean(MyContants.PREFERENCE_USER_IS_ENTERED, false);
//        SharedPreferences sPref1 = getSharedPreferences(MyContants.PREFERENCE_IS_ORDERED, MODE_PRIVATE);
//        boolean isOrdered = sPref1.getBoolean(MyContants.PREFERENCE_IS_ORDERED, false);
//
//        if (isEntered) {
//            if (isOrdered) {
//                SharedPreferences sPref2 = getSharedPreferences(MyContants.ORDER_ID, MODE_PRIVATE);
//                String id = sPref2.getString(MyContants.ORDER_ID, "unknown");
//                Intent intent = new Intent(this, OrderAcceptedActivity.class);
//                intent.putExtra("id", id);
//                startActivity(intent);
//                finish();
//            } else {
//                Intent intent = new Intent(this, MainActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        } else {
//            Intent intent = new Intent(this, AutenticationActivity.class);
//            startActivity(intent);
//            finish();
//        }
    }

}
