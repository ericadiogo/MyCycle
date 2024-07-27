package com.ericadiogo.mycycle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class RegisterActivity extends AppCompatActivity {
    private EditText firstName,lastName,emailRegister,passRegister,passConfirm,periodLengthReg,cycleReg,weightReg;
    private Button btnRegister,lastPeriodButton;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference reference,reference2;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatePickerDialog datePickerDialog;
    private String lastPeriodDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        emailRegister = findViewById(R.id.emailRegister);
        passRegister = findViewById(R.id.passRegister);
        passConfirm = findViewById(R.id.passConfirm);
        periodLengthReg = findViewById(R.id.periodLengthReg);
        cycleReg = findViewById(R.id.cycleReg);
        weightReg = findViewById(R.id.weightReg);
        lastPeriodButton = findViewById(R.id.lastPeriodButton);
        btnRegister = findViewById(R.id.btnRegister);
        mAuth = FirebaseAuth.getInstance();

        lastPeriodButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Calendar calendar = Calendar.getInstance();
                 int year = calendar.get(Calendar.YEAR);
                 int month = calendar.get(Calendar.MONTH);
                 int day = calendar.get(Calendar.DAY_OF_MONTH);

                 datePickerDialog = new DatePickerDialog(RegisterActivity.this, new DatePickerDialog.OnDateSetListener() {
                     @Override
                     public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                         Date seldate = new Date(month+1+ "/" + day + "/" + year);
                         String fdate = new SimpleDateFormat("dd/MM/yyyy").format(seldate);
                         String ffdate = new SimpleDateFormat("dd-MM-yyy").format(seldate);
                         lastPeriodDate = ffdate;
                         lastPeriodButton.setText(fdate);
                     }
                 }, year, month, day);
                 datePickerDialog.show();
             }


        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    validateUser();
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void validateUser() throws ParseException {
        String fname = firstName.getText().toString().trim();
        String lname = lastName.getText().toString().trim();
        String email = emailRegister.getText().toString().trim();
        String password = passRegister.getText().toString().trim();
        String passConf = passConfirm.getText().toString().trim();
        int pLength = Integer.parseInt(periodLengthReg.getText().toString().trim());
        int cLength = Integer.parseInt(cycleReg.getText().toString().trim());
        int weight = Integer.parseInt(weightReg.getText().toString().trim());
        String lastDate = lastPeriodDate;
        String id = "a1";

        if(!fname.isEmpty()){
            if (!lname.isEmpty()){
                if(!email.isEmpty()){
                    emailRegister.requestFocus();
                    if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                        Toast.makeText(this,"Please, provide a valid email.", Toast.LENGTH_SHORT).show();
                    } else {
                        if(!password.isEmpty()){
                            if(!passConf.isEmpty()){
                                if(!password.equals(passConf)) {
                                    Toast.makeText(RegisterActivity.this,"Passwords don't match.",Toast.LENGTH_SHORT).show();
                                } else {
                                    createUserFirebase(id,fname,lname,email,password,pLength,cLength,weight,lastDate);
                                }
                            } else {
                                Toast.makeText(RegisterActivity.this,"Please, retype your password.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(this,"Please, create a password.", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(this,"Please, provide your email.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(RegisterActivity.this,"Please, type your last name.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(RegisterActivity.this, "Please, type your first name.", Toast.LENGTH_SHORT).show();
        }
    }

    private void createUserFirebase(String i, String fn, String ln, String emailReg, String passReg, int pl, int cl, int w, String lp) {
        mAuth.createUserWithEmailAndPassword(
                emailReg,passReg
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    database = FirebaseDatabase.getInstance();
                    reference = database.getReference("users");
                    UserModel user = new UserModel(mAuth.getUid(),fn,ln,emailReg,pl,cl,w,lp);
                    reference.child(user.getId()).setValue(user);

                    reference2 = database.getReference("dailyinfo");
                    DailyInfo dailyInfo = new DailyInfo(lp,true);
                    reference2.child(user.getId()).child(dailyInfo.getDate()).setValue(dailyInfo);

                    Toast.makeText(RegisterActivity.this,"You are registered!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);

                    intent.putExtra("id",user.getId());
                    intent.putExtra("firstname",fn);
                    intent.putExtra("lastname",ln);
                    intent.putExtra("email",emailReg);
                    intent.putExtra("periodlength",pl);
                    intent.putExtra("cyclelength",cl);
                    intent.putExtra("weight",w);
                    intent.putExtra("lastperiod",lp);
                    mAuth.signOut();
                    mAuth = null;
                    startActivity(intent);
                    finish();
                } else {
                    String error;
                    error = task.getException().getMessage();
                    Toast.makeText(RegisterActivity.this,"" + error, Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
















