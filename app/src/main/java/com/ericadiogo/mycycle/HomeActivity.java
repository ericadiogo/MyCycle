package com.ericadiogo.mycycle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.OptIn;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.media3.common.util.Log;
import androidx.media3.common.util.UnstableApi;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity {
    private TextView usergreeting,todayDate,periodDay,fertDay,pregChance;
    private CardView cardCalendar,cardReminder,cardNotes,cardSettings;
    private DatabaseReference reference1,reference2;
    private FirebaseAuth mAuth;
    private ImageView imageLogOut;
    private int periodlength,cyclelength;
    private String lastperiod,currentperiodstart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        todayDate = findViewById(R.id.todayDate);
        usergreeting = findViewById(R.id.userGreeting);
        mAuth = FirebaseAuth.getInstance();
        todayDate.setText(showDate());
        cardCalendar = findViewById(R.id.cardCalendar);
        cardReminder = findViewById(R.id.cardReminder);
        cardNotes = findViewById(R.id.cardNotes);
        cardSettings = findViewById(R.id.cardSettings);
        imageLogOut = findViewById(R.id.imageLogOut);
        periodDay = findViewById(R.id.periodDay);
        fertDay = findViewById(R.id.fertDay);
        pregChance = findViewById(R.id.pregChance);
        reference1 = FirebaseDatabase.getInstance().getReference("users");
        reference2 = FirebaseDatabase.getInstance().getReference("dailyinfo");

        getInitialInfo();

        imageLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                mAuth = null;
                Intent intent = new Intent(HomeActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        cardCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this,CalendarActivity.class);
                startActivity(intent);
                finish();
            }
        });

        cardReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this,RemindersActivity.class);
                startActivity(intent);
                finish();
            }
        });

        cardNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, NotesActivity.class);
                startActivity(intent);
                finish();
            }
        });

        cardSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this,SettingsActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void getInitialInfo() {
        FirebaseUser user = mAuth.getCurrentUser();

        if(user != null) {
            reference1.child(mAuth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    UserModel userModel = snapshot.getValue(UserModel.class);
                    if(userModel != null){
                        int plength = userModel.getpLength();
                        int clength = userModel.getcLength();
                        String lperiod = userModel.getLastPeriod();
                        periodlength = plength;
                        cyclelength = clength;
                        lastperiod = lperiod;
                        String fname = userModel.getFirstName();
                        usergreeting.setText("Hello, " + fname + "!");

                        try {
                            showNextPeriod();
                            showFertilePeriod();
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    private void showNextPeriod() throws ParseException {
        SimpleDateFormat newformat = new SimpleDateFormat("dd-MM-yyyy");
        Calendar calendar = Calendar.getInstance();
        if (lastperiod != null) {
            Date lperioddate = null;
            try {
                lperioddate = newformat.parse(lastperiod);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            calendar.setTime(lperioddate);
            calendar.add(Calendar.DAY_OF_MONTH, cyclelength);
            Date time = calendar.getTime();
            String date = new SimpleDateFormat("EEE, MMM dd").format(time);
            periodDay.setText(date);

        }
    }

    private void showFertilePeriod(){
        SimpleDateFormat newformat = new SimpleDateFormat("dd-MM-yyyy");
        Calendar calendar = Calendar.getInstance();
        Date today = new Date();

        if (lastperiod != null) {
            Date lperioddate = null;
            try {
                lperioddate = newformat.parse(lastperiod);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            calendar.setTime(lperioddate);
            calendar.add(Calendar.DAY_OF_MONTH,(int)(cyclelength/2));
            Date fd = calendar.getTime();
            String fertileday = new SimpleDateFormat("EEE, MMM dd").format(fd);
            fertDay.setText(fertileday);

            calendar.setTime(fd);
            calendar.add(Calendar.DAY_OF_MONTH,-2);
            Date a = calendar.getTime();

            calendar.setTime(fd);
            calendar.add(Calendar.DAY_OF_MONTH,2);
            Date b = calendar.getTime();

            calendar.setTime(fd);
            calendar.add(Calendar.DAY_OF_MONTH,-5);
            Date c = calendar.getTime();

            calendar.setTime(fd);
            calendar.add(Calendar.DAY_OF_MONTH,6);
            Date d = calendar.getTime();

            if(today.after(a) && today.before(b)) {
                pregChance.setText("Pregnancy chance: HIGH");
            } else {
                if((today.after(c) && today.before(a)) || (today.after(b) && today.before(d))){
                    pregChance.setText("Pregnancy chance: Medium");
                } else {
                    pregChance.setText("Pregnancy chance: Low");
                }
            }
        }
    }

    @Override
    protected void onStart(){
        super.onStart();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser == null){
            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    protected String showDate(){
        String date = new SimpleDateFormat("EEE, MMM dd", Locale.getDefault()).format(new Date());
        return date;
    }
}

