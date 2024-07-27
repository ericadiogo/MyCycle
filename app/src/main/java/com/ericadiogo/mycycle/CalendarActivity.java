package com.ericadiogo.mycycle;

import static com.ericadiogo.mycycle.R.*;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CalendarActivity extends AppCompatActivity {
    private LinearLayout calendarBack;
    private CalendarView calView;
    private CardView dailyInfoCard;
    private TextView pickedDate;
    private Button addInfobtn;
    private String dateSel,currentperiodstart;
    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;
    private FirebaseDatabase database;
    private DatabaseReference reference1,reference2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_calendar);

        dailyInfoCard = findViewById(R.id.dailyInfoCard);
        calendarBack = findViewById(R.id.calendarBack);
        calView = findViewById(R.id.calView);
        pickedDate = findViewById(R.id.pickedDate);
        addInfobtn = findViewById(R.id.addInfobtn);
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        reference1 = database.getReference("users");
        reference2 = database.getReference("dailyinfo");

        calendarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CalendarActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        calView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                Date seldate = new Date(month+1+ "/" + day + "/" + year);
                String fdate = new SimpleDateFormat("dd/MM/yyyy").format(seldate);
                String ffdate = new SimpleDateFormat("dd-MM-yyy").format(seldate);
                Boolean ps = false;
                dateSel = ffdate;
                pickedDate.setText(fdate);
                dailyInfoCard.setVisibility(View.VISIBLE);

                reference2.child(mAuth.getUid()).child(dateSel).runTransaction(new Transaction.Handler() {
                    @NonNull
                    @Override
                    public Transaction.Result doTransaction(@NonNull MutableData currentData) {
                        if(currentData.getValue() == null){
                            DailyInfo dailyInfo = new DailyInfo(dateSel,ps);
                            currentData.setValue(dailyInfo);
                            return Transaction.success(currentData);
                        } else {
                            return Transaction.abort();
                        }
                    }

                    @Override
                    public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {

                    }
                });
            }
        });

        addInfobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dateSel == null){
                    Toast.makeText(CalendarActivity.this,"Please, select a date.",Toast.LENGTH_SHORT).show();
                } else {
                    showDialogDailyInfo();
                }
            }
        });
    }

    private void showDialogDailyInfo() {
        AlertDialog.Builder builder = new AlertDialog.Builder(CalendarActivity.this);
        View view1 = getLayoutInflater().inflate(R.layout.daily_info,null);
        Button btnSaveInfo,btnCancelInfo;
        TextView selDate;
        CheckBox perStartCB;

        btnSaveInfo = view1.findViewById(R.id.btnSaveInfo);
        btnCancelInfo = view1.findViewById(R.id.btnCancelInfo);
        selDate = view1.findViewById(R.id.selDate);
        selDate.setText(pickedDate.getText().toString());
        perStartCB = view1.findViewById(id.perStartCB);
        builder.setView(view1);

        AlertDialog dialog = builder.create();
        dialog.setCancelable(true);
        dialog.show();

        reference2.child(mAuth.getUid()).child(dateSel).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DailyInfo dailyInfo = snapshot.getValue(DailyInfo.class);
                if(dailyInfo != null){
                    Boolean pstart = dailyInfo.getPerStart();
                    if(pstart){
                        perStartCB.setChecked(true);
                    } else {
                        perStartCB.setChecked(false);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnSaveInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference2.child(mAuth.getUid()).child(dateSel).runTransaction(new Transaction.Handler() {
                    @NonNull
                    @Override
                    public Transaction.Result doTransaction(@NonNull MutableData currentData) {
                        DailyInfo dailyInfo = currentData.getValue(DailyInfo.class);

                        if(dailyInfo == null){
                            dailyInfo = new DailyInfo(dateSel,false);
                        }
                        Boolean pstart = dailyInfo.getPerStart();
                        Boolean pstartnew;

                        if(perStartCB.isChecked()){
                            pstartnew = true;
                            if(!pstart.equals(pstartnew)){
                                reference2.child(mAuth.getUid()).child(dateSel).child("perStart").setValue(pstartnew);
                                currentperiodstart = dailyInfo.getDate();
                                setNewPeriod();
                            }
                        } else {
                            pstartnew = false;
                            if(!pstart.equals(pstartnew)){
                                reference2.child(mAuth.getUid()).child(dateSel).child("perStart").setValue(pstartnew);
                            }
                        }
                        return Transaction.success(currentData);
                    }

                    @Override
                    public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {

                    }
                });
/*                reference2.child(mAuth.getUid()).child(dateSel).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        DailyInfo dailyInfo = snapshot.getValue(DailyInfo.class);
                        if(dailyInfo != null){

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                }); */
                dialog.dismiss();
            }
        });

        btnCancelInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    private void setNewPeriod(){
        reference1.child(mAuth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserModel userModel = snapshot.getValue(UserModel.class);
                SimpleDateFormat newformat = new SimpleDateFormat("dd-MM-yyyy");
                Calendar calendar = Calendar.getInstance();
                if(userModel != null){
                    String lastperiod = userModel.getLastPeriod();
                    if (lastperiod != null) {
                        Date lperioddate = null;
                        try {
                            lperioddate = newformat.parse(lastperiod);
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                        if (currentperiodstart != null) {
                            Date cperioddate = null;
                            try {
                                cperioddate = newformat.parse(currentperiodstart);
                            } catch (ParseException e) {
                                throw new RuntimeException(e);
                            }
                            if (cperioddate.after(lperioddate)) {
                                reference1.child(mAuth.getUid()).child("lastPeriod").setValue(currentperiodstart);
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}