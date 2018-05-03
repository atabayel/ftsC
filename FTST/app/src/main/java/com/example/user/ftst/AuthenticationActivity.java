package com.example.user.ftst;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AuthenticationActivity extends AppCompatActivity {

    EditText login;
    EditText pswd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        login = findViewById(R.id.etLogin);
        pswd = findViewById(R.id.etPswd);
    }

    public void onClick(View view) {
        if (checkAutorization(login.getText().toString(), pswd.getText().toString())) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    private boolean checkAutorization(String login, String pswd) {
        //TODO: Отправка данных и получение ответа (проверка логина и пароля)
        if (login.equals("admin") && pswd.equals("pswd")) {
            return true;
        } else {
            return false;
        }
    }
}
