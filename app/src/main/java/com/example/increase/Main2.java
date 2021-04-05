package com.example.increase;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.StringTokenizer;

public class Main2 extends AppCompatActivity {

    TextView ldate;
    Switch sw;
    int mYear,mMonth,mDay,mnow,mtotal,mgl,not1;
    EditText ta,pa,gl;
    public void ldat(View view)
    {

        String cdate= (String) ldate.getText();
        StringTokenizer cd=new StringTokenizer(cdate,"-");
        mDay=Integer.parseInt(cd.nextToken());
        mMonth=Integer.parseInt(cd.nextToken())-1;
        mYear=Integer.parseInt(cd.nextToken());


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            ldate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            MainActivity.sdate=dayOfMonth; MainActivity.smonth=monthOfYear; MainActivity.syear=year;


                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();




    }

    @Override
    public void onBackPressed() {
        Intent cc=new Intent();
        setResult(RESULT_CANCELED,cc);
        finish();
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        pa=(EditText)findViewById(R.id.editText1);
        ta=(EditText)findViewById(R.id.editText2);
        gl=(EditText)findViewById(R.id.editText3);
        ldate=(TextView) findViewById(R.id.ldate);
        sw=(Switch)findViewById(R.id.switch1);
        Intent intent=getIntent();
        mDay= intent.getIntExtra("sd",0);
        mMonth= intent.getIntExtra("sm",0);
        mYear= intent.getIntExtra("sy",0);
        mnow=intent.getIntExtra("pa",0);
        mtotal=intent.getIntExtra("ta",0);
        mgl=intent.getIntExtra("gl",0);
        not1=intent.getIntExtra("not",0);

        if(not1==1)
        {
            sw.setChecked(true);
        }
        else sw.setChecked(false);

        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) MainActivity.not=1;
                else MainActivity.not=0;
            }
        });



        if(mnow==0) pa.setText("");
        else
        pa.setText(String.valueOf(mnow));
        if(mtotal==0) ta.setText("");
        else
        ta.setText(String.valueOf(mtotal));
        ldate.setText(mDay + "-" + (mMonth + 1) + "-" + mYear);
        pa.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
              try {
                  if (s.length() != 0)
                      MainActivity.now = Integer.parseInt(String.valueOf(s));
                  else MainActivity.now = 0;
              }catch (Exception e)
              {e.printStackTrace();  }

            }
        });
        ta.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                try {


                    if (s.length() != 0)
                        MainActivity.total = Integer.parseInt(String.valueOf(s));
                    else MainActivity.total = 0;
                }catch (Exception e)
                {e.printStackTrace();}
            }
        });

        gl.setText(String.valueOf(mgl));
        gl.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    if (s.length() != 0) {
                        int yy = Integer.parseInt(String.valueOf(s));
                        if (yy > 100 || yy == 0) yy = 75;
                        MainActivity.goal = yy;

                    } else MainActivity.goal = 75;
                }catch (Exception e)
                {e.printStackTrace();}
            }
        });

    }


}
