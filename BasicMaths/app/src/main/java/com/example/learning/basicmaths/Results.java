package com.example.learning.basicmaths;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.BundleCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Results extends AppCompatActivity {

    FileManage f;
    TextView [][] textField;
    Context context;

    private void setTextField(){
        String [] d = f.getDataFile(context);
        for(int i = 0; i < d.length; i++){ //each info saved has 5 data
            textField[i/5][i%5].setText(d[i]);
        }
    }

    protected void onCreate(Bundle savedInstanceInstate){
        super.onCreate(savedInstanceInstate);
        setContentView(R.layout.activity_results);

        f = new FileManage();
        context = getApplicationContext();
        f.getDataFile(context);
        textField = new TextView[10][5];
        //set link text with the table of results
        //first row
        textField[0][0] = findViewById(R.id.r1);
        textField[0][1] = findViewById(R.id.r11);
        textField[0][2] = findViewById(R.id.r12);
        textField[0][3] = findViewById(R.id.r13);
        textField[0][4] = findViewById(R.id.r14);
        //second row
        textField[1][0] = findViewById(R.id.r2);
        textField[1][1] = findViewById(R.id.r21);
        textField[1][2] = findViewById(R.id.r22);
        textField[1][3] = findViewById(R.id.r23);
        textField[1][4] = findViewById(R.id.r24);
        //third row
        textField[2][0] = findViewById(R.id.r3);
        textField[2][1] = findViewById(R.id.r31);
        textField[2][2] = findViewById(R.id.r32);
        textField[2][3] = findViewById(R.id.r33);
        textField[2][4] = findViewById(R.id.r34);
        //fourth row
        textField[3][0] = findViewById(R.id.r4);
        textField[3][1] = findViewById(R.id.r41);
        textField[3][2] = findViewById(R.id.r42);
        textField[3][3] = findViewById(R.id.r43);
        textField[3][4] = findViewById(R.id.r44);
        //fifth row
        textField[4][0] = findViewById(R.id.r5);
        textField[4][1] = findViewById(R.id.r51);
        textField[4][2] = findViewById(R.id.r52);
        textField[4][3] = findViewById(R.id.r53);
        textField[4][4] = findViewById(R.id.r54);
        //sixth row
        textField[5][0] = findViewById(R.id.r6);
        textField[5][1] = findViewById(R.id.r61);
        textField[5][2] = findViewById(R.id.r62);
        textField[5][3] = findViewById(R.id.r63);
        textField[5][4] = findViewById(R.id.r64);
        //seventh row
        textField[6][0] = findViewById(R.id.r7);
        textField[6][1] = findViewById(R.id.r71);
        textField[6][2] = findViewById(R.id.r72);
        textField[6][3] = findViewById(R.id.r73);
        textField[6][4] = findViewById(R.id.r74);
        //eight row
        textField[7][0] = findViewById(R.id.r8);
        textField[7][1] = findViewById(R.id.r81);
        textField[7][2] = findViewById(R.id.r82);
        textField[7][3] = findViewById(R.id.r83);
        textField[7][4] = findViewById(R.id.r84);
        //nine row
        textField[8][0] = findViewById(R.id.r9);
        textField[8][1] = findViewById(R.id.r91);
        textField[8][2] = findViewById(R.id.r92);
        textField[8][3] = findViewById(R.id.r93);
        textField[8][4] = findViewById(R.id.r94);
        //tenth row
        textField[9][0] = findViewById(R.id.r10);
        textField[9][1] = findViewById(R.id.r101);
        textField[9][2] = findViewById(R.id.r102);
        textField[9][3] = findViewById(R.id.r103);
        textField[9][4] = findViewById(R.id.r104);

        setTextField();
    }
}
