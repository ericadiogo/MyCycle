package com.ericadiogo.mycycle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    private EditText emailRegister,passRegister,passConfirm;
    private Button btnRegister, btnLoginRegister;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        emailRegister = findViewById(R.id.emailRegister);
        passRegister = findViewById(R.id.passRegister);
        passConfirm = findViewById(R.id.passConfirm);

        btnRegister = findViewById(R.id.btnRegister);
        btnLoginRegister = findViewById(R.id.btnLoginRegister);

        mAuth = FirebaseAuth.getInstance();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateUser();
            }
        });
        btnLoginRegister.setOnClickListener(view -> {
            Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void validateUser(){
        String email = emailRegister.getText().toString().trim();
        String password = passRegister.getText().toString().trim();
        String passConf = passConfirm.getText().toString().trim();

        if(!email.isEmpty()){
            if(!password.isEmpty()){
                if(!passConf.isEmpty()){
                    if(password.equals(passConf)) {
                        Toast.makeText(RegisterActivity.this,"Passwords match!",Toast.LENGTH_SHORT).show();
                        createUserFirebase(email, password);
                    } else {
                        Toast.makeText(RegisterActivity.this,"Passwords don't match.",Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(RegisterActivity.this,"Please, retype your password.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this,"Please, create a password.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this,"Please, provide your email.", Toast.LENGTH_SHORT).show();
        }
    }

    private void createUserFirebase(String emailReg, String passReg) {
        mAuth.createUserWithEmailAndPassword(
          emailReg,passReg
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(RegisterActivity.this,"You are registered!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RegisterActivity.this,"Something went wrong.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}