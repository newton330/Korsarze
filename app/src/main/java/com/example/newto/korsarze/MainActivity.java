package com.example.newto.korsarze;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

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
