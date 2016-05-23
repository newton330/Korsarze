package com.example.newto.korsarze;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class BattleEndActivity extends AppCompatActivity {
    TextView UserStat;
    TextView summaryTitle;
    int wins;
    int loss;
    int myShipCounter;
    int opponentShipCounter;
    int myShipAll;
    int opponentShipAll;
    String Login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle_end);

        UserStat = (TextView) findViewById(R.id.summaryText);
        summaryTitle = (TextView) findViewById(R.id.summaryTitle);
        Bundle myShipCounter1 = getIntent().getExtras();
        Bundle opponentShipCounter1 = getIntent().getExtras();
        myShipCounter = 20-myShipCounter1.getInt("myShipCounter");
        opponentShipCounter =20-opponentShipCounter1.getInt("opponentShipCounter");

        SharedPreferences sharedPref = getSharedPreferences("NAME", Context.MODE_PRIVATE);
        Login = sharedPref.getString("NAME", "");
        String statystykiwygr;
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
        myShipAll = Integer.parseInt(myShipAllS) + myShipCounter;
        opponentShipAll = Integer.parseInt(opponentShipAllS) + opponentShipCounter;

        if (myShipCounter < opponentShipCounter)
        {
            wins += 1;
            summaryTitle.setText("Zwycięstwo");
        }
        else
        {
            loss += 1;
            summaryTitle.setText("Porażka");
        }
        UserStat.setText(Login+"\n\nZatopienia: "+opponentShipCounter+"\nStraty: "+myShipCounter+"\n\nZwycięstwa: "+wins+"\nPorażki: "+loss+"\nZatopienia (łącznie): "+opponentShipAll+"\nStraty (łącznie): "+myShipAll);

        SharedPreferences sharedPreferences = getSharedPreferences("NAME", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("NAME",Login);
        editor.putString("winsstring",Integer.toString(wins));
        editor.putString("lossstring",Integer.toString(loss));
        editor.putString("myShips",Integer.toString(myShipAll));
        editor.putString("opShips",Integer.toString(opponentShipAll));
        editor.commit();
    }

    public void OnClickEnd(View view)
    {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    public void OnClickReplay(View view)
    {
        Intent intent = new Intent(this,PrepareActivity.class);
        startActivity(intent);
    }
}
