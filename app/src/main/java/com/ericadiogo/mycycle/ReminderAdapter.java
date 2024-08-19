package com.ericadiogo.mycycle;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.ReminderViewHolder> {
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference reference3 = database.getReference("reminders");
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private String reminderId = reference3.push().getKey();
    private List<Reminder> reminderList;
    private Context context;

    public ReminderAdapter(List<Reminder> reminderList) {
        this.reminderList = reminderList;
    }

    @NonNull
    @Override
    public ReminderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reminder_item, parent, false);
        return new ReminderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReminderViewHolder holder, int position) {
        Reminder reminder = reminderList.get(position);
        holder.remItemName.setText(reminder.getTitle());
        holder.remDateTime.setText(reminder.getDateTime());
        holder.remRepInfo.setText(reminder.getRepetitionInfo());

        holder.editReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentPosition = holder.getAdapterPosition();
                if (currentPosition != RecyclerView.NO_POSITION) {
                    Reminder editRem = reminderList.get(currentPosition);
                    View editView = LayoutInflater.from(view.getContext()).inflate(R.layout.edit_reminder, null);
                    AlertDialog dialog = new AlertDialog.Builder(view.getContext()).setView(editView).create();

                    Button saveButton11 = editView.findViewById(R.id.saveButton11);
                    Button cancelButton11 = editView.findViewById(R.id.cancelButton11);
                    EditText editRemTitle = editView.findViewById(R.id.editRemTitle);
                    TextView editDateTime = editView.findViewById(R.id.editDateTime);
                    TextView editDateTime2 = editView.findViewById(R.id.editDateTime2);
                    EditText editRemInterval = editView.findViewById(R.id.editRemInterval);

                    editRemTitle.setText(editRem.getTitle());
                    editDateTime2.setText(editRem.getDateTime());
                    editRemInterval.setText(editRem.getRepetitionInfo());

                    Button selectButton2 = editView.findViewById(R.id.selectButton2);
                    selectButton2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Calendar pickedDate = Calendar.getInstance();
                            int year = pickedDate.get(Calendar.YEAR);
                            int month = pickedDate.get(Calendar.MONTH);
                            int day = pickedDate.get(Calendar.DAY_OF_MONTH);
                            int hour = pickedDate.get(Calendar.HOUR_OF_DAY);
                            int minute = pickedDate.get(Calendar.MINUTE);

                            DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext(), new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                    TimePickerDialog timePickerDialog = new TimePickerDialog(view.getContext(), new TimePickerDialog.OnTimeSetListener() {
                                        @Override
                                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                            pickedDate.set(year, month, dayOfMonth, hourOfDay, minute);
                                            Log.d("DateAndTime", "Picked date and time " + pickedDate.getTimeInMillis());
                                            editDateTime.setText(String.valueOf(pickedDate.getTimeInMillis()));
                                            if (minute < 10) {
                                                editDateTime2.setText(dayOfMonth + "/" + month + "/" + year + ", " + hourOfDay + ":0" + minute);
                                            } else {
                                                editDateTime2.setText(dayOfMonth + "/" + month + "/" + year + ", " + hourOfDay + ":" + minute);
                                            }
                                        }
                                    }, hour, minute, true);
                                    timePickerDialog.show();
                                }
                            }, year, month, day);
                            datePickerDialog.show();
                        }
                    });

                    saveButton11.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            long timeDelayInSeconds = 0;
                            if (editRemTitle.getText().toString().isEmpty()) {
                                editRemTitle.requestFocus();
                                editRemTitle.setError("Please provide a title.");
                            } else {
                                if (editDateTime.getText().toString().isEmpty()) {
                                    Toast.makeText(view.getContext(), "Please select date and time.", Toast.LENGTH_SHORT).show();
                                } else if (editRemInterval.getText().toString().isEmpty()) {
                                    Toast.makeText(view.getContext(), "Please provide a repeat interval.", Toast.LENGTH_SHORT).show();
                                } else {
                                    Calendar pickedDate = Calendar.getInstance();
                                    pickedDate.setTimeInMillis(Long.parseLong(editDateTime.getText().toString()));
                                    timeDelayInSeconds = (pickedDate.getTimeInMillis() / 1000L) - (Calendar.getInstance().getTimeInMillis() / 1000L);
                                    if (timeDelayInSeconds < 0) {
                                        Toast.makeText(view.getContext(), "Can't set reminder for past date", Toast.LENGTH_SHORT).show();
                                        return;
                                    }

                                    Toast.makeText(view.getContext(), "Reminder edited", Toast.LENGTH_SHORT).show();
                                }
                            }

                            int updatedPosition = holder.getAdapterPosition();
                            if (updatedPosition != RecyclerView.NO_POSITION) {
                                Reminder updatedReminder = reminderList.get(updatedPosition);
                                updatedReminder.setTitle(editRemTitle.getText().toString());
                                updatedReminder.setDateTime(editDateTime2.getText().toString());
                                updatedReminder.setRepetitionInfo(editRemInterval.getText().toString());

                                reminderId = updatedReminder.getReminderId();
                                reference3.child(mAuth.getUid()).child(reminderId).setValue(updatedReminder);
                                notifyItemChanged(updatedPosition);

                                int intervalHours = Integer.parseInt(editRemInterval.getText().toString());
                                createWorkRequest(editRemTitle.getText().toString(),"", timeDelayInSeconds, intervalHours);
                                dialog.dismiss();
                            }
                        }
                    });

                    cancelButton11.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

                    dialog.show();
                }
            }
        });

        holder.deleteReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentPosition = holder.getAdapterPosition();
                if (currentPosition != RecyclerView.NO_POSITION && currentPosition < reminderList.size()) {
                    Reminder remDelete = reminderList.get(currentPosition);
                    String reminderId = remDelete.getReminderId();

                    if (reminderId != null) {
                        View deleteView = LayoutInflater.from(view.getContext()).inflate(R.layout.delete_reminder, null);
                        AlertDialog dialog = new AlertDialog.Builder(view.getContext()).setView(deleteView).create();

                        Button btnYes = deleteView.findViewById(R.id.btnYes);
                        Button btnNo = deleteView.findViewById(R.id.btnNo);

                        btnYes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (currentPosition != RecyclerView.NO_POSITION && currentPosition < reminderList.size()) {
                                    reminderList.remove(currentPosition);
                                    notifyItemRemoved(currentPosition);
                                }
                                reference3.child(mAuth.getUid()).child(reminderId).removeValue();
                                dialog.dismiss();
                            }
                        });

                        btnNo.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });

                        dialog.show();
                    } else {
                        Log.e("ReminderAdapter", "reminderId is null, can't show delete dialog.");
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return reminderList.size();
    }

    public static class ReminderViewHolder extends RecyclerView.ViewHolder {
        TextView remItemName, remDateTime, remRepInfo;
        ImageView editReminder, deleteReminder;

        public ReminderViewHolder(@NonNull View itemView) {
            super(itemView);
            remItemName = itemView.findViewById(R.id.remItemName);
            remDateTime = itemView.findViewById(R.id.remDateTime);
            remRepInfo = itemView.findViewById(R.id.remRepInfo);
            editReminder = itemView.findViewById(R.id.editReminder);
            deleteReminder = itemView.findViewById(R.id.deleteReminder);
        }
    }

    private void createWorkRequest(String title, String reminderType, long delay, int intervalHours) {
        Data inputData = new Data.Builder().putString("Title", "Remember: " + title).putString("Message",reminderType).build();

        if (intervalHours > 0) {
            WorkRequest reminderWorkRequest = new PeriodicWorkRequest.Builder(ReminderWorker.class, intervalHours, TimeUnit.HOURS)
                    .setInitialDelay(delay, TimeUnit.SECONDS).setInputData(inputData).build();
            WorkManager.getInstance(context).enqueue(reminderWorkRequest);
        } else {
            WorkRequest reminderWorkRequest = new OneTimeWorkRequest.Builder(ReminderWorker.class).setInitialDelay(delay, TimeUnit.SECONDS)
                    .setInputData(inputData).build();
            WorkManager.getInstance(context).enqueue(reminderWorkRequest);
        }
    }

}

