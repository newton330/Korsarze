package com.example.newto.korsarze;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.jar.Attributes;

public class MainActivity extends AppCompatActivity {
    TextView User;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        User=(TextView) findViewById(R.id.nazwauzytkownika);

        Intent intent =getIntent();
        String Login;
        //String Login = intent.getStringExtra("login");
        SharedPreferences sharedPref =getSharedPreferences("NAME",Context.MODE_PRIVATE);
        Login=sharedPref.getString("NAME","");
//logowanie


        if(Login==""){
            User.setText("Zaloguj się!!! ");}
        else
            User.setText(""+Login+". Czas zatopić parę statków");
//koniec logowania
    }

    public void onClickOAplikacji(View view)
    {
        Intent intent = new Intent(this,AboutActivity.class);
        startActivity(intent);
    }

    public void onClickUstwienia(View view)
    {
        Intent intent = new Intent(this,SettingsActivity.class);
        startActivity(intent);
    }

    public void OnClickNewGame(View view) {
        Intent intent = new Intent(this, ConnectActivity.class);////////////zmiana tymczasowa
        startActivity(intent);

    }
}
