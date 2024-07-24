package com.ericadiogo.mycycle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity {
    private TextView usergreeting,todayDate;
    private CardView cardCalendar,cardReminder,cardNotes,cardSettings;
    private DatabaseReference reference;
    private FirebaseAuth mAuth;
    private ImageView imageLogOut;

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
        reference = FirebaseDatabase.getInstance().getReference("users");

//        showName();

        imageLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
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

    private void showName() {
        reference.child(mAuth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserModel userModel = snapshot.getValue(UserModel.class);
                if(userModel != null){
                    String fname = userModel.getFirstName();
                    usergreeting.setText("Hello, " + fname + "!");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    protected String showDate(){
        String date = new SimpleDateFormat("EEE, MMM dd", Locale.getDefault()).format(new Date());
        return date;
    }
}

