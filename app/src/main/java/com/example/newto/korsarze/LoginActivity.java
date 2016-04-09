package com.example.newto.korsarze;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {
    EditText fav;
    TextView textout;
    public String message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        fav= (EditText) findViewById(R.id.Podajnick);

        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        String name = sharedPref.getString("NAME","");
//        if (!name.isEmpty()){
//            finish();
//            Intent intent =new Intent(this,MainActivity.class);
//            startActivity(intent);
//        }

    }


    public void OkLogowanie(View view) {

        String name = fav.getText().toString();

        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (!name.isEmpty()){
            editor.putString("NAME",name);
            editor.commit();
        }
        String message ="Moja nazwa to "+name;
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);


    }
    }


