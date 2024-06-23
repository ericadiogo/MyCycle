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
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText emailLogin,passwordLogin;
    private Button btnLogin, btnRegister, btnForgot;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailLogin = findViewById(R.id.emailLogin);
        passwordLogin = findViewById(R.id.passwordLogin);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        btnForgot = findViewById(R.id.btnForgot);

        mAuth = FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateLogin();
            }
        });

        btnRegister.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
            startActivity(intent);
            finish();
        });

        btnForgot.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this,RecoveryActivity.class);
            startActivity(intent);
            finish();
        });

    }
    private void validateLogin(){
        String email = emailLogin.getText().toString().trim();
        String password = passwordLogin.getText().toString().trim();

        if(!email.isEmpty()){
            if(!password.isEmpty()){
                loginFirebase(email, password);
            } else {
                Toast.makeText(this,"Wrong password.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this,"Wrong email.", Toast.LENGTH_SHORT).show();
        }
    }

    private void loginFirebase(String emailReg, String passReg) {
        mAuth.signInWithEmailAndPassword(
                emailReg,passReg
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this,"Something went wrong.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}