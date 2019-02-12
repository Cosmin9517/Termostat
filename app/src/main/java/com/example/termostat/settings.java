package com.example.termostat;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class settings extends AppCompatActivity {

    private FirebaseDatabase programDB;
    private DatabaseReference programReference;
    private List<Program> listaPrograme;
    private int selectedDay=1;
    private int mHour, mMinute;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        programDB = FirebaseDatabase.getInstance();
        programReference = programDB.getReference("program");
        listaPrograme= new ArrayList<>();
        programReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listaPrograme.clear();
                for (DataSnapshot datasnapshotchild : dataSnapshot.getChildren()) {
                    Program p = datasnapshotchild.getValue(Program.class);
                    listaPrograme.add(p);
                    setup_settings(selectedDay);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    public void setup_settings(int day){
        TextView nightStart = findViewById(R.id.nightStart);
        TextView nightEnd = findViewById(R.id.nightEnd);
        TextView nightUmid = findViewById(R.id.nightUmid);
        TextView nightTemp = findViewById(R.id.nightTemp);
        TextView dayStart = findViewById(R.id.dayStart);
        TextView dayEnd = findViewById(R.id.dayEnd);
        TextView dayUmid = findViewById(R.id.dayUmid);
        TextView dayTemp = findViewById(R.id.dayTemp);

        for(Program p:listaPrograme){
            if(p.ziua==day){
                nightTemp.setText(String.valueOf(p.tn)+"°C");
                nightUmid.setText(String.valueOf(p.un)+" %");
                dayTemp.setText(String.valueOf(p.tz)+"°C");
                dayUmid.setText(String.valueOf(p.uz)+" %");

                nightStart.setText(p.ns);
                nightEnd.setText(p.ne);
                dayStart.setText(p.ds);
                dayEnd.setText(p.de);

            }
        }


    }
    public void clickZi(View v){

        TextView luni = findViewById(R.id.label_luni);
        TextView marti = findViewById(R.id.label_marti);
        TextView miercuri = findViewById(R.id.label_miercuri);
        TextView joi = findViewById(R.id.label_joi);
        TextView vineri = findViewById(R.id.label_Vineri);
        TextView sambata = findViewById(R.id.label_sambata);
        TextView duminica = findViewById(R.id.label_duminica);

        switch (v.getId()){
            case (R.id.label_luni):
                setup_settings(1);
                luni.setBackgroundResource(R.drawable.buton);
                marti.setBackgroundResource(0);
                miercuri.setBackgroundResource(0);
                joi.setBackgroundResource(0);
                vineri.setBackgroundResource(0);
                sambata.setBackgroundResource(0);
                duminica.setBackgroundResource(0);
                break;
            case (R.id.label_marti):
                setup_settings(2);
                luni.setBackgroundResource(0);
                marti.setBackgroundResource(R.drawable.buton);
                miercuri.setBackgroundResource(0);
                joi.setBackgroundResource(0);
                vineri.setBackgroundResource(0);
                sambata.setBackgroundResource(0);
                duminica.setBackgroundResource(0);
                break;
            case (R.id.label_miercuri):
                setup_settings(3);
                luni.setBackgroundResource(0);
                marti.setBackgroundResource(0);
                miercuri.setBackgroundResource(R.drawable.buton);
                joi.setBackgroundResource(0);
                vineri.setBackgroundResource(0);
                sambata.setBackgroundResource(0);
                duminica.setBackgroundResource(0);
                break;
            case (R.id.label_joi):
                setup_settings(4);
                luni.setBackgroundResource(0);
                marti.setBackgroundResource(0);
                miercuri.setBackgroundResource(0);
                joi.setBackgroundResource(R.drawable.buton);
                vineri.setBackgroundResource(0);
                sambata.setBackgroundResource(0);
                duminica.setBackgroundResource(0);
                break;
            case (R.id.label_Vineri):
                setup_settings(5);
                luni.setBackgroundResource(0);
                marti.setBackgroundResource(0);
                miercuri.setBackgroundResource(0);
                joi.setBackgroundResource(0);
                vineri.setBackgroundResource(R.drawable.buton);
                sambata.setBackgroundResource(0);
                duminica.setBackgroundResource(0);
                break;
            case (R.id.label_sambata):
                setup_settings(6);
                luni.setBackgroundResource(0);
                marti.setBackgroundResource(0);
                miercuri.setBackgroundResource(0);
                joi.setBackgroundResource(0);
                vineri.setBackgroundResource(0);
                sambata.setBackgroundResource(R.drawable.buton);
                duminica.setBackgroundResource(0);
                break;
            case (R.id.label_duminica):
                setup_settings(7);
                luni.setBackgroundResource(0);
                marti.setBackgroundResource(0);
                miercuri.setBackgroundResource(0);
                joi.setBackgroundResource(0);
                vineri.setBackgroundResource(0);
                sambata.setBackgroundResource(0);
                duminica.setBackgroundResource(R.drawable.buton);
                break;

        }
    }
    public void set_time(View v){

        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        final View vf=v;
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                switch (vf.getId()){
                    case (R.id.dayStart):
                        TextView dayStart = findViewById(R.id.dayStart);
                        dayStart.setText(hourOfDay + ":" + minute);
                        break;
                    case (R.id.dayEnd):
                        TextView dayEnd = findViewById(R.id.dayEnd);
                        dayEnd.setText(hourOfDay + ":" + minute);

                        break;
                    case (R.id.nightStart):
                        TextView nightStart = findViewById(R.id.nightStart);
                        nightStart.setText(hourOfDay + ":" + minute);

                        break;
                    case (R.id.nightEnd):
                        TextView nightEnd = findViewById(R.id.nightEnd);
                        nightEnd.setText(hourOfDay + ":" + minute);

                        break;
                }
            }
        }, mHour, mMinute, true);
        timePickerDialog.show();
    }

    public void set_temp_umid(final View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(settings.this);
        View mView = getLayoutInflater().inflate(R.layout.setter, null);
        final EditText val = mView.findViewById(R.id.dialogVal);
        Button update_btn = mView.findViewById(R.id.update_btn);
        builder.setView(mView);
        final AlertDialog dialog = builder.create();
        dialog.show();

        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vt) {
                TextView vtu;
                if(!val.getText().toString().isEmpty()){
                    final double valoare = Double.valueOf(val.getText().toString());
                    switch(v.getId()){
                        case(R.id.dayTemp):
                            if(validare_temp(valoare)){
                            vtu = findViewById(R.id.dayTemp);
                            vtu.setText(String.valueOf(valoare)+"°C");
                            dialog.dismiss();
                            }
                            break;
                        case (R.id.nightTemp):
                            if(validare_temp(valoare)) {
                                vtu = findViewById(R.id.nightTemp);
                                vtu.setText(String.valueOf(valoare)+"°C");
                                dialog.dismiss();
                            }
                            break;
                        case (R.id.dayUmid):
                            if(validare_umid((int)valoare)) {
                                vtu = findViewById(R.id.dayUmid);
                                vtu.setText(String.valueOf((int)valoare)+" %");
                                dialog.dismiss();
                            }
                            break;
                        case (R.id.nightUmid):
                            if(validare_umid((int)valoare)) {
                                vtu = findViewById(R.id.nightUmid);
                                vtu.setText(String.valueOf((int)valoare)+" %");
                                dialog.dismiss();
                            }
                            break;

                    }

                }
            }
        });


    }
    public boolean validare_temp(double t){
        //Valideaza temperatura inainte de a introduce datele
        if((t<5) || (t>45))
            return false;
        return true;
    }
    public boolean validare_umid( int u){
        //Valideaza umiditatea inainte de a introduce datele
        if((u<45) || (u>65))
            return false;
        return true;
    }

    /* ToDo - completat/ajustat functie
    public void validare_intervale_orare(View v){

        TextView dayStart = findViewById(R.id.dayStart);
        TextView dayEnd = findViewById(R.id.dayEnd);
        TextView nightStart = findViewById(R.id.nightStart);
        TextView nightEnd = findViewById(R.id.nightEnd);
        TextView testing = findViewById(R.id.testing);
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        Date ds,de,ns,ne;

        try {
            ds = format.parse(dayStart.getText().toString());
            de = format.parse(dayEnd.getText().toString());
            ns = format.parse(nightStart.getText().toString());
            ne = format.parse(nightEnd.getText().toString());

            long dayTime = de.getTime() - ds.getTime();
            long nightTime = ne.getTime() - ns.getTime();
            //nightTime = (nightTime < 0 ? -nightTime : nightTime);
            dayTime = (dayTime < 0 ? -dayTime : dayTime);
            long diff = nightTime;
            long days = (int) (diff / (1000*60*60*24));
            long hours = (int) ((diff - (1000*60*60*24*days)) / (1000*60*60));
            hours = (hours < 0 ?(24+hours):hours);
            long minutes = (int) (diff - (1000*60*60*24*days) - (1000*60*60*hours)) / (1000*60);
            testing.setText(String.valueOf(hours)+" hours and "+String.valueOf(minutes)+" minutes");

        } catch (ParseException e) {
            e.printStackTrace();
        }


    }*/

}





