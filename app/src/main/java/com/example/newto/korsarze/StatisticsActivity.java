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
    int myShipAll;
    int opponentShipAll;
    String Login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        UserStat=(TextView)findViewById(R.id.staticsset);

        SharedPreferences sharedPref = getSharedPreferences("NAME", Context.MODE_PRIVATE);
        Login = sharedPref.getString("NAME", "");String statystykiwygr;
        String statystykiprzegr;
        String myShipAllS, opponentShipAllS;
        statystykiwygr = sharedPref.getString("winsstring", "");
        statystykiprzegr = sharedPref.getString("lossstring", "");
        myShipAllS = sharedPref.getString("myShips", "");
        opponentShipAllS = sharedPref.getString("opShips", "");

        if(statystykiwygr=="")
            statystykiwygr="0";
        if(statystykiprzegr=="")
            statystykiprzegr="0";
        if(myShipAllS=="")
            myShipAllS="0";
        if(opponentShipAllS=="")
            opponentShipAllS="0";

        wins = Integer.parseInt(statystykiwygr);
        loss = Integer.parseInt(statystykiprzegr);
        myShipAll = Integer.parseInt(myShipAllS);
        opponentShipAll = Integer.parseInt(opponentShipAllS);

        UserStat.setText("Zwycięstwa: "+wins+"\nPorażki: "+loss+"\nZatopienia (łącznie): "+opponentShipAll+"\nStraty (łącznie): "+myShipAll);
    }

    public void BackSettings(View view) {
        Intent intent = new Intent(this,SettingsActivity.class);
        startActivity(intent);
    }
}
