package com.example.newto.korsarze;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class StatisticsActivity extends AppCompatActivity {
TextView UserStat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        UserStat=(TextView)findViewById(R.id.staticsset);



        Intent intent =getIntent();

        String Login;
        //String Login = intent.getStringExtra("login");
        SharedPreferences sharedPref =getSharedPreferences("NAME",Context.MODE_PRIVATE);
        Login=sharedPref.getString("NAME","");

        String statystykiwygr;
        String statystykiprzegr;
        statystykiwygr=sharedPref.getString("winsstring","");
        statystykiprzegr=sharedPref.getString("winsstring","");
        if(Login==""){
            UserStat.setText("Zaloguj się!!! ");}
        else
            UserStat.setText("Twoje statystyki piracie:\nStatystyki przgrane"+statystykiprzegr+"\nStatystyki wygrane"+statystykiwygr+".");
    }

    public void BackSettings(View view) {
        Intent intent = new Intent(this,SettingsActivity.class);
        startActivity(intent);
    }
}
