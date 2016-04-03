package com.example.newto.korsarze;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class BattleEndActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle_end);
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
