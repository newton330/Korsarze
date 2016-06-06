package com.example.newto.korsarze;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class AboutActivity extends AppCompatActivity {
    //TODO

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }

    public void OnClickOkAbout(View view)
    {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    public void onAboutImageClick(View view)
    {
        Toast.makeText(AboutActivity.this,"Description	: A stereotypical caricature of a pirate. \n Author	Caricature by J.J., SVG file by Gustavb \n \n Description	: Flaga pirata Edwarda England \n Author	WarX, edited by Manuel Strehl",Toast.LENGTH_LONG).show();
    }
}
