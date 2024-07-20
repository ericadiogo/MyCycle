package com.ericadiogo.mycycle;

import static com.ericadiogo.mycycle.R.*;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
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
    private DatabaseReference reference;
    private String loggedUserId, dateSel;
    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;

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
                String date = (month+1) + "/" + day + "/" + year;
                dateSel = date;
                pickedDate.setText(date);
                dailyInfoCard.setVisibility(View.VISIBLE);
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

        btnSaveInfo = view1.findViewById(R.id.btnSaveInfo);
        btnCancelInfo = view1.findViewById(R.id.btnCancelInfo);
        selDate = view1.findViewById(R.id.selDate);
        selDate.setText(dateSel);
        builder.setView(view1);

        AlertDialog dialog = builder.create();
        dialog.setCancelable(true);
        dialog.show();

        /* btnSaveInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference.child(loggedUserId).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        UserModel userModel = snapshot.getValue(UserModel.class);
                        if(userModel != null){
                            int plength = userModel.getpLength();
                            int plengthnew = Integer.valueOf(edtChangePeriod.getText().toString());
                            if(plength != plengthnew){
                                reference.child(loggedUserId).child("pLength").setValue(plengthnew);
                                txtPeriod.setText("Period length: " + plengthnew + " days");
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                dialog.dismiss();
            }
        }); */

        btnCancelInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }
}