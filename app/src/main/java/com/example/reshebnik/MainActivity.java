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
import android.widget.Toast;

import java.math.BigDecimal;
import java.math.RoundingMode;


public class MainActivity extends AppCompatActivity {
    boolean isFirstNumber = true;
    int lastIndexNumber;
    LinearLayout linearLayout, linearLayout1, equationTvLayout, mainLayout;
    LayoutInflater layoutInflater;
    float fault;
    float limit1, limit2;
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
                limit1 = Float.parseFloat(e);
            }else{
                limit1ET.setBackgroundColor(Color.RED);
                return;
            }

            EditText limit2ET = findViewById(R.id.etX2);
            if(limit2ET.getText().toString().trim().length()>0){
                String e = limit2ET.getText().toString();
                limit2 = Float.parseFloat(e);
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
        float limit3 = limit1 + 100;
        f1 = 0;
        f2 = 0;
        f3 = 0;
        int p = 0;
        boolean isNoFirst = false;
        while(checkInterval(limit1,limit3,limit2)){
            if(isNoFirst) {
                selectInterval(f1,f3,limit3);
            }
            if(p>15){
                Toast.makeText(this,"Ошибка, нет корня", Toast.LENGTH_LONG).show();
                return;
            }
            isNoFirst = true;
            createTextLimit();
            f1 = calculateEquation(limit1);
            f2 = calculateEquation(limit2);
            limit3 = limit1 - (f1*(limit2 - limit1)/(f2-f1));

            BigDecimal bd = new BigDecimal(limit3).setScale(3, RoundingMode.HALF_EVEN);
            limit3 = bd.floatValue();

            f3 = calculateEquation(limit3);

            BigDecimal bd1 = new BigDecimal(f3).setScale(3, RoundingMode.HALF_EVEN);
            f3 = bd1.floatValue();

            Log.i("MyTag","limit3 is "+limit3);
            createFValue(f1,f2);
            createChord(f1,f2,limit3);
            createNewInterval(limit3);
            createValue(f3, limit3);
            p++;
        }
        TextView tv = new TextView(this);
        tv.setLayoutParams(layoutParams);
        tv.setText("ОТВЕТ "+limit3);
        mainLayout.addView(tv);

        Log.i("MyTag","ОТВЕТ ЭТО " + limit3);
    }

    void selectInterval(float lim1, float lim3, float lim){
        if((lim1>0&&lim3<0)||(lim1<0&&lim3>0)){
            limit2 = lim;
        }else{
            limit1 = lim;
        }
    }

    float calculateEquation(float limit){
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
                number = limit;
            } else {
                number = operandsValue[i][1];
            }
            float exponent;
            if (operands[i][2]==0){
                exponent = limit;
            }else{
                exponent = operandsValue[i][2];
            }
            double valueOperand = sign*factor*Math.pow(number,exponent);
            float spareValue = (float) valueOperand;
            equationResult+=spareValue;
        }
        return equationResult;

    }

    boolean checkInterval(float a, float b, float c){
        float v12 = Math.abs(Math.abs(a)-Math.abs(b));
        Log.i("MyTag","v12 is "+v12);
        float bb = Math.abs(b);
        float cc = Math.abs(c);
        Log.i("MyTag","bb is "+String.valueOf(bb)+" cc is "+String.valueOf(cc));
        float v23 = Math.abs(Math.abs(b)-Math.abs(c));
        Log.i("MyTag","v23 is "+v23);
        boolean cheking = v12<=fault||v23<=fault;
        Log.i("MyTag", "cheking is "+cheking);
        return !cheking;

    }

   void createTextLimit(){
       TextView tv = new TextView(this);
       tv.setLayoutParams(layoutParams);
       tv.setText("E = ["+limit1+";"+limit2+"]");
       mainLayout.addView(tv);
   }

   void createChord(float f1, float f2, float limit3){

       LinearLayout ln = new LinearLayout(this); //костыль
       ln.setLayoutParams(layoutParams);

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

       ln.addView(chord);

       mainLayout.addView(ln);
   }

   void createFValue(float f1, float f2){
       TextView tvValue = new TextView(this);
       tvValue.setLayoutParams(layoutParams);
       tvValue.setText("f("+limit1+") = "+f1 +"\n f("+limit2+") = "+f2);
       mainLayout.addView(tvValue);
   }

   void createValue(float f3, float limit3){
       TextView tvValue1 = new TextView(this);
       tvValue1.setLayoutParams(layoutParams);
       tvValue1.setText("f("+limit3+") = "+f3);
       mainLayout.addView(tvValue1);
       Log.i("MyTag","Вьюшка создалась");
   }

   void createNewInterval(float limit3){
       TextView tvValue2 = new TextView(this);
       tvValue2.setLayoutParams(layoutParams);
       tvValue2.setText("x е ["+limit1+";"+limit3 + "] и ["+limit3+";"+limit2+"]");
       mainLayout.addView(tvValue2);
   }

}
