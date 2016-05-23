package com.example.newto.korsarze;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.newto.korsarze.bluetooth.BluetoothDeviceDataStructure;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

public class BattleActivity extends AppCompatActivity {
    TextView infoText;
    Button buttonText;
    int myMap[] = new int[100];
    int opponentMap[] = new int[100];
    int myShipCounter=20;
    int opponentShipCounter=20;
    int myTurn=0;
    int tarfienie=0;
    int wins=0;//Przekazywane do statystyk
    int loss=0;//Przekazywane do statystyk
    //Trzeba inkrementować po wygranej
    BluetoothControl bluetoothControl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle);
        infoText=(TextView) findViewById(R.id.battleInfoText);
        buttonText=(Button) findViewById(R.id.fireButton);
        buttonText.setText("Wyjście");
        for (int i =0;i<100;i++)
            opponentMap[i]=4;
        Bundle bundle = getIntent().getExtras();
        myMap = bundle.getIntArray("myMap");

        final GridView gridView = (GridView) findViewById(R.id.battleGrid);
        final ImageAdapterOpponent imageAdapterOpponent = new ImageAdapterOpponent(this);
        gridView.setAdapter(imageAdapterOpponent);
        final List<Integer> bluetoothBuffer = new ArrayList<>();

        Observer bluetoothObserver = new Observer() {
            @Override
            public void update(Observable observable, Object data) {
                bluetoothBuffer.add(bluetoothControl.getNextData());
            }
        };

        bluetoothControl = BluetoothControl.getBluetoothControl();
        bluetoothControl.setObserver(bluetoothObserver);

        int myRand = 0;
        int opRand = 0;
        Random rand = new Random();
        infoText.setText("Aby rozpocząć dotknj planszę");
        while(myRand==opRand)
        {
            myRand = rand.nextInt();
            bluetoothControl.write((myRand+"").getBytes());
            opRand = bluetoothControl.getNextData();
            if(myRand>opRand)
                myTurn=1;
            if(myRand<opRand)
                myTurn=0;
        }

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
        //bluetoothControl.startListening();
        //bluetoothControl.connect();
        //bluetoothControl.getNextData();

        if(myMap[position]==2)
        {
///////////////////////////////////////// wysłanie trafienia///////////////////////////////////////////////////////////////////
            myMap[position]=3;
            myShipCounter--;
          //  bluetoothControl.write("Twoja tura".getBytes());
          //  bluetoothControl.
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
        ///////////////////////zapisywanie do statystyk//////////////////////
        String winsstring=Integer.toString(wins);
        String lossstring=Integer.toString(loss);
        SharedPreferences sharedPreferences = getSharedPreferences("winsstring", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("winsstring",winsstring);
        editor.putString("lossstring",lossstring);
        editor.commit();


        Intent intent = new Intent(this,BattleEndActivity.class);
        intent.putExtra("winsstring",winsstring);//Pamiętaj o usunięciu testowych wartosci w LoginActivity
        intent.putExtra("lossstring",lossstring);//Pamiętaj o usunięciu testowych wartosci w LoginActivity
        startActivity(intent);
    }
}
