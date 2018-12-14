package com.example.learning.basicmaths;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/*
in the example I've seen to start another layout, they
have extends the class with Activity, use AppCompatActivity
will show an error (?)
 */

public class Menu extends AppCompatActivity {

    FileManage f;
    Context context;

    private void initiateLayoutM(int o, View v){
        Intent i = new Intent(v.getContext(), MainActivity.class);
        i.putExtra("intOption", o);
        startActivity(i);
    }

    private void initiateLayoutR(View v){
        Intent i = new Intent(v.getContext(), Results.class);
        startActivity(i);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //delete file
        /*f = new FileManage();
        context = getApplicationContext();
        f.deleteFile(context);*/

        final Button buttonAd = findViewById(R.id.button_Add);
        final Button buttonSu = findViewById(R.id.button_Subtract);
        final Button buttonMu = findViewById(R.id.button_Multiply);
        final Button buttonDi = findViewById(R.id.button_Division);
        final Button buttonRe = findViewById(R.id.button_Results);

        buttonAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //option = 1
                initiateLayoutM(1, v);
            }
        });
        buttonSu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //option = 2
                initiateLayoutM(2, v);
            }
        });
        buttonMu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //option = 3
                initiateLayoutM(3, v);
            }
        });
        buttonDi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //option = 4
                initiateLayoutM(4, v);
            }
        });
        buttonRe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //change layout to get last results
                //Toast.makeText(Menu.this, "No results yet", Toast.LENGTH_SHORT).show();
                initiateLayoutR(v);
            }
        });

    }

}
