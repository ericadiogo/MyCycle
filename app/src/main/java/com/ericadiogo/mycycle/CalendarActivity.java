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
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CalendarActivity extends AppCompatActivity {
    private LinearLayout calendarBack;
    private CalendarView calView;
    private CardView dailyInfoCard;
    private TextView pickedDate;
    private ImageView addInfobtn;
    private String loggedUserId, dateSel;
    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;
    private FirebaseDatabase database;
    private DatabaseReference reference;

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
        loggedUserId = mAuth.getUid();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("dailyinfo");

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
                DailyInfo dailyInfo = new DailyInfo(ffdate,true);
                reference.child(mAuth.getUid()).child(dailyInfo.getDate()).setValue(dailyInfo);
            }
        });

        addInfobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogDailyInfo();
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
        //showInfo();

        btnSaveInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference.child(mAuth.getUid()).child(dateSel).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        DailyInfo dailyInfo = snapshot.getValue(DailyInfo.class);
                        if(dailyInfo != null){
                            Boolean pstart = dailyInfo.getPerStart();
                            Boolean pstartnew;
                            if(perStartCB.isChecked()){
                                pstartnew = true;
                                if(!pstart.equals(pstartnew)){
                                    reference.child(mAuth.getUid()).child(dateSel).child("perStart").setValue(pstartnew);
                                }
                            } else {
                                pstartnew = false;
                                if(!pstart.equals(pstartnew)){
                                    reference.child(mAuth.getUid()).child(dateSel).child("perStart").setValue(pstartnew);
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

        btnCancelInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        /* private void showInfo() {
            reference.child(loggedUserId).child(dateSel).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    DailyInfo dailyInfo = snapshot.getValue(DailyInfo.class);
                    CheckBox perStartCB;
                    perStartCB = view1.findViewById(id.perStartCB);

                    if(DailyInfo != null){
                        Boolean pstarts = dailyInfo.getPerStart();


                        userFnameSet.setText(fname);
                        userLnameSet.setText(lname);
                        useremailSet.setText(email);
                        txtPeriod.setText("Period length: " + plength + " days");
                        txtCycleLength.setText("Cycle length: " + clength + " days");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        } */
    }
}