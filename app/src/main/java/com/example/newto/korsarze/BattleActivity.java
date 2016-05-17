package com.example.newto.korsarze;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newto.korsarze.bluetooth.BluetoothControl;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class BattleActivity extends AppCompatActivity {
    TextView infoText;
    Button buttonText;
    int myMap[] = new int[100];
    int opponentMap[] = new int[100];
    int myShipCounter=20;
    int opponentShipCounter=20;
    int myTurn=0;
    int tarfienie=0;
BluetoothControl bluetoothControl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle);
        infoText=(TextView) findViewById(R.id.battleInfoText);
        buttonText=(Button) findViewById(R.id.fireButton);
        buttonText.setText("Wyjście");
       int liczba =  Integer.getInteger("1");
        for (int i =0;i<100;i++)
            opponentMap[i]=4;
bluetoothControl.write((""position+"").getBytes());

        final GridView gridView = (GridView) findViewById(R.id.battleGrid);
        final ImageAdapter imageAdapterPlayer = new ImageAdapter(this);
        gridView.setAdapter(imageAdapterPlayer);
        final List<Integer> bluetoothBuffer = new ArrayList<>();
Observer bluetoothObserver = new Observer() {
    @Override
    public void update(Observable observable, Object data) {
        bluetoothBuffer.add(bluetoothControl.getNextData());
    }
};

        infoText.setText("Aby rozpocząć dotknj planszę");
        bluetoothControl = BluetoothControl.getBluetoothControl();
        bluetoothControl.setObserver(bluetoothObserver);
//////////////////////////////////////////// Trzeba ustalić czyja jest tura ////////////////////////////////////////////////////

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {
                if(myTurn!=-1)
                {
                    showShips(v);
                    if (myTurn == 0)
                        opponentMove();
                    else
                        myMove(v,position);
                }
            }

        });
    }


    public void opponentMove()
    {
        //showShips(v);
        myTurn=-1;
        infoText.setText("Ruch przeciwnika");
        int position=10;////////////////////////////////////////////tymczasowo
///////////////////////////////////////// Otrzymanie pozycji///////////////////////////////////////////////////////////////////
        bluetoothControl.startListening();
        bluetoothControl.connect();
        bluetoothControl.getNextData();

        if(myMap[position]==2)
        {
///////////////////////////////////////// wysłanie trafienia///////////////////////////////////////////////////////////////////
            myMap[position]=3;
            myShipCounter--;
            //ImageView imageView = (ImageView) v;
            //imageView.setImageResource(mThumbIds[opponentMap[position]]);
            bluetoothControl.write("Twoja tura".getBytes());
            bluetoothControl.
            if(myShipCounter==0)
                endActivity(0);
        }
        else
        {
///////////////////////////////////////// wysłanie trafienia///////////////////////////////////////////////////////////////////
            myMap[position] = 1;
            //ImageView imageView = (ImageView) v;
            //imageView.setImageResource(mThumbIds[opponentMap[position]]);
            infoText.setText("Twój ruch");
            myTurn = 1;
        }
    }

    public void myMove(View v,int position)
    {
        showShips(v);
        myTurn=-1;
        infoText.setText("Twój ruch");
        if(opponentMap[position]==4)
        {
//////////////////////////////////////////Wysłanie pozycji//// Otrzymanie trafienia ////////////////////////////////////////////
            if(tarfienie==1)
            {
                opponentMap[position]=3;
                opponentShipCounter--;
                ImageView imageView = (ImageView) v;
                imageView.setImageResource(mThumbIds[opponentMap[position]]);
                if(opponentShipCounter==0)
                    endActivity(1);
                else
                    myTurn=1;
            }
            else
            {
                opponentMap[position]=1;
                ImageView imageView = (ImageView) v;
                imageView.setImageResource(mThumbIds[opponentMap[position]]);
                myTurn=0;
                opponentMove();
            }
        }
        else
        {
            Toast.makeText(BattleActivity.this, "Wybierz wolne pole", Toast.LENGTH_SHORT).show();
            myTurn=1;
        }
    }

    public void showShips(View v)
    {
        if(myTurn==0)
            for (int i =0;i<100;i++)
            {
                ImageView imageView = (ImageView) v;
                imageView.setImageResource(mThumbIds[opponentMap[i]]);
            }
        else
            for (int i =0;i<100;i++)
            {
                ImageView imageView = (ImageView) v;
                imageView.setImageResource(mThumbIds[myMap[i]]);
            }

        //Toast.makeText(PrepareActivity.this,Integer.toString(position),Toast.LENGTH_SHORT).show();
    }

    public Integer[] mThumbIds = new Integer[]{
            R.drawable.water, R.drawable.water, R.drawable.ship, R.drawable.hit, R.drawable.unknown
    };

    public void OnClickFire(View view)
    {
        Intent intent = new Intent(this,BattleEndActivity.class);
        startActivity(intent);
    }

    public void endActivity(int win)
    {
        Intent intent = new Intent(this,BattleEndActivity.class);
        startActivity(intent);
    }
}
