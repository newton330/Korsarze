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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


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

    public void OnClickNewGame(View view)
    {
        Intent intent = new Intent(this,ConnectActivity.class);
        startActivity(intent);
    }


}
