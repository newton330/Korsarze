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
    int wins;
    int loss;
    int myShipCounter2;
    int opponentShipCounter2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        UserStat=(TextView)findViewById(R.id.staticsset);

        Bundle myShipCounter1 = getIntent().getExtras();
        Bundle opponentShipCounter1 = getIntent().getExtras();
        myShipCounter2 =myShipCounter1 .getInt("myShipCounter");
        opponentShipCounter2 =opponentShipCounter1 .getInt("opponentShipCounter");

       Intent intent =getIntent();
if(((myShipCounter2)-20)>(opponentShipCounter2-20)){
    wins+=1;
}
        else
{
    loss+=1;
}

        String winsstring=Integer.toString(wins);
        String lossstring=Integer.toString(loss);
        SharedPreferences sharedPreferences = getSharedPreferences("winsstring", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("winsstring",winsstring);
        editor.putString("lossstring",lossstring);
        editor.commit();


        ///Wyswietlanie trzeba zrobic

//        String Login;
//        //String Login = intent.getStringExtra("login");
//        SharedPreferences sharedPref =getSharedPreferences("NAME",Context.MODE_PRIVATE);
//        Login=sharedPref.getString("NAME","");
//
//        String statystykiwygr;
//        String statystykiprzegr;
//        statystykiwygr=sharedPref.getString("winsstring","");
//        statystykiprzegr=sharedPref.getString("lossstring","");
//        if(Login==""){
//            UserStat.setText("Zaloguj się!!! ");}
//        else
//            UserStat.setText("Twoje statystyki piracie:\nStatystyki przgrane"+statystykiprzegr+"\nStatystyki wygrane"+statystykiwygr+".");
    }

    public void BackSettings(View view) {
        Intent intent = new Intent(this,SettingsActivity.class);
        startActivity(intent);
    }
}
