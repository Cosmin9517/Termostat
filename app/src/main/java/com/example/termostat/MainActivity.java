package com.example.termostat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 1;
    //Trimitere mesaj
    String numar_tel = "0757472603";
    String mesaj;

    //baza de date
    private FirebaseDatabase programDB;
    private DatabaseReference programReference;
    private DatabaseReference sistemReference;
    private Program pc = new Program();
    private Sistem sc = new Sistem();
    private boolean active_temp=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        programDB = FirebaseDatabase.getInstance();
        programReference = programDB.getReference("program");
        sistemReference = programDB.getReference("sistem");
        final ImageView temp_view = findViewById(R.id.buton_temperatura);
        final ImageView umid_view = findViewById(R.id.buton_umiditate);
        temp_view.setColorFilter(ContextCompat.getColor(this, R.color.colorPrimary), PorterDuff.Mode.MULTIPLY);
        checkSMSMessage();


        temp_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                active_temp=true;
                temp_view.setColorFilter(ContextCompat.getColor(v.getContext(), R.color.colorPrimary), PorterDuff.Mode.MULTIPLY);
                umid_view.setColorFilter(null);
                set_up_bottom(pc);
                set_cur(sc);
            }
        });
        umid_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                active_temp=false;
                umid_view.setColorFilter(ContextCompat.getColor(v.getContext(), R.color.colorPrimary), PorterDuff.Mode.MULTIPLY);
                temp_view.setColorFilter(null);
                set_up_bottom(pc);
                set_cur(sc);
            }
        });


        programReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int sdow = get_the_day();

                for (DataSnapshot datasnapshotchild : dataSnapshot.getChildren()) {
                    Program p = datasnapshotchild.getValue(Program.class);
                    if (p.ziua == sdow) {
                        pc.set_program(p);
                        set_up_bottom(p);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        sistemReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

               for (DataSnapshot datasnapshotchild : dataSnapshot.getChildren()){
                    Sistem s = datasnapshotchild.getValue(Sistem.class);
                   set_cur(s);
                   sc.set_sistem(s);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void sendSMSMessage(){
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(numar_tel, null, mesaj, null, null);
            Toast.makeText(getApplicationContext(), "Mesaj trimis!",
                    Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),
                    "Mesajul nu s-a putut trimite!",
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

    }


    protected void checkSMSMessage() {

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.SEND_SMS)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        }
    }
/*
    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {try {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(numar_tel, null, mesaj, null, null);
                    Toast.makeText(getApplicationContext(), "Mesaj trimis!",
                            Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(),
                            "Mesajul nu s-a putut trimite!",
                            Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                        }
                }
                else {
                    Toast.makeText(getApplicationContext(),
                            "Nu s-a reusit trimiterea mesajului!", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }

    }
*/
    public void goto_settings(View view){
        Intent intent = new Intent(MainActivity.this, settings.class);
        startActivity(intent);
    }
    public int get_the_day(){
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        int sdow = 0;

        switch (day) {
            case Calendar.MONDAY:
                sdow = 1;
                break;
            case Calendar.TUESDAY:
                sdow = 2;
                break;
            case Calendar.WEDNESDAY:
                sdow = 3;
                break;
            case Calendar.THURSDAY:
                sdow = 4;
                break;
            case Calendar.FRIDAY:
                sdow = 5;
                break;
            case Calendar.SATURDAY:
                sdow = 6;
                break;
            case Calendar.SUNDAY:
                sdow = 7;
                break;
        }
        return sdow;
    }

    //functie de test pentru introducere de date
    public void set_up_data(View view){

        programDB = FirebaseDatabase.getInstance();
        programReference = programDB.getReference("program");
        //curata db
        programReference.removeValue();
        int t=20;
        int u=45;
        String ds="09:46";
        String de="23:06";
        String ns="23:07";
        String ne="09:45";


        //populeaza db
        for(int i =1; i<=7; i++){
            Program p= new Program(i, t++, t, u++, u, ds, de, ns, ne);
            programReference.push().setValue(p);

        }

    }
    public void set_up_bottom(Program p){
        TextView set_val_day = findViewById(R.id.set_val_day);
        TextView set_val_night = findViewById(R.id.set_val_night);
        if(active_temp){
            set_val_day.setText(String.valueOf(p.tz)+"°C");
            set_val_night.setText(String.valueOf(p.tn)+"°C");

        }
        else{
            set_val_day.setText(String.valueOf(p.uz)+" %");
            set_val_night.setText(String.valueOf(p.un)+" %");
        }
    }
    public void set_cur(Sistem s){
        TextView val_cur = findViewById(R.id.curr_val);
        if(active_temp){
            val_cur.setText(String.valueOf(s.vt_cur)+"°C");
        }
        else
            {
                val_cur.setText(String.valueOf(s.vu_cur)+" %");
            }

        if((s.vt_cur<5) || (s.vt_cur>45)){
            //send message
            mesaj = "!!!Atentie!!! Temperatura curenta inregistrata este : "+String.valueOf(s.vt_cur);
            sendSMSMessage();
        }
        if((s.vu_cur<45) || (s.vu_cur>65)){
            //send message
            mesaj = mesaj = "!!!Atentie!!! Umiditatea curenta inregistrata este : "+String.valueOf(s.vu_cur);
            sendSMSMessage();
        }
    }
}
