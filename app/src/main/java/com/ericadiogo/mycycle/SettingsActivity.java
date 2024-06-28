package com.ericadiogo.mycycle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SettingsActivity extends AppCompatActivity {
    private LinearLayout settingsBack;
    private TextView userFullName,userEmail;
    private DatabaseReference reference;
    private String loggedUserId;
    private FirebaseAuth mAuth;
    private Button btnEditProfile,btnLogOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_settings);

        settingsBack = findViewById(R.id.settingsBack);
       // userFullName = findViewById(R.id.userFullName);
        userEmail = findViewById(R.id.userEmail);
        //btnEditProfile = findViewById(R.id.btnEditProfile);
        btnLogOut = findViewById(R.id.btnlogOut);
        mAuth = FirebaseAuth.getInstance();
        loggedUserId = mAuth.getUid();
        reference = FirebaseDatabase.getInstance().getReference("users");

        showNameEmail();

        settingsBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingsActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(SettingsActivity.this,EditProfileActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Intent intent = new Intent(SettingsActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void showNameEmail() {
        reference.child(loggedUserId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserModel userModel = snapshot.getValue(UserModel.class);
                if(userModel != null){
                    String fname = userModel.getFirstName();
                    String lname = userModel.getLastName();
                    String email = userModel.getEmail();

                    userFullName.setText(String.format("%s %s", fname, lname));
                    userEmail.setText(email);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}