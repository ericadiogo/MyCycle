package com.ericadiogo.mycycle;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class HomeActivity extends AppCompatActivity {
    TextView usergreeting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        usergreeting = findViewById(R.id.userGreeting);

        greetingUser();
    }

    public void greetingUser(){
        Intent intent = getIntent();
        usergreeting.setText("Hello, " + intent.getStringExtra("name") + "!");
    }

}