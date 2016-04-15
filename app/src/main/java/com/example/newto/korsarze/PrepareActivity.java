package com.example.newto.korsarze;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

public class PrepareActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prepare);


        GridView gridView = (GridView) findViewById(R.id.prepareGrid);
        final ImageAdapter imageAdapterPlayer = new ImageAdapter(this);
        gridView.setAdapter(imageAdapterPlayer);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id){
                OnWaterClick(v,position);
            }
        });
    }


    public void OnClickPlay(View view)
    {
        Intent intent = new Intent(this,BattleActivity.class);
        startActivity(intent);
    }

    public void OnWaterClick(View v, int position)
    {
        ImageView imageView = (ImageView) v;
        imageView.setImageResource(R.drawable.hit);
        Toast.makeText(PrepareActivity.this,Integer.toString(position),Toast.LENGTH_SHORT).show();
    }
}
