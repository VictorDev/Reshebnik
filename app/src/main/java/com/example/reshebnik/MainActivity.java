package com.example.reshebnik;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
    LinearLayout linearLayout, linearLayout1, equationTvLayout, mainLayout;
    LayoutInflater layoutInflater;
    float fault;
    int limit1, limit2;
    final int ID_REMOVE_BUTTON = 412351652;
    int[][]operands;
    float[][] operandsValue;
    LayoutParams layoutParams;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        linearLayout = findViewById(R.id.linearlayout);
        linearLayout1 = findViewById(R.id.equationLayout);
        equationTvLayout = findViewById(R.id.equationTvLayout);
        mainLayout = findViewById(R.id.mainLayout);
        layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        layoutInflater = getLayoutInflater();
    }

    public void addOperand(View view){

        View item = layoutInflater.inflate(R.layout.operand,linearLayout,false);
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
        operands = new int[lastIndexNumber+1][4];
        operandsValue = new float[lastIndexNumber+1][4];
        for(int i = 0; i<= lastIndexNumber; i++){
            View myView = linearLayout.getChildAt(i);
            EditText sign = myView.findViewById(R.id.sign);
            if(sign.getText().toString().trim().length()>0){
                if(sign.getText().toString().equals("+")){
                    operands[i][0] = 1;
                Log.i("MyTag","sign is +");

                } else{
                    operands[i][0] = -1;
                    Log.i("MyTag","sign is -");
                }
            } else{
                sign.setBackgroundColor(Color.RED);
                return;
            }

            EditText number =  myView.findViewById(R.id.number);
            if(number.getText().toString().trim().length()>0){
                if(number.getText().toString().toUpperCase().equals("Х")||number.getText().toString().toUpperCase().equals("X")){
                    operands[i][1] = 0;
                    Log.i("MyTag","number is X");
                } else{
                    operands[i][1] = 1;
                    Log.i("MyTag","number is const");
                    float value = Integer.parseInt(number.getText().toString());
                    operandsValue[i][1] = value;
                    Log.i("MyTag", String.valueOf(value));
                }
            } else{
                number.setBackgroundColor(Color.RED);
                return;
            }

            EditText exponent = myView.findViewById(R.id.exponent);
            if(exponent.getText().toString().trim().length()>0){
                if(exponent.getText().toString().toUpperCase().equals("Х")||exponent.getText().toString().toUpperCase().equals("X")){
                    operands[i][2] = 0;
                    Log.i("MyTag","exponent is X");
                } else{
                    operands[i][2] = 1;
                    Log.i("MyTag","exponent is const");
                    float value = Integer.parseInt(exponent.getText().toString());
                    operandsValue[i][2] = value;
                    Log.i("MyTag",String.valueOf(value));
                }
            }else{
                exponent.setBackgroundColor(Color.RED);
                return;
            }

            EditText factor = myView.findViewById(R.id.factor);
            if(factor.getText().toString().trim().length()>0){
                float f = Integer.valueOf(factor.getText().toString());
                operands[i][3] = 1;
                operandsValue[i][3] = f;
                Log.i("MyTag", "factor x = "+ String.valueOf(f));
            }else {
                operands[i][3] = 0;
                Log.i("MyTag", "not factor");
            }

            EditText faultET = findViewById(R.id.fault);
            if(faultET.getText().toString().trim().length()>0){
                String e = faultET.getText().toString();
                fault = Float.parseFloat(e);
            }else{
                faultET.setBackgroundColor(Color.RED);
                return;
            }

            EditText limit1ET = findViewById(R.id.etX1);
            if(limit1ET.getText().toString().trim().length()>0){
                String e = limit1ET.getText().toString();
                limit1 = Integer.parseInt(e);
            }else{
                limit1ET.setBackgroundColor(Color.RED);
                return;
            }

            EditText limit2ET = findViewById(R.id.etX2);
            if(limit2ET.getText().toString().trim().length()>0){
                String e = limit2ET.getText().toString();
                limit2 = Integer.parseInt(e);
            }else{
                limit2ET.setBackgroundColor(Color.RED);
                return;
            }



            }
        createTextViewEquation();
    }

    private void createTextViewEquation(){
        for(int i =0; i<=lastIndexNumber; i++) {
            View item = layoutInflater.inflate(R.layout.operandtv, equationTvLayout, false);
            TextView sign = item.findViewById(R.id.signtv);
            if (operands[i][0] == 1 && i == 0) {
                sign.setText("");
            } else if (operands[i][0] == 1) {
                sign.setText("+");
            } else {
                sign.setText("-");
            }

            TextView number = item.findViewById(R.id.numbertv);
            if (operands[i][1] == 0) {
                number.setText("X");
            } else {
                String n = String.valueOf(operandsValue[i][1]);
                String[] splitter = n.split("\\.");
                int c = splitter[1].length();
                if (c > 1) {
                    number.setText(n);
                } else {
                    String n1 = String.valueOf((int) operandsValue[i][1]);
                    number.setText(n1);
                }

            }

            TextView exponent = item.findViewById(R.id.exponenttv);
            if (operands[i][2] == 0) {
                exponent.setText("x");
            } else if (operandsValue[i][2] == 1.0) {
                exponent.setText("");
            } else {
                String n = String.valueOf((int) operandsValue[i][2]);
                exponent.setText(n);
            }


            TextView factor = item.findViewById(R.id.factortv);
            if(operands[i][3]==1){
                String n = String.valueOf(operandsValue[i][3]);
                String[] splitter = n.split("\\.");
                int c = splitter[1].length();
                if(c>1){
                    factor.setText(n);
                }else{
                    String n1 = String.valueOf((int)operandsValue[i][3]);
                    factor.setText(n1);
                }
            } else{
                factor.setText(" ");
            }

            equationTvLayout.addView(item);

        }

        View zero = layoutInflater.inflate(R.layout.zero, equationTvLayout, false);
        equationTvLayout.addView(zero);

        foundRoot();
    }

    void foundRoot(){
        float f1,f2,f3;
        float limit3;
        f1 = 0;
        f2 = 0;
        //while(){
            createTextLimit();
            int[] limits = {limit1,limit2};
            for(int j = 0; j<2; j++) {
                float equationResult = 0;
                for (int i = 0; i <= lastIndexNumber; i++) {
                    int sign = operands[i][0];
                    float factor;
                    if (operands[i][3] == 1) {
                        factor = operandsValue[i][3];
                    } else {
                        factor = 1;
                    }
                    float number;
                    if (operands[i][1] == 0) {
                        number = limits[j];
                    } else {
                        number = operandsValue[i][1];
                    }
                    float exponent;
                    if (operands[i][2]==0){
                        exponent = limits[j];
                    }else{
                        exponent = operandsValue[i][2];
                    }
                    double valueOperand = sign*factor*Math.pow(number,exponent);
                    float spareValue = (float) valueOperand;
                    equationResult+=spareValue;
                }
                if(j==0){
                    f1 = equationResult;
                } else{
                    f2 = equationResult;
                }

               /* limit3 = limit1 - (f1*(limit2 - limit1)/(f2-f1));
                Log.i("MyTag","limit3 is "+limit3); */
            }
        limit3 = limit1 - (f1*(limit2 - limit1)/(f2-f1));
        Log.i("MyTag","limit3 is "+limit3);
        createFValue(f1,f2);
        createChord(f1,f2,limit3);
        createNewInterval(limit3);


        //}
        Log.i("MyTag","f1 is " + f1 + " f2 is " + f2);
    }

    void calculateEquation(float limit){
        
    }

   /* boolean checkInterval(float a, float b, float c){

    } */

   void createTextLimit(){
       TextView tv = new TextView(this);
       tv.setLayoutParams(layoutParams);
       tv.setText("E = ["+limit1+";"+limit2+"]");
       mainLayout.addView(tv);
   }

   void createChord(float f1, float f2, float limit3){

       //отступить 4dp сверху для х= а и для = limit3
       //добавить знак умножения для (в-а)
       View chord = layoutInflater.inflate(R.layout.chord, mainLayout, false);

       TextView tvA = chord.findViewById(R.id.a);
       tvA.setText("x = "+ limit1 + " -   ");

       TextView tvFa = chord.findViewById(R.id.fa);
       tvFa.setText(String.valueOf(f1));

       TextView tvBa = chord.findViewById(R.id.ba);
       String n = String.valueOf(limit2-limit1);
       tvBa.setText(" * "+n);

       TextView tvFb = chord.findViewById(R.id.fb);
       tvFb.setText(String.valueOf(f2));

       TextView tvFaB = chord.findViewById(R.id.faB);
       tvFaB.setText(" - "+String.valueOf(f1));

       TextView tvLimit3 = chord.findViewById(R.id.limit3tv);
       tvLimit3.setText("   = " + String.valueOf(limit3));

       mainLayout.addView(chord);
   }

   void createFValue(float f1, float f2){
       TextView tvValue = new TextView(this);
       tvValue.setLayoutParams(layoutParams);
       tvValue.setText("f("+limit1+") = "+f1 +"\n f("+limit2+") = "+f2);
       mainLayout.addView(tvValue);
   }

   void createValue(float f3, float limit3){
       TextView tvValue = new TextView(this);
       tvValue.setLayoutParams(layoutParams);
       tvValue.setText("f("+limit3+") = "+f3);
       mainLayout.addView(tvValue);
   }

   void createNewInterval(float limit3){
       TextView tvValue = new TextView(this);
       tvValue.setLayoutParams(layoutParams);
       tvValue.setText("x е ["+limit1+";"+limit3 + "] и ["+limit3+";"+limit2+"]");
       mainLayout.addView(tvValue);
   }
}
