package com.ericadiogo.mycycle;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.List;

public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.ReminderViewHolder> {
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference reference3 = database.getReference("reminders");
    private List<Reminder> reminderList;
    private String reminderId = reference3.push().getKey();;

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
                    editDateTime.setText(editRem.getDateTime());
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
                                    }, hour, minute, false);
                                    timePickerDialog.show();
                                }
                            }, year, month, day);
                            datePickerDialog.show();
                        }
                    });

                    saveButton11.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            int updatedPosition = holder.getAdapterPosition();
                            if (updatedPosition != RecyclerView.NO_POSITION) {
                                Reminder updatedReminder = reminderList.get(updatedPosition);
                                updatedReminder.setTitle(editRemTitle.getText().toString());
                                updatedReminder.setDateTime(editDateTime2.getText().toString());
                                updatedReminder.setRepetitionInfo(editRemInterval.getText().toString());

                                reminderId = updatedReminder.getReminderId();
                                reference3.child(reminderId).setValue(updatedReminder);

                                notifyItemChanged(updatedPosition);
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
                if (currentPosition != RecyclerView.NO_POSITION) {
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
                                reminderList.remove(currentPosition);
                                notifyItemRemoved(currentPosition);
                                reference3.child(reminderId).removeValue();

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
}

