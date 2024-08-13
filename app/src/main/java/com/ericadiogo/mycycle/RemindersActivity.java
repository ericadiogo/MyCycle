package com.ericadiogo.mycycle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class RemindersActivity extends AppCompatActivity {
    private LinearLayout remindersBack;
    private CardView cardAddReminder;
    private RecyclerView recViewReminder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reminders);

        remindersBack = findViewById(R.id.remindersBack);
        cardAddReminder = findViewById(R.id.cardAddReminder);
        recViewReminder = findViewById(R.id.recViewReminder);

        recViewReminder.setLayoutManager(new LinearLayoutManager(getApplicationContext()));


        remindersBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RemindersActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        cardAddReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showReminderDialog();
            }
        });
    }

    private void showReminderDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);


        View p_layout = getLayoutInflater().inflate(R.layout.newreminder_dialog, null);
        final EditText edtWeight = p_layout.findViewById(R.id.edtWeight);
        Button btnSave = p_layout.findViewById(R.id.btnSave8);
        Button btnCancel = p_layout.findViewById(R.id.btnCancel8);

        builder.setView(p_layout);
        AlertDialog dialog = builder.create();
        dialog.setCancelable(true);
        dialog.show();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference.child(loggedUserId).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        UserModel userModel = snapshot.getValue(UserModel.class);
                        if (userModel != null) {
                            int weight = userModel.getWeight();
                            int weightnew = Integer.valueOf(edtWeight.getText().toString());
                            if (weight != weightnew) {
                                if(weightnew < 0) {
                                    Toast.makeText(SettingsActivity.this,"Please, type a positive number.",Toast.LENGTH_SHORT).show();
                                } else {
                                    if (weightnew > 350) {
                                        Toast.makeText(SettingsActivity.this, "Please, type a number less than 350.", Toast.LENGTH_SHORT).show();
                                    } else {
                                        reference.child(loggedUserId).child("weight").setValue(weightnew);
                                        txtPeriod.setText("Weight: " + weightnew + " kg");
                                    }
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                dialog.dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }
}