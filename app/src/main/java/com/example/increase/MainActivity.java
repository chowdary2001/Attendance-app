package com.example.increase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    ScrollView sc;
    DatabaseHandler db;
   static int sdate=10,smonth=4,syear=2020;
    static int now=0;
    static int total=0;
    static int goal=75;
    int dark=1;
    LinearLayout layout1;
    ProgressBar ppl;
    TextView ttl,h1,h2;
    Calendar c = Calendar.getInstance();
    int date = c.get(Calendar.DATE);
    int month= c.get(Calendar.MONTH);
    int day  =c.get(Calendar.DAY_OF_WEEK);
    int yer=c.get(Calendar.YEAR);
    private FirebaseAuth mAuth;
    int present=0,maintotal=0;
    int error1=0,error2=0;
   static int not=1;  int dnot=1;
    SeekBar sb;
    String ms[]={"Jan","Feb","Mar", "Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
    String ds[] = {"Sun.","Mon.","Tue.","Wed.","Thu.","Fri.","Sat."};
    int dm[]={31,28,31,30,31,30,31,31,30,31,30,31};
    List<String> cheq;
    List<String> atd;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db=new DatabaseHandler(this);

        mAuth = FirebaseAuth.getInstance();
        sc = (ScrollView) findViewById(R.id.scr);
        layout1 = (LinearLayout) findViewById(R.id.lay);
        ppl=(ProgressBar)findViewById(R.id.progressBar) ;
        sb=(SeekBar)findViewById(R.id.seekBar);
        ttl=(TextView)findViewById(R.id.textView4);
        h1=(TextView)findViewById(R.id.textView5);
        h2=(TextView)findViewById(R.id.textView6);










        ttl.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(getApplicationContext(),String.valueOf(error1)+" / "+String.valueOf(error2)+"/   re : "+String.valueOf(pred()),Toast.LENGTH_LONG).show();
                return true;
            }
        });
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                ttl.setText(String.valueOf(progress/10)+"%");
                pred();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        cheq=db.getAllContacts();


        if(cheq.contains("not")) {
            Contact cv=  db.getContact("not");
            String ff=cv.getAttend();
            not = Integer.parseInt(ff);




        }
        else
        {
            db.addContact(new Contact("not", "1"));
            Intent alarmIntent = new Intent(getApplicationContext(), Notification_re.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, alarmIntent, 0);

            AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
           // Toast.makeText(getApplicationContext(),"started",Toast.LENGTH_LONG).show();
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
           calendar.set(Calendar.HOUR_OF_DAY,19);
            calendar.set(Calendar.MINUTE,00);
            calendar.set(Calendar.SECOND,0);

            manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY , pendingIntent);

        }






        if(cheq.contains("123456789")) {
          Contact cv=  db.getContact("123456789");
          String ff=cv.getAttend();
          StringTokenizer gf=new StringTokenizer(ff,"/");
         sdate = Integer.parseInt(gf.nextToken());
         smonth= Integer.parseInt(gf.nextToken());
         syear= Integer.parseInt(gf.nextToken());


        }
        else
        {
            db.addContact(new Contact("123456789", "10/4/2020"));

        }

        if(cheq.contains("tillnow")) {
            Contact cv=  db.getContact("tillnow");
            String ff=cv.getAttend();
            StringTokenizer gf=new StringTokenizer(ff,"/");
            now = Integer.parseInt(gf.nextToken());
            total= Integer.parseInt(gf.nextToken());
            goal=Integer.parseInt(gf.nextToken());



        }
        else
        {
            db.addContact(new Contact("tillnow", "0/0/75"));

        }


        if(cheq.contains("errors")) {
            Contact cv=  db.getContact("errors");
            String ff=cv.getAttend();
            StringTokenizer gf=new StringTokenizer(ff,"/");
            error1= Integer.parseInt(gf.nextToken());
            error2= Integer.parseInt(gf.nextToken());

        }
        else
        {
            db.addContact(new Contact("errors", "0/0"));

        }

        int temp=start();
        psum(1);
        if(temp==1)
            restart();


    }



    public void psum(int w)
    {
       present=0; maintotal=0;
        int date1 = c.get(Calendar.DATE);
        int month1= c.get(Calendar.MONTH);
        int day1  =c.get(Calendar.DAY_OF_WEEK);
        int yer1=c.get(Calendar.YEAR);
        cheq.clear();
        cheq=db.getAllContacts();
        while(true)
        {
            if(yer1>syear || (yer1==syear && month1>smonth)  || (yer1==syear && month1==smonth && date1>=sdate))
            {
                if(day1!=1)
                {
                    String sd=Integer.toString(date1)+"00"+Integer.toString(month1)+"00"+Integer.toString(yer1);
                    if( cheq.contains(sd))
                    {

                        Contact cc=db.getContact(sd);
                       if(!cc.getAttend().equals("N/A"))
                       {
                           present=present+Integer.parseInt(cc.getAttend());
                          if(day1==7) maintotal=maintotal+3;
                          else maintotal=maintotal+6;
                       }
                    }



                }
                if(month1==0 && date1==1) {month1=12; yer1--;}
                if(date1==1) {    month1--;   date1=dm[month1];  }
                else   date1--;
                if(day1==1)  day1=7;
                else  day1--;

            }
            else break;


        }
        int vv,vv2;
        if((maintotal+error2)!=0) {
            vv = (present + error1) * 100 / (maintotal + error2);
            vv2 = (present + error1) * 1000 / (maintotal + error2);
        }
        else{ vv=0;
                vv2=0;}
        if(w==1) {
            ObjectAnimator ani = ObjectAnimator.ofInt(ppl, "progress", 0, vv);
            ani.setDuration(900);
            ObjectAnimator ani2 = ObjectAnimator.ofInt(sb, "progress", 0, vv2);
            ani2.setDuration(900);
            ani.start();
            ani2.start();

        }
             else  { ppl.setProgress(vv);
                    sb.setProgress(vv2);}

    }

    public int pred() {

        int remain=0;
        int date1 = c.get(Calendar.DATE);
        int month1 = c.get(Calendar.MONTH);
        int day1 = c.get(Calendar.DAY_OF_WEEK);
        int yer1 = c.get(Calendar.YEAR);

        if (yer1 > syear || (yer1 == syear && month1 > smonth) || (yer1 == syear && month1 == smonth && date1 >= sdate)) {
            if (day1 != 1)
            {
                String sd=Integer.toString(date1)+"00"+Integer.toString(month1)+"00"+Integer.toString(yer1);
                Contact cc=db.getContact(sd);
                if(!cc.getAttend().equals("0"))
                {
                    date1++;
                    if (day1 == 7) day1 = 1;
                    else day1++;
                }

            }
            if(date1-1 ==dm[month1])
            {
                int vv=sb.getProgress()/10;
                h1.setText("Max month end Attendance  =  "+String.valueOf(vv)+"%");

                h2.setText("To maintain "+goal+"% on month end you can absent for ** classes");

            }
            if (!(date1 >dm[month1] ))
            while (true) {

                if (day1 != 1)
                {
                    if (day1 == 7) remain = remain + 3;
                    else remain = remain + 6;
                }
                if (day1 == 7) day1 = 1;
                else day1++;

                if (date1 >= dm[month1] )
                {
                    int per=((present+error1+remain)*100)/(maintotal+error2+remain);
                    int gg=((maintotal+error2+remain)*goal)/100;
                    int cg;
                    cg=present+remain-gg+error1;
                    if(cg<0) cg=0;
                    h1.setText("Max month end Attendance  =  "+String.valueOf(per)+"%");
                    h2.setText("To maintain "+goal+"% on month end you can absent for "+String.valueOf(cg)+" classes");


                    break;
                }
                else date1++;






            }

        }
        return remain;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mf=getMenuInflater();
        mf.inflate(R.menu.menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent a2=new Intent(getApplicationContext(),Main2.class);
        psum(0);
        dnot=not;
        now=present+error1;
        total=maintotal+error2;
        a2.putExtra("sd",sdate);
        a2.putExtra("sm",smonth);
        a2.putExtra("sy",syear);
        a2.putExtra("pa",now);
        a2.putExtra("ta",total);
        a2.putExtra("gl",goal);
        a2.putExtra("not",not);
        startActivityForResult(a2,1);
        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode==RESULT_CANCELED)
        {  layout1.removeAllViewsInLayout();
            date = c.get(Calendar.DATE);
             month= c.get(Calendar.MONTH);
             day  =c.get(Calendar.DAY_OF_WEEK);
            yer=c.get(Calendar.YEAR);

            if(not!=dnot)
            {
                if(not==1)
                {
                    Intent alarmIntent = new Intent(getApplicationContext(), Notification_re.class);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, alarmIntent, 0);

                    AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                   // Toast.makeText(getApplicationContext(),"started",Toast.LENGTH_LONG).show();
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(System.currentTimeMillis());
                   calendar.set(Calendar.HOUR_OF_DAY,19);
                    calendar.set(Calendar.MINUTE,00);
                    calendar.set(Calendar.SECOND,0);

                    manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                            AlarmManager.INTERVAL_DAY, pendingIntent);
                }
                else
                {
                    AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                    Intent alarmIntent = new Intent(getApplicationContext(), Notification_re.class);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, alarmIntent, 0);
                    manager.cancel(pendingIntent);
                   // Toast.makeText(getApplicationContext(),"canceled",Toast.LENGTH_LONG).show();

                }
                Contact cc5=db.getContact("not");
                cc5.setAttend(String.valueOf(not));
                db.updateContact(cc5);

            }




            String vv=String.valueOf(sdate)+"/"+String.valueOf(smonth)+"/"+String.valueOf(syear);
            Contact cc=db.getContact("123456789");
            cc.setAttend(vv);
            db.updateContact(cc);
            Contact cc2=db.getContact("tillnow");
            String vv2=String.valueOf(now)+"/"+String.valueOf(total)+"/"+String.valueOf(goal);
            error1=now-present;
            error2=total-maintotal;
            cc2.setAttend(vv2);
            db.updateContact(cc2);
            String vv3=String.valueOf(error1)+"/"+String.valueOf(error2);
            Contact cc3=db.getContact("errors");
            cc3.setAttend(vv3);
            db.updateContact(cc3);
            pred();
            cheq.clear();
            cheq=db.getAllContacts();
            psum(0);
            int temp=start();
            if(temp==1) restart();

        }

    }



    public void restart()
    {
        final TextView see = new TextView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(500,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(400, 0, 0, 0);
        see.setLayoutParams(params);
        see.setText("See more....");
        see.setTextColor(Color.parseColor("#0009ff"));
        see.setTextSize(17);
        layout1.addView(see);
        see.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout1.removeView(see);
                int temp=start();
                if(temp==1)
                layout1.addView(see);
            }
        });
    }


    @SuppressLint("ResourceAsColor")
    public int start()

    {
        for(int i=0;i<200;i++)
    {

        if(yer>syear || (yer==syear && month>smonth)  || (yer==syear && month==smonth && date>=sdate))
        {

        final   int max;
        if(day!=1) {
            LinearLayout tem = new LinearLayout(this);
            tem.setOrientation(LinearLayout.HORIZONTAL);



            TextView text = new TextView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(450,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(50, 0, 50, 0);
            text.setLayoutParams(params);
            String tod = date + "/" + ms[month] + "   " + ds[day - 1];
            text.setText(tod);
            text.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            text.setTextAppearance(R.style.TextAppearance_AppCompat_Body2);
            text.setTextSize(20);
            tem.addView(text);
            if(day==7)
                max=4;
            else
                max=7;


            final Button bbb = new Button(this);
            final String kk = "-";
            bbb.setText(kk);
            bbb.setTextSize(18);
            params = new LinearLayout.LayoutParams(115, 115);
            bbb.setLayoutParams(params);
            bbb.setBackgroundResource(R.drawable.round);

            tem.addView(bbb);


            final TextView text2 = new TextView(this);
            params = new LinearLayout.LayoutParams(190,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            text2.setLayoutParams(params);
            String te1 = Integer.toString(date)+"00"+Integer.toString(month)+"00"+Integer.toString(yer);
           if( cheq.contains(te1))
           {
               Contact con=db.getContact(te1);
               text2.setText(con.getAttend());
               text2.setTag(Integer.toString(date)+"00"+Integer.toString(month)+"00"+Integer.toString(yer));

           }
           else {
               db.addContact(new Contact(te1,"0"));
              text2.setText("0");
              text2.setTag(Integer.toString(date)+"00"+Integer.toString(month)+"00"+Integer.toString(yer));
           }

            text2.setTextAppearance(R.style.TextAppearance_AppCompat_Body2);
            text2.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            text2.setTextSize(25);
            tem.addView(text2);




            bbb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int x;
                    if(text2.getText().equals("N/A"))
                        x=-1;
                    else
                        x = Integer.parseInt((String) text2.getText());
                    x--;
                    if (x >= -1) {
                        if(x==-1)
                            text2.setText("N/A");
                        else
                            text2.setText(String.valueOf(x));
                        String te1 = (String) text2.getTag();
                        String te2= (String) text2.getText();
                        Contact con=db.getContact(te1);
                        con.setAttend(te2);
                        db.updateContact(con);
                        psum(0);

                    }

                }
            });


            bbb.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(final View v) {
                    final Timer timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            if (bbb.isPressed()) {
                                int x;
                                if(text2.getText().equals("N/A"))
                                    x=-1;
                                else
                                    x = Integer.parseInt((String) text2.getText());
                                x--;
                                if (x >= -1)
                                {  if(x==-1)
                                    text2.setText("N/A");
                                else
                                    text2.setText(String.valueOf(x));
                                    String te1 = (String) text2.getTag();
                                    String te2= (String) text2.getText();
                                    Contact con=db.getContact(te1);
                                    con.setAttend(te2);
                                    db.updateContact(con);
                                    psum(0);




                                }
                            } else
                            {
                                timer.cancel();}
                        }
                    }, 80, 90);
                    return true;
                }
            });


            final Button bbb2 = new Button(this);
            final String kk2 = "+";
            bbb2.setText(kk2);
            bbb2.setTextSize(18);
            params = new LinearLayout.LayoutParams(115, 115);
            bbb2.setLayoutParams(params);
            bbb2.setBackgroundResource(R.drawable.round);
            bbb2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int x;
                    if(text2.getText().equals("N/A"))
                        x=-1;
                    else
                        x = Integer.parseInt((String) text2.getText());
                    x++;
                    if (x < max)
                    { text2.setText(String.valueOf(x));
                        String te1 = (String) text2.getTag();
                        String te2= (String) text2.getText();
                        Contact con=db.getContact(te1);
                        con.setAttend(te2);
                        db.updateContact(con);
                        psum(0);

                    }
                }
            });
            bbb2.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(final View v) {
                    final Timer timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            if (bbb2.isPressed()) {
                                int x;
                                if(text2.getText().equals("N/A"))
                                    x=-1;
                                else
                                    x = Integer.parseInt((String) text2.getText());
                                x++;
                                if (x < max)
                                {
                                    text2.setText(String.valueOf(x));
                                    String te1 = (String) text2.getTag();
                                    String te2= (String) text2.getText();
                                    Contact con=db.getContact(te1);
                                    con.setAttend(te2);
                                    db.updateContact(con);
                                    psum(0);

                                }
                            } else
                                timer.cancel();
                        }
                    }, 80, 90);
                    return true;
                }
            });


            tem.addView(bbb2);
            LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    185);
            tem.setLayoutParams(params2);


            layout1.addView(tem);
        }
        if(month==0 && date==1) {month=12; yer--;}
        if(date==1) {    month--;   date=dm[month];  }
        else   date--;
        if(day==1)  day=7;
        else  day--;

    }
        else return 0;

    }
        if(yer>syear || (yer==syear && month>smonth)  || (yer==syear && month==smonth && date>=sdate))
            return 1;
        return 0;
    }
}
