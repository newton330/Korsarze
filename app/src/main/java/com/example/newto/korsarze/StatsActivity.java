package com.example.newto.korsarze;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class StatsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
    }

    public void OnClickOKStats(View view) {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}
