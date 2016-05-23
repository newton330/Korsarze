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
    int clickNumber=0;
    int map[] = new int[100];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prepare);
        for (int i =0;i<100;i++)
            map[i]=0;

        final GridView gridView = (GridView) findViewById(R.id.prepareGrid);
        final ImageAdapter imageAdapterPlayer = new ImageAdapter(this);
        gridView.setAdapter(imageAdapterPlayer);


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id){
                if(map[position]==0)
                    if(clickNumber<20)
                    {
                        map[position]=2;
                        clickNumber++;
                        SetShip(v, position);
                    }
                    else
                        Toast.makeText(PrepareActivity.this,"Twoja flota jest już wystarczająco duża",Toast.LENGTH_SHORT).show();
                else if(map[position]==2)
                {
                    map[position]=0;
                    clickNumber--;
                    SetShip(v, position);
                }
            }
        });
    }

    public void OnClickPlay(View view)
    {
        if (clickNumber == 20)
        {
            Intent intent = new Intent(getApplicationContext(), BattleActivity.class);
            Bundle bundle =  new Bundle();
            bundle.putIntArray("myMap",map);
            intent.putExtras(bundle);
            startActivity(intent);
        }
        else
            Toast.makeText(PrepareActivity.this,"Twoja flota musi zajmować dokładnie 20 pól. Umieść więcej statków",Toast.LENGTH_SHORT).show();
    }

    public void SetShip(View v, int position)
    {
        ImageView imageView = (ImageView) v;
        imageView.setImageResource(mThumbIds[map[position]]);
        //Toast.makeText(PrepareActivity.this,Integer.toString(position),Toast.LENGTH_SHORT).show();
    }
    public int Ship4(int position)
    {
        clickNumber++;
        if(clickNumber==3)
        {
            return 1;
        }
        else
        return 0;
    }
    public int Ships(int position,long ID)
    {
        Ship4(position);
        return 1;
    }
    public Integer[] mThumbIds = new Integer[]{
            R.drawable.water, R.drawable.water, R.drawable.ship, R.drawable.hit, R.drawable.unknown
    };

    public void PrepareHelp(View view)
    {
        Toast.makeText(PrepareActivity.this,"Umieść lub usuń swoje statki dotykając wybrane pola",Toast.LENGTH_SHORT).show();
    }
}
