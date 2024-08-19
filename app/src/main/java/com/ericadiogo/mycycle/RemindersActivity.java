package com.ericadiogo.mycycle;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class RemindersActivity extends AppCompatActivity {
    private LinearLayout remindersBack;
    private CardView addReminder;
    private RecyclerView recViewReminder;
    private List<Reminder> reminderList;
    private ReminderAdapter reminderAdapter;
    private FirebaseDatabase database;
    private DatabaseReference reference3;
    private FirebaseAuth mAuth;
    private String reminderId;
    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminders);

        remindersBack = findViewById(R.id.remindersBack);
        addReminder = findViewById(R.id.addReminder);
        recViewReminder = findViewById(R.id.recViewReminder);
        reminderList = new ArrayList<>();
        reminderAdapter = new ReminderAdapter(reminderList);
        recViewReminder.setAdapter(reminderAdapter);
        database = FirebaseDatabase.getInstance();
        reference3 = database.getReference("reminders");
        mAuth = FirebaseAuth.getInstance();
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recViewReminder.setLayoutManager(layoutManager);

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
                addReminder();
            }
        });
    }

    private void addReminder() {
        if (Build.VERSION.SDK_INT >= 33) {
            if(!NotificationManagerCompat.from(this).areNotificationsEnabled()) {
                showNotificationPermissionDialog();
            } else {
                addReminderDialog();
            }
        } else {
            addReminderDialog();
        }
    }

    private void addReminderDialog() {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.reminder_dialog, null);
        AlertDialog dialog = new AlertDialog.Builder(this).setView(dialogView).show();

        Button btnCancel = dialogView.findViewById(R.id.btnCancel10);
        CardView cardTimeDate = dialogView.findViewById(R.id.cardTimeDate);
        Button selectButton = dialogView.findViewById(R.id.selectButton);
        Button btnSave = dialogView.findViewById(R.id.btnSave10);
        EditText reminderName = dialogView.findViewById(R.id.reminderName);
        TextView remDateTime = dialogView.findViewById(R.id.remDateTime);
        TextView remDateTime2 = dialogView.findViewById(R.id.remDateTime2);
        EditText reminderInterval = dialogView.findViewById(R.id.reminderInterval);

        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar pickedDate = Calendar.getInstance();
                int year = pickedDate.get(Calendar.YEAR);
                int month = pickedDate.get(Calendar.MONTH);
                int day = pickedDate.get(Calendar.DAY_OF_MONTH);
                int hour = pickedDate.get(Calendar.HOUR_OF_DAY);
                int minute = pickedDate.get(Calendar.MINUTE);

                DatePickerDialog datePickerDialog = new DatePickerDialog(RemindersActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        TimePickerDialog timePickerDialog = new TimePickerDialog(RemindersActivity.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                pickedDate.set(year, month, dayOfMonth, hourOfDay, minute);
                                Log.d("DateAndTime", "Picked date and time " + pickedDate.getTimeInMillis());
                                remDateTime.setText(String.valueOf(pickedDate.getTimeInMillis()));
                                if (minute < 10) {
                                    remDateTime2.setText(dayOfMonth + "/" + month + "/" + year + ", " + hourOfDay + ":0" + minute);
                                    cardTimeDate.setVisibility(View.VISIBLE);
                                } else {
                                    remDateTime2.setText(dayOfMonth + "/" + month + "/" + year + ", " + hourOfDay + ":" + minute);
                                    cardTimeDate.setVisibility(View.VISIBLE);
                                }
                            }
                        }, hour, minute, true);
                        timePickerDialog.show();
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (reminderName.getText().toString().isEmpty()) {
                    reminderName.requestFocus();
                    reminderName.setError("Please provide a title.");
                } else {
                    if (remDateTime.getText().toString().isEmpty()) {
                        Toast.makeText(RemindersActivity.this, "Please select date and time.", Toast.LENGTH_SHORT).show();
                    } else if (reminderInterval.getText().toString().isEmpty()) {
                        Toast.makeText(RemindersActivity.this, "Please provide a repeat interval.", Toast.LENGTH_SHORT).show();
                    } else {
                        Calendar pickedDate = Calendar.getInstance();
                        pickedDate.setTimeInMillis(Long.parseLong(remDateTime.getText().toString()));
                        long timeDelayInSeconds = (pickedDate.getTimeInMillis() / 1000L) - (Calendar.getInstance().getTimeInMillis() / 1000L);
                        if (timeDelayInSeconds < 0) {
                            Toast.makeText(RemindersActivity.this, "Can't set reminder for past date", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        reminderId = reference3.push().getKey();
                        Reminder rem = new Reminder(reminderName.getText().toString(), remDateTime2.getText().toString(), reminderInterval.getText().toString());
                        rem.setReminderId(reminderId);

                        if (reminderId != null) {
                            reference3.child(mAuth.getUid()).child(reminderId).setValue(rem).addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    reminderList.add(rem);
                                    reminderAdapter.notifyDataSetChanged();
                                    Toast.makeText(RemindersActivity.this, "Reminder added", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                        int intervalHours = Integer.parseInt(reminderInterval.getText().toString());
                        createWorkRequest(reminderName.getText().toString(),"", timeDelayInSeconds, intervalHours);
                        dialog.dismiss();
                    }
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }


    private void createWorkRequest(String title, String reminderType, long delay, int intervalHours) {
        Data inputData = new Data.Builder().putString("Title", "Remember: " + title).putString("Message",reminderType).build();

        if (intervalHours > 0) {
            WorkRequest reminderWorkRequest = new PeriodicWorkRequest.Builder(ReminderWorker.class, intervalHours, TimeUnit.HOURS)
                    .setInitialDelay(delay, TimeUnit.SECONDS).setInputData(inputData).build();
            WorkManager.getInstance(this).enqueue(reminderWorkRequest);
        } else {
            WorkRequest reminderWorkRequest = new OneTimeWorkRequest.Builder(ReminderWorker.class).setInitialDelay(delay, TimeUnit.SECONDS)
                    .setInputData(inputData).build();
            WorkManager.getInstance(this).enqueue(reminderWorkRequest);
        }
    }

    private String getCurrentDateAndTime(long millis) {
        return new SimpleDateFormat("dd-MM-YYYY hh:mm a", Locale.getDefault()).format(new Date(millis));
    }

    private void showNotificationPermissionDialog() {
        if(!NotificationManagerCompat.from(this).areNotificationsEnabled()) {
            new MaterialAlertDialogBuilder(this, com.google.android.material.R.style.MaterialAlertDialog_Material3)
                    .setTitle("Notification Permission").setMessage("You need to allow reminder notifications.")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                notificationPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS);
                            }
                        }
                    })
                    .setNegativeButton("Cancel", null).show();
        } else {
            addReminderDialog();
        }
    }

    private final ActivityResultLauncher<String> notificationPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(),
        isGranted -> {
            if (!isGranted) {
                Toast.makeText(this, "Notification permission granted.", Toast.LENGTH_SHORT).show();
                addReminderDialog();
            } else {
                if (Build.VERSION.SDK_INT >= 33) {
                if (shouldShowRequestPermissionRationale(android.Manifest.permission.POST_NOTIFICATIONS)) {
                    showNotificationPermissionDialog();
                } else {
                    showSettingsDialog();
                }
            }
        }
    });

    private void showSettingsDialog() {
        new MaterialAlertDialogBuilder(RemindersActivity.this, com.google.android.material.R.style.MaterialAlertDialog_Material3)
                .setTitle("Notification Permission").setMessage("You need to allow reminder notifications.")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        intent.setData(Uri.parse("package:" + getApplicationContext().getPackageName()));
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Cancel", null).show();
    }

    private void showReminder() {
        reference3.child(mAuth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    reminderList.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Reminder reminder = dataSnapshot.getValue(Reminder.class);
                        if (reminder != null) {
                            reminderList.add(reminder);
                        }
                    }
                    reminderAdapter.notifyDataSetChanged();
                } else {
                    Log.d("RemindersActivity", "Datasnapshot does not exists or is empty");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(RemindersActivity.this, "Failed to load reminders.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}