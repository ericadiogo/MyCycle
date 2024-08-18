package com.ericadiogo.mycycle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class RemindersActivity extends AppCompatActivity {
    private LinearLayout remindersBack;
    private CardView addReminder;
    private RecyclerView recViewReminder;
    private List<Reminder> reminderListç
    private ReminderAdapter reminderAdapter;
    private FirebaseDatabase database;
    private DatabaseReference reference3;
    private String reminderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reminders);

        remindersBack = findViewById(R.id.remindersBack);
        addReminder = findViewById(R.id.addReminder);
        recViewReminder = findViewById(R.id.recViewReminder);
        reminderList = new ArrayList<>();
        reminderAdapter = new ReminderAdapter(reminderList);
        recViewReminder.setAdapter(reminderAdapter);
        database = FirebaseDatabase.getInstance();
        reference3 = database.getReference("reminders");

        recViewReminder.setLayoutManager(new LinearLayoutManager(this));
        showReminder();

        remindersBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RemindersActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        addReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showReminder();
            }
        });
    }


}