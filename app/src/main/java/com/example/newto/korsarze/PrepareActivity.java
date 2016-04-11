package com.example.newto.korsarze;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

public class PrepareActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prepare);

        GridView gridView = (GridView) findViewById(R.id.prepareGrid);
        ImageAdapter imageAdapterPlayer = new ImageAdapter(this);
        gridView.setAdapter(imageAdapterPlayer);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id){
                Toast.makeText(PrepareActivity.this,Integer.toString(position),Toast.LENGTH_SHORT).show();

            }
        });
    }


    public void OnClickPlay(View view)
    {
        Intent intent = new Intent(this,BattleActivity.class);
        startActivity(intent);
    }
}
