package com.example.learning.basicmaths;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private Operations op;
    private int number,opM,numberUser,amountD,amountN, opDone;
    private TextView numberOp, numberIn, numberAD, numberAN;

    private String setNameExercise(int o){
        String nameBasicOperation = "";
        switch(o){
            case 1:
                nameBasicOperation = getResources().getString(R.string.button_Add); break;
            case 2:
                nameBasicOperation = getResources().getString(R.string.button_Subtract); break;
            case 3:
                nameBasicOperation = getResources().getString(R.string.button_Multiply); break;
            case 4:
                nameBasicOperation = getResources().getString(R.string.button_Division); break;
            default:
                Toast.makeText(this, "Error getting menu option", Toast.LENGTH_SHORT).show();
                finishActivity();
        }
        return nameBasicOperation;
    }

    private String operationToString(){
        String s = "";
        int [] numbers = op.get_numbers();
        s = s.concat(Integer.toString(numbers[0]));
        for(int i = 1; i < numbers.length; i++){
            switch (opM){
                case 1:
                    s = s.concat(" + "); break;
                case 2:
                    s = s.concat(" - "); break;
                case 3:
                    s = s.concat(" * "); break;
                case 4:
                    s = s.concat(" / "); break;
                default:
                    Toast.makeText(this, "Error setting number", Toast.LENGTH_SHORT).show();
                    finishActivity();
            }
            s = s.concat(Integer.toString(numbers[i]));
        }
        return s;
    }

    private void buttonChoose(Button [] b, int p){
        final int pp = p;
        b[p].setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                numberUser *= 10;
                numberUser += pp;
                numberIn.setText(Integer.toString(numberUser));
            }
        });
    }

    private boolean checkAnswer(){
        if(numberUser == number) return true;
        return false;
    }

    private int getOption(){
        Intent i = getIntent();
        int o = i.getIntExtra("intOption", 0);
        return o;
    }

    private void initiateValues(){
        number = op.set_number(amountN,amountD,opM); //number to get == answer
        this.numberOp.setText(operationToString());
        numberUser = 0; //initialization of number that introduces the user
    }

    //method to increase or reduce the amount of digits or numbers
    private int changeND(int n, boolean o, boolean type, TextView t){
        int a;
        if(type) a = 1; //type == true => digits
        else a = 2; //type == false => numbers
        if(o) n++;
        else{
            if(n > a) n--;
            else Toast.makeText(this, "It cannot be reduce more", Toast.LENGTH_SHORT).show();
        }
        initiateValues(); //change operation with the new values
        t.setText(Integer.toString(n)); //set new value on screen
        return n;
    }

    //today date?
    private String todayDate(){
        //LocalDate localDate = LocalDate.now();
        //Date date;
        Calendar c = Calendar.getInstance();
        String d = "";
        d = d.concat(Integer.toString(c.get(Calendar.DAY_OF_MONTH)));
        d = d.concat("/" + Integer.toString(c.get(Calendar.MONTH)+1));
        d = d.concat("/" + Integer.toString(c.get(Calendar.YEAR)));
        return d;
    }

    /**
     * Method which would set the variables to record the activity done, the next
     * variables are the data to save:
     * 1.Activity (Operation) 2. Time (Date/Month/Year) 3. Number of operations
     * 4. Number of Digits 5. Number of Numbers
     */
    private String [] setDataToSave(){
        String [] data = new String[5];
        data[0] = setNameExercise(opM);
        data[1] = todayDate();
        data[2] = Integer.toString(opDone);
        data[3] = Integer.toString(amountD);
        data[4] = Integer.toString(amountN);
        return data;
    }

    private void finishActivity(){
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //First data to be set == successful communication?
        opM = getOption();
        op = new Operations();
        opDone = 0;

        TextView typeOperation;
        final TextView numberOperations;
        final Button buttonN[] = new Button [10];
        //set links to buttons
        final Button buttonB = findViewById(R.id.buttonStop);
        final Button buttonD = findViewById(R.id.button_Delete);
        final Button buttonS = findViewById(R.id.button_Send);
        final Button buttonNe = findViewById(R.id.button_Neg);
        final Button buttonZe = findViewById(R.id.button_Zero);
        final ImageButton buttonUpD = findViewById(R.id.buttonUpD);
        final ImageButton buttonDownD = findViewById(R.id.buttonDownD);
        final ImageButton buttonUpN = findViewById(R.id.buttonUpN);
        final ImageButton buttonDownN = findViewById(R.id.buttonDownN);
        buttonN[0] = findViewById(R.id.button_n0);
        buttonN[1] = findViewById(R.id.button_n1);
        buttonN[2] = findViewById(R.id.button_n2);
        buttonN[3] = findViewById(R.id.button_n3);
        buttonN[4] = findViewById(R.id.button_n4);
        buttonN[5] = findViewById(R.id.button_n5);
        buttonN[6] = findViewById(R.id.button_n6);
        buttonN[7] = findViewById(R.id.button_n7);
        buttonN[8] = findViewById(R.id.button_n8);
        buttonN[9] = findViewById(R.id.button_n9);
        //set links to TextView
        numberOp = findViewById(R.id.textViewOperationToDo);
        numberIn = findViewById(R.id.textViewNumber);
        numberAD = findViewById(R.id.textDigits);
        numberAN = findViewById(R.id.textNumbers);
        typeOperation = findViewById(R.id.textViewOperation);
        numberOperations = findViewById(R.id.operationsDone);
        //set operation name on TextView
        typeOperation.setText(setNameExercise(opM));

        //Initial values to set
        setNameExercise(opM);
        amountD = 1; amountN = 2; // numbers by default
        numberAD.setText(Integer.toString(amountD)); //text amount of digits
        numberAN.setText(Integer.toString(amountN)); // text amount of numbers
        initiateValues();

        buttonChoose(buttonN, 0);
        buttonChoose(buttonN, 1);
        buttonChoose(buttonN, 2);
        buttonChoose(buttonN, 3);
        buttonChoose(buttonN, 4);
        buttonChoose(buttonN, 5);
        buttonChoose(buttonN, 6);
        buttonChoose(buttonN, 7);
        buttonChoose(buttonN, 8);
        buttonChoose(buttonN, 9);

        //Buttons to increase or reduce the amount of digits and numbers
        buttonUpD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amountD = changeND(amountD,true, true, numberAD);
            }
        });

        buttonDownD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amountD = changeND(amountD, false, true, numberAD);
            }
        });

        buttonUpN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amountN = changeND(amountN, true, false, numberAN);
            }
        });

        buttonDownN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amountN = changeND(amountN, false, false, numberAN);
            }
        });

        //button to save data and close the activity
        buttonB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileManage f = new FileManage();
                Context context;
                context = getApplicationContext();
                f.writeF(context, setDataToSave());
                finishActivity();
            }
        });

        //Button to set the number of the user as a negative one
        buttonNe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberUser *= (-1);
                numberIn.setText(Integer.toString(numberUser));
            }
        });

        //Button to set the number of the user to zero
        buttonZe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberUser = 0;
                numberIn.setText(Integer.toString(numberUser));
            }
        });

        //Button to delete the last digit
        buttonD.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                numberUser /= 10;
                numberIn.setText(Integer.toString(numberUser));
            }
        });

        //Button to review if answer is right and start a new operation
        buttonS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Toast toast;
                if(checkAnswer()){
                    toast = Toast.makeText(MainActivity.this, "Right Answer!!, Try another one!!", Toast.LENGTH_SHORT);
                    initiateValues();
                    numberIn.setText(getResources().getString(R.string.userNumber));
                    opDone++;
                    numberOperations.setText(Integer.toString(opDone));
                }
                else {
                    toast = Toast.makeText(MainActivity.this, "There is an error on your answer.", Toast.LENGTH_SHORT);
                }
                toast.show();
                //the Handler h would reduce the amount of time that Toast is showed
                Handler h = new Handler();
                h.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        toast.cancel();
                    }
                }, 1000);
            }
        });

    }
}
