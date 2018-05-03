package com.example.user.fts;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.widget.PullRefreshLayout;

public class OrderAcceptedActivity extends AppCompatActivity {

    MyPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_accepted);

        final PullRefreshLayout layout = (PullRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        layout.setRefreshStyle(PullRefreshLayout.STYLE_MATERIAL);
        layout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //TODO: Обновление страницы
                Log.d(MyContants.TAG, "onRefresh start");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.d(MyContants.TAG, "onRefresh end");
                layout.setRefreshing(false);
            }
        });

        preferences = new MyPreferences(this, "tablevar");
        TextView yourId = findViewById(R.id.yourId);
        yourId.append(" " + preferences.getVariable(MyContants.DB_TABLE_ORDER_ID));
    }

    @Override
    public void onBackPressed() {
        if (preferences.getVariable(MyContants.DB_TABLE_JUST_ORDERED).equals("1")) {
            super.onBackPressed();
            Intent intent = new Intent(this, SplashScreenActivity.class);
            intent.putExtra("just_ordered", true);
            startActivity(intent);
        }
        finish();
    }
}
