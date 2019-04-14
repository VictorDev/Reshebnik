package com.example.reshebnik;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    boolean isFirstNumber = true;
    int lastIndexNumber;
    LinearLayout linearLayout, linearLayout1;
    LayoutInflater layoutInflater;
    final int ID_REMOVE_BUTTON = 412351652;
    int[][]operands = null;
    float[][] operandsValue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        linearLayout =findViewById(R.id.linearlayout);
        linearLayout1 = findViewById(R.id.equationLayout);
        layoutInflater = getLayoutInflater();
    }

    public void addOperand(View view){

        View item = layoutInflater.inflate(R.layout.operand,linearLayout,false);
        item.getLayoutParams().width = LayoutParams.WRAP_CONTENT;
        linearLayout.addView(item);
        if(isFirstNumber){
            final Button removeButton = new Button(this);
            removeButton.setId(ID_REMOVE_BUTTON);
            removeButton.setText("remove");
            removeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeOperand(v);
                }
            });
            removeButton.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
            linearLayout1.addView(removeButton,1);
            isFirstNumber = false;
            lastIndexNumber = 0;
        }else{
            lastIndexNumber++;
        }
    }

    public void removeOperand(View view){
        linearLayout.removeView(linearLayout.getChildAt(lastIndexNumber));
        lastIndexNumber--;
        if(lastIndexNumber<0){
            Button deleteButton = findViewById(view.getId());
            linearLayout1.removeView(deleteButton);
            isFirstNumber = true;
        }
    }

    public void isReady(View view){
        int[][] mymass = new int[lastIndexNumber][3];
        float[][] mymassValues = new float[lastIndexNumber][3];// костыль из за  инициализации массивов
        for(int i = 0; i<= lastIndexNumber; i++){
            View myView = linearLayout.getChildAt(i);
            EditText sign = myView.findViewById(R.id.sign);
            if(sign.getText().toString().trim().length()>0){
                if(sign.getText().toString().equals("+")){
                    mymass[i][0] = 0;

                } else{
                    mymass[i][0] = 1;
                }
            } else{
                //оп
                sign.setBackgroundColor(Color.RED);
                return;
            }

            EditText number =  myView.findViewById(R.id.number);
            if(number.getText().toString().trim().length()>0){
                if(number.getText().toString().toUpperCase().equals("X")){
                    mymass[i][1] = 0;
                } else{
                    mymass[i][1] = 1;
                    Log.i("MyTag","number is const");
                    float value = Integer.parseInt(number.getText().toString());
                    mymassValues[i][1] = value;
                    Log.i("MyTag", String.valueOf(value));
                }
            } else{
                number.setBackgroundColor(Color.RED);
                return;
            }

            EditText exponent = myView.findViewById(R.id.exponent);
            if(exponent.getText().toString().trim().length()>0){
                if(exponent.getText().toString().toUpperCase().equals("X")){
                    mymass[i][2] = 0;
                    Log.i("MyTag","exponent is X");
                } else{
                    mymass[i][2] = 1;
                    Log.i("MyTag","exponent is const");
                    float value = Integer.parseInt(exponent.getText().toString());
                    mymassValues[i][2] = value;
                    Log.i("MyTag",String.valueOf(value));
                }
            }else{
                exponent.setBackgroundColor(Color.RED);
                return;
            }
        }
    }
}
