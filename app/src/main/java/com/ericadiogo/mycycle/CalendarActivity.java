
package com.ericadiogo.mycycle;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
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
import androidx.core.content.ContextCompat;
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
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

public class CalendarActivity extends AppCompatActivity {
    private static final String TAG = "CalendarActivity";

    private LinearLayout calendarBack;
    private MaterialCalendarView calView;
    private CardView dailyInfoCard;
    private TextView pickedDate;
    private Button addInfobtn;
    private String dateSel, currentperiodstart;
    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;
    private FirebaseDatabase database;
    private DatabaseReference reference1, reference2;
    private Date lpd, cpd, ps, pend;
    private int cyclelength, periodlength;
    private Drawable pdrawable;


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
        pdrawable = ContextCompat.getDrawable(CalendarActivity.this, R.drawable.perioddays);

        getInitialInfo(new InitialInfoCallback() {
            @Override
            public void onInitialInfoLoaded(Date lpd, int periodLength,int cyclelength) {
                Log.d(TAG, "Initial info loaded: lpd = " + lpd + ", periodLength = " + periodLength);
                Collection<CalendarDay> pdates = CalendarDate.getPeriodDates(lpd, periodLength,cyclelength);
                calView.addDecorator(new EventDecorator(pdrawable, pdates,Color.WHITE));
                calView.invalidateDecorators();
            }
        });

        calendarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CalendarActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        calView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                int year = date.getYear();
                int month = date.getMonth() + 1;
                int day = date.getDay();

                Date seldate = new Date(month + "/" + day + "/" + year);
                String fdate = new SimpleDateFormat("dd/MM/yyyy").format(seldate);
                String ffdate = new SimpleDateFormat("dd-MM-yyy").format(seldate);
                dateSel = ffdate;
                pickedDate.setText(fdate);
                dailyInfoCard.setVisibility(View.VISIBLE);

                reference2.child(mAuth.getUid()).child(dateSel).runTransaction(new Transaction.Handler() {
                    @NonNull
                    @Override
                    public Transaction.Result doTransaction(@NonNull MutableData currentData) {
                        if (currentData.getValue() == null) {
                            DailyInfo dailyInfo = new DailyInfo(dateSel);
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
                if (dateSel == null) {
                    Toast.makeText(CalendarActivity.this, "Please, select a date.", Toast.LENGTH_SHORT).show();
                } else {
                    showDialogDailyInfo();
                }
            }
        });


    }

