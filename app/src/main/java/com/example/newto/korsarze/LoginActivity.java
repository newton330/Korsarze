package com.example.newto.korsarze;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {
    EditText edittext1;
    Button Ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edittext1= (EditText) findViewById(R.id.Podajnick);

        Ok=(Button) findViewById(R.id.Okbutton);
    }


    public void OkLogowanie(View view) {

        String name = edittext1.getText().toString();

        SharedPreferences sharedPreferences = getSharedPreferences("NAME",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("NAME",name);
            editor.commit();




        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra("login",name);
        startActivity(intent);
    }


    }


