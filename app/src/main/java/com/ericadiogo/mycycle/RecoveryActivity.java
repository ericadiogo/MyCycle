package com.ericadiogo.mycycle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RecoveryActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText emailRecover;
    private Button btnRecover,btnRecBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_recovery);

        emailRecover = findViewById(R.id.emailRecover);
        btnRecover = findViewById(R.id.btnRecover);
        btnRecBack = findViewById(R.id.btnRecBack);

        mAuth = FirebaseAuth.getInstance();
        btnRecover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateEmail();
            }
        });

        btnRecBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecoveryActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void validateEmail(){
        String email = emailRecover.getText().toString().trim();
        if(!email.isEmpty()){
            recoverFirebase(email);
        } else {
            Toast.makeText(this,"Please, provide your recovery email.", Toast.LENGTH_SHORT).show();
        }
    }

    private void recoverFirebase(String emailReg) {
        mAuth.sendPasswordResetEmail(
                emailReg).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(RecoveryActivity.this,"Email sent.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RecoveryActivity.this,"This email is not registered.", Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(RecoveryActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}