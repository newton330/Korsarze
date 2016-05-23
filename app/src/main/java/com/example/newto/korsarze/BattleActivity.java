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

        GridView gridView = (GridView) findViewById(R.id.battleGrid);
        final ImageAdapterOpponent imageAdapterOpponent = new ImageAdapterOpponent(this);
        gridView.setAdapter(imageAdapterOpponent);
        final List<Integer> bluetoothBuffer = new ArrayList<>();

        Observer bluetoothObserver = new Observer() ///////////////////////////////////////////
        {
            @Override
            public void update(Observable observable, Object data) {
                bluetoothBuffer.add(bluetoothControl.getNextData());
            }
        };

        bluetoothControl = BluetoothControl.getBluetoothControl();//////////////////////////
        bluetoothControl.setObserver(bluetoothObserver);/////////////////////////

        int myRand = 0;
        int opRand = 0;
        Random rand = new Random();
        infoText.setText("Aby rozpocząć dotknj planszę");
        while(myRand==opRand)
        {
            myRand = rand.nextInt();
            bluetoothControl.write((myRand+"").getBytes());////////////////////
            bluetoothControl.startListening();////////////////////////
            opRand = bluetoothControl.getNextData();//////////////////////
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
        myTurn=-1;
        infoText.setText("Ruch przeciwnika");
        int position;
        bluetoothControl.startListening();/////////////////
        position= bluetoothControl.getNextData();//////////////////
        if(myMap[position]==2)
        {
            bluetoothControl.write("1".getBytes());
            myMap[position]=3;
            myShipCounter--;
            if(myShipCounter==0)
                endActivity(0);
            else
                opponentMove();
        }
        else
        {
            bluetoothControl.write("0".getBytes());
            myMap[position] = 1;
            infoText.setText("Twój ruch");
            myTurn = 1;
        }
    }

    public void myMove(View v,int position)
    {
        myTurn=-1;
        infoText.setText("Twój ruch");
        if(opponentMap[position]==4)
        {
            bluetoothControl.write((position+"").getBytes());//////////////////////
            bluetoothControl.startListening();/////////////////
            position= bluetoothControl.getNextData();//////////////////
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

    public Integer[] mThumbIds = new Integer[]{
            R.drawable.water, R.drawable.water, R.drawable.ship, R.drawable.hit, R.drawable.unknown
    };

    public void OnClickFire(View view)
    {

        Intent intent1 = new Intent(getApplicationContext(), BattleActivity.class);
        Bundle myShipCounter1 =  new Bundle();
        Bundle opponentShipCounter1 =  new Bundle();
        myShipCounter1.putInt("myShipCounter",myShipCounter);
        opponentShipCounter1.putInt("opponentShipCounter",opponentShipCounter);
        intent1.putExtras(myShipCounter1);
        intent1.putExtras(opponentShipCounter1);
        startActivity(intent1);

        Intent intent = new Intent(this,BattleEndActivity.class);
        startActivity(intent);

    }

    public void endActivity(int win)
    {
        Intent intent1 = new Intent(getApplicationContext(), BattleActivity.class);
        Bundle myShipCounter1 =  new Bundle();
        Bundle opponentShipCounter1 =  new Bundle();
        myShipCounter1.putInt("myShipCounter",myShipCounter);
        opponentShipCounter1.putInt("opponentShipCounter",opponentShipCounter);
        intent1.putExtras(myShipCounter1);
        intent1.putExtras(opponentShipCounter1);
        startActivity(intent1);

        Intent intent = new Intent(this,BattleEndActivity.class);
        startActivity(intent);
    }
}