    private void showDialogDailyInfo() {
        AlertDialog.Builder builder = new AlertDialog.Builder(CalendarActivity.this);
        View view1 = getLayoutInflater().inflate(R.layout.daily_info, null);
        Button btnSaveInfo, btnCancelInfo;
        TextView selDate;
        CheckBox perStartCB, perEndCB, intercourseCB, ovtestPositive, ovtestNegative, ovpainCB, acneCB,
                headacheCB, migrainesCB, backachesCB, breastsensCB, crampsCB,
                musclepainCB, pmsCB, weightgainCB, creamyCB, dryCB, foulsmellCB, greenCB, wbloodCB,
                abdominalpainCB, bloatingCB, constipationCB, dyarrheaCB, anxietyCB, fatigueCB, insomniaCB,
                stressCB, tensionCB;

        btnSaveInfo = view1.findViewById(R.id.btnSaveInfo);
        btnCancelInfo = view1.findViewById(R.id.btnCancelInfo);
        selDate = view1.findViewById(R.id.selDate);
        selDate.setText(pickedDate.getText().toString());
        perStartCB = view1.findViewById(R.id.perStartCB);
        perEndCB = view1.findViewById(R.id.perEndCB);
        intercourseCB = view1.findViewById(R.id.intercourseCB);
        ovtestPositive = view1.findViewById(R.id.ovtestPositive);
        ovtestNegative = view1.findViewById(R.id.ovtestNegative);
        ovpainCB = view1.findViewById(R.id.ovpainCB);
        acneCB = view1.findViewById(R.id.acneCB);
        headacheCB = view1.findViewById(R.id.headacheCB);
        migrainesCB = view1.findViewById(R.id.migrainesCB);
        backachesCB = view1.findViewById(R.id.backachesCB);
        breastsensCB = view1.findViewById(R.id.breastsensCB);
        crampsCB = view1.findViewById(R.id.crampsCB);
        musclepainCB = view1.findViewById(R.id.musclepainCB);
        pmsCB = view1.findViewById(R.id.pmsCB);
        weightgainCB = view1.findViewById(R.id.weightgainCB);
        creamyCB = view1.findViewById(R.id.creamyCB);
        dryCB = view1.findViewById(R.id.dryCB);
        foulsmellCB = view1.findViewById(R.id.foulsmellCB);
        greenCB = view1.findViewById(R.id.greenCB);
        wbloodCB = view1.findViewById(R.id.wbloodCB);
        abdominalpainCB = view1.findViewById(R.id.abdominalpainCB);
        bloatingCB = view1.findViewById(R.id.bloatingCB);
        constipationCB = view1.findViewById(R.id.constipationCB);
        dyarrheaCB = view1.findViewById(R.id.dyarrheaCB);
        anxietyCB = view1.findViewById(R.id.anxietyCB);
        fatigueCB = view1.findViewById(R.id.fatigueCB);
        insomniaCB = view1.findViewById(R.id.insomniaCB);
        stressCB = view1.findViewById(R.id.stressCB);
        tensionCB = view1.findViewById(R.id.tensionCB);

        builder.setView(view1);

        AlertDialog dialog = builder.create();
        dialog.setCancelable(true);
        dialog.show();

        reference2.child(mAuth.getUid()).child(dateSel).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DailyInfo dailyInfo = snapshot.getValue(DailyInfo.class);
                if (dailyInfo != null) {
                    if (dailyInfo.getPerStart() != null) {
                        perStartCB.setChecked(dailyInfo.getPerStart());
                    } else {
                        perStartCB.setChecked(false);
                    }
                    if (dailyInfo.getPerEnd() != null) {
                        perEndCB.setChecked(dailyInfo.getPerEnd());
                    } else {
                        perEndCB.setChecked(false);
                    }
                    if (dailyInfo.getIntercourse() != null) {
                        intercourseCB.setChecked(dailyInfo.getIntercourse());
                    } else {
                        intercourseCB.setChecked(false);
                    }
                    if (dailyInfo.getOvPos() != null) {
                        ovtestPositive.setChecked(dailyInfo.getOvPos());
                    } else {
                        ovtestPositive.setChecked(false);
                    }
                    if (dailyInfo.getOvNeg() != null) {
                        ovtestNegative.setChecked(dailyInfo.getOvNeg());
                    } else {
                        ovtestNegative.setChecked(false);
                    }
                    if (dailyInfo.getOvPain() != null) {
                        ovpainCB.setChecked(dailyInfo.getOvPain());
                    } else {
                        ovpainCB.setChecked(false);
                    }
                    if (dailyInfo.getAcne() != null) {
                        acneCB.setChecked(dailyInfo.getAcne());
                    } else {
                        acneCB.setChecked(false);
                    }
                    if (dailyInfo.getHeadache() != null) {
                        headacheCB.setChecked(dailyInfo.getHeadache());
                    } else {
                        headacheCB.setChecked(false);
                    }
                    if (dailyInfo.getMigraines() != null) {
                        migrainesCB.setChecked(dailyInfo.getMigraines());
                    } else {
                        migrainesCB.setChecked(false);
                    }
                    if (dailyInfo.getBackaches() != null) {
                        backachesCB.setChecked(dailyInfo.getBackaches());
                    } else {
                        backachesCB.setChecked(false);
                    }
                    if (dailyInfo.getBreastSens() != null) {
                        breastsensCB.setChecked(dailyInfo.getBreastSens());
                    } else {
                        breastsensCB.setChecked(false);
                    }
                    if (dailyInfo.getCramps() != null) {
                        crampsCB.setChecked(dailyInfo.getCramps());
                    } else {
                        crampsCB.setChecked(false);
                    }
                    if (dailyInfo.getMusclePain() != null) {
                        musclepainCB.setChecked(dailyInfo.getMusclePain());
                    } else {
                        musclepainCB.setChecked(false);
                    }
                    if (dailyInfo.getPms() != null) {
                        pmsCB.setChecked(dailyInfo.getPms());
                    } else {
                        pmsCB.setChecked(false);
                    }
                    if (dailyInfo.getWeightGain() != null) {
                        weightgainCB.setChecked(dailyInfo.getWeightGain());
                    } else {
                        weightgainCB.setChecked(false);
                    }
                    if (dailyInfo.getCreamy() != null) {
                        creamyCB.setChecked(dailyInfo.getCreamy());
                    } else {
                        creamyCB.setChecked(false);
                    }
                    if (dailyInfo.getDry() != null) {
                        dryCB.setChecked(dailyInfo.getDry());
                    } else {
                        dryCB.setChecked(false);
                    }
                    if (dailyInfo.getFoulSmell() != null) {
                        foulsmellCB.setChecked(dailyInfo.getFoulSmell());
                    } else {
                        foulsmellCB.setChecked(false);
                    }
                    if (dailyInfo.getGreen() != null) {
                        greenCB.setChecked(dailyInfo.getGreen());
                    } else {
                        greenCB.setChecked(false);
                    }
                    if (dailyInfo.getWblood() != null) {
                        wbloodCB.setChecked(dailyInfo.getWblood());
                    } else {
                        wbloodCB.setChecked(false);
                    }
                    if (dailyInfo.getAbdominalPain() != null) {
                        abdominalpainCB.setChecked(dailyInfo.getAbdominalPain());
                    } else {
                        abdominalpainCB.setChecked(false);
                    }
                    if (dailyInfo.getBloating() != null) {
                        bloatingCB.setChecked(dailyInfo.getBloating());
                    } else {
                        bloatingCB.setChecked(false);
                    }
                    if (dailyInfo.getConstipation() != null) {
                        constipationCB.setChecked(dailyInfo.getConstipation());
                    } else {
                        constipationCB.setChecked(false);
                    }
                    if (dailyInfo.getDyarrhea() != null) {
                        dyarrheaCB.setChecked(dailyInfo.getDyarrhea());
                    } else {
                        dyarrheaCB.setChecked(false);
                    }
                    if (dailyInfo.getAnxiety() != null) {
                        anxietyCB.setChecked(dailyInfo.getAnxiety());
                    } else {
                        anxietyCB.setChecked(false);
                    }
                    if (dailyInfo.getFatigue() != null) {
                        fatigueCB.setChecked(dailyInfo.getFatigue());
                    } else {
                        fatigueCB.setChecked(false);
                    }
                    if (dailyInfo.getInsomnia() != null) {
                        insomniaCB.setChecked(dailyInfo.getInsomnia());
                    } else {
                        insomniaCB.setChecked(false);
                    }
                    if (dailyInfo.getStress() != null) {
                        stressCB.setChecked(dailyInfo.getStress());
                    } else {
                        stressCB.setChecked(false);
                    }
                    if (dailyInfo.getTension() != null) {
                        tensionCB.setChecked(dailyInfo.getTension());
                    } else {
                        tensionCB.setChecked(false);
                    }
                } else {
                    perStartCB.setChecked(false);
                    perEndCB.setChecked(false);
                    intercourseCB.setChecked(false);
                    ovtestPositive.setChecked(false);
                    ovtestNegative.setChecked(false);
                    ovpainCB.setChecked(false);
                    acneCB.setChecked(false);
                    headacheCB.setChecked(false);
                    migrainesCB.setChecked(false);
                    backachesCB.setChecked(false);
                    breastsensCB.setChecked(false);
                    crampsCB.setChecked(false);
                    musclepainCB.setChecked(false);
                    pmsCB.setChecked(false);
                    weightgainCB.setChecked(false);
                    creamyCB.setChecked(false);
                    dryCB.setChecked(false);
                    foulsmellCB.setChecked(false);
                    greenCB.setChecked(false);
                    wbloodCB.setChecked(false);
                    abdominalpainCB.setChecked(false);
                    bloatingCB.setChecked(false);
                    constipationCB.setChecked(false);
                    dyarrheaCB.setChecked(false);
                    anxietyCB.setChecked(false);
                    fatigueCB.setChecked(false);
                    insomniaCB.setChecked(false);
                    stressCB.setChecked(false);
                    tensionCB.setChecked(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnSaveInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference2.child(mAuth.getUid()).child(dateSel).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        DailyInfo dailyInfo = snapshot.getValue(DailyInfo.class);
                        if (dailyInfo == null) {
                            dailyInfo = new DailyInfo(dateSel);
                        }

                        dailyInfo.setPerStart(perStartCB.isChecked());
                        dailyInfo.setPerEnd(perEndCB.isChecked());
                        dailyInfo.setIntercourse(intercourseCB.isChecked());
                        dailyInfo.setOvPos(ovtestPositive.isChecked());
                        dailyInfo.setOvNeg(ovtestNegative.isChecked());
                        dailyInfo.setOvPain(ovpainCB.isChecked());
                        dailyInfo.setAcne(acneCB.isChecked());
                        dailyInfo.setHeadache(headacheCB.isChecked());
                        dailyInfo.setMigraines(migrainesCB.isChecked());
                        dailyInfo.setBackaches(backachesCB.isChecked());
                        dailyInfo.setBreastSens(breastsensCB.isChecked());
                        dailyInfo.setCramps(crampsCB.isChecked());
                        dailyInfo.setMusclePain(musclepainCB.isChecked());
                        dailyInfo.setPms(pmsCB.isChecked());
                        dailyInfo.setWeightGain(weightgainCB.isChecked());
                        dailyInfo.setCreamy(creamyCB.isChecked());
                        dailyInfo.setDry(dryCB.isChecked());
                        dailyInfo.setFoulSmell(foulsmellCB.isChecked());
                        dailyInfo.setGreen(greenCB.isChecked());
                        dailyInfo.setWblood(wbloodCB.isChecked());
                        dailyInfo.setAbdominalPain(abdominalpainCB.isChecked());
                        dailyInfo.setBloating(bloatingCB.isChecked());
                        dailyInfo.setConstipation(constipationCB.isChecked());
                        dailyInfo.setDyarrhea(dyarrheaCB.isChecked());
                        dailyInfo.setAnxiety(anxietyCB.isChecked());
                        dailyInfo.setFatigue(fatigueCB.isChecked());
                        dailyInfo.setInsomnia(insomniaCB.isChecked());
                        dailyInfo.setStress(stressCB.isChecked());
                        dailyInfo.setTension(tensionCB.isChecked());

                        if (perStartCB.isChecked()) {
                            currentperiodstart = dailyInfo.getDate();
                            setNewPeriod();
                            getInitialInfo(new InitialInfoCallback() {
                                @Override
                                public void onInitialInfoLoaded(Date lpd, int periodLength,int cyclelength) {
                                    Log.d(TAG, "Initial info loaded: lpd = " + lpd + ", periodLength = " + periodLength);
                                    Collection<CalendarDay> pdates = CalendarDate.getPeriodDates(lpd, periodLength,cyclelength);
                                    calView.addDecorator(new EventDecorator(pdrawable, pdates,Color.WHITE));
                                    calView.invalidateDecorators();
                                }
                            });
                        }
                        reference2.child(mAuth.getUid()).child(dateSel).setValue(dailyInfo);

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
    }

    private void setNewPeriod() {
        reference1.child(mAuth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserModel userModel = snapshot.getValue(UserModel.class);
                SimpleDateFormat newformat = new SimpleDateFormat("dd-MM-yyyy");
                Calendar calendar = Calendar.getInstance();
                if (userModel != null) {
                    String lastperiod = userModel.getLastPeriod();
                    cyclelength = userModel.getcLength();
                    periodlength = userModel.getpLength();

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
                                lpd = cperioddate;
                            } else {
                                lpd = lperioddate;
                            }
                            Log.d(TAG, "Initial info loaded: lpd = " + lpd + ", periodLength = " + periodlength);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getInitialInfo(InitialInfoCallback callback) {
        FirebaseUser user = mAuth.getCurrentUser();
        SimpleDateFormat newformat = new SimpleDateFormat("dd-MM-yyyy");
        if (user != null) {
            reference1.child(mAuth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    UserModel userModel = snapshot.getValue(UserModel.class);
                    if (userModel != null) {
                        String lastPeriod = userModel.getLastPeriod();
                        periodlength = userModel.getpLength();
                        cyclelength = userModel.getcLength();
                        if (lastPeriod != null) {
                            try {
                                lpd = newformat.parse(lastPeriod);
                                callback.onInitialInfoLoaded(lpd, periodlength,cyclelength);
                            } catch (ParseException e) {
                                Log.e(TAG, "Error parsing date", e);
                            }
                        } else {
                            Log.e(TAG, "Last period date is null");
                        }
                    } else {
                        Log.e(TAG, "User model is null");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e(TAG, "Database error", error.toException());
                }
            });
        } else {
            Log.e(TAG, "User is not authenticated");
        }
    }

    public interface InitialInfoCallback {
        void onInitialInfoLoaded(Date lpd, int periodLength,int cyclelength);
    }
}
