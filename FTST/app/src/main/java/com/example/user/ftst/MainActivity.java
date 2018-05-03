package com.example.user.ftst;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MainActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tv = findViewById(R.id.tv);
        Order order1 = new Order("id: 20180312001", "15.03.2018",
                "Казахский - Корейский", "Политика",
                "500", "1 500 000");
        tv.setText(order1.getId());

        DB db = new DB(this, MyConstants.DB_TABLE_ORDER);
        db.open();
        db.addOrder(order1);
        db.get
        db.close();
    }
}
