package com.ericadiogo.mycycle;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SettingsActivity extends AppCompatActivity {
    private LinearLayout settingsBack, aboutProject, aboutMe;
    private TextView userFnameSet,userLnameSet,useremailSet,txtPeriod,txtCycleLength;
    private DatabaseReference reference;
    private String loggedUserId;
    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;
    private Button btnlogOut, btnDeleteProfile;
    private ImageView fNamedEdit,lNameEdit,emailEditSet,passwordEdit,pLengthEdit,cLengthEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_settings);

        settingsBack = findViewById(R.id.settingsBack);
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        loggedUserId = mAuth.getUid();
        reference = FirebaseDatabase.getInstance().getReference("users");
        userFnameSet = findViewById(R.id.userFnameSet);
        userLnameSet =  findViewById(R.id.userLnameSet);
        useremailSet = findViewById(R.id.userEmailSet);
        btnlogOut = findViewById(R.id.btnlogOut);
        btnDeleteProfile = findViewById(R.id.btnDeleteProfile);
        txtPeriod = findViewById(R.id.txtPeriod);
        txtCycleLength = findViewById(R.id.txtCycleLength);
        aboutProject = findViewById(R.id.aboutProject);
        aboutMe = findViewById(R.id.aboutMe);
        fNamedEdit = findViewById(R.id.fNamedEdit);
        lNameEdit = findViewById(R.id.lNameEdit);
        emailEditSet = findViewById(R.id.emailEditSet);
        passwordEdit = findViewById(R.id.passwordEdit);
        pLengthEdit = findViewById(R.id.pLengthEdit);
        cLengthEdit = findViewById(R.id.cLengthEdit);

        settingsBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingsActivity.this,HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        showInfo();

        fNamedEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogFname();
            }
        });

        lNameEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogLname();
            }
        });

        emailEditSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogEmail();
            }
        });

        passwordEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogPass();
            }
        });

        pLengthEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogPeriod();
            }
        });

        cLengthEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogCycle();
            }
        });

        btnDeleteProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogDelete();
            }
        });

        btnlogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Intent intent = new Intent(SettingsActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        aboutProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
                View view1 = getLayoutInflater().inflate(R.layout.about_project,null);
                Button btnOkProject;

                btnOkProject = view1.findViewById(R.id.btnOkProject);
                builder.setView(view1);

                AlertDialog dialog = builder.create();
                dialog.setCancelable(true);
                dialog.show();

                btnOkProject.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });

        aboutMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
                View view1 = getLayoutInflater().inflate(R.layout.about_me,null);
                Button btnOkMe;

                btnOkMe = view1.findViewById(R.id.btnOkMe);
                builder.setView(view1);

                AlertDialog dialog = builder.create();
                dialog.setCancelable(true);
                dialog.show();

                btnOkMe.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
    }

    private void showInfo() {
        reference.child(loggedUserId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserModel userModel = snapshot.getValue(UserModel.class);
                if(userModel != null){
                    String fname = userModel.getFirstName();
                    String lname = userModel.getLastName();
                    String email = userModel.getEmail();
                    int plength = userModel.getpLength();
                    int clength = userModel.getcLength();

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
    }

    private void showDialogFname(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View fname_layout = getLayoutInflater().inflate(R.layout.fname_dialog, null);
        final EditText edtChangeFname = fname_layout.findViewById(R.id.edtChangeFname);
        Button btnSave = fname_layout.findViewById(R.id.btnSave3);
        Button btnCancel = fname_layout.findViewById(R.id.btnCancel3);

        builder.setView(fname_layout);
        AlertDialog dialog = builder.create();
        dialog.setCancelable(true);
        dialog.show();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference.child(loggedUserId).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        UserModel userModel = snapshot.getValue(UserModel.class);
                        if(userModel != null){
                            String fname = userModel.getFirstName();
                            String fnamenew = edtChangeFname.getText().toString();
                            if(!fname.equals(fnamenew)){
                                reference.child(loggedUserId).child("firstName").setValue(fnamenew);
                                userFnameSet.setText(fnamenew);
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

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

    private void showDialogLname(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View lname_layout = getLayoutInflater().inflate(R.layout.lname_dialog, null);
        final EditText edtChangeLname = lname_layout.findViewById(R.id.edtChangeLname);
        Button btnSave = lname_layout.findViewById(R.id.btnSave4);
        Button btnCancel = lname_layout.findViewById(R.id.btnCancel4);

        builder.setView(lname_layout);
        AlertDialog dialog = builder.create();
        dialog.setCancelable(true);
        dialog.show();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference.child(loggedUserId).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        UserModel userModel = snapshot.getValue(UserModel.class);
                        if(userModel != null){
                            String lname = userModel.getFirstName();
                            String lnamenew = edtChangeLname.getText().toString();
                            if(!lname.equals(lnamenew)){
                                reference.child(loggedUserId).child("lastName").setValue(lnamenew);
                                userLnameSet.setText(lnamenew);
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

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

    private void showDialogEmail(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View email_layout = getLayoutInflater().inflate(R.layout.email_dialog, null);
        final EditText edtChangeEmail = email_layout.findViewById(R.id.edtChangeEmail);
        Button btnSave = email_layout.findViewById(R.id.btnSave5);
        Button btnCancel = email_layout.findViewById(R.id.btnCancel5);

        builder.setView(email_layout);
        AlertDialog dialog = builder.create();
        dialog.setCancelable(true);
        dialog.show();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference.child(loggedUserId).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        UserModel userModel = snapshot.getValue(UserModel.class);
                        if(userModel != null){
                            String email = userModel.getEmail();
                            String emailnew = edtChangeEmail.getText().toString();
                            if(!email.equals(emailnew)){
                                reference.child(loggedUserId).child("email").setValue(emailnew);
                                useremailSet.setText(emailnew);
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

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

    private void showDialogPass(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View password_layout = getLayoutInflater().inflate(R.layout.password_dialog, null);
        final EditText edtChangePass = password_layout.findViewById(R.id.edtChangePass);
        Button btnSave = password_layout.findViewById(R.id.btnSave6);
        Button btnCancel = password_layout.findViewById(R.id.btnCancel6);

        builder.setView(password_layout);
        AlertDialog dialog = builder.create();
        dialog.setCancelable(true);
        dialog.show();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference.child(loggedUserId).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        UserModel userModel = snapshot.getValue(UserModel.class);
                        if(userModel != null){
                            String pass = userModel.getPassword();
                            String passnew = edtChangePass.getText().toString();
                            if(!pass.equals(passnew)){
                                reference.child(loggedUserId).child("password").setValue(passnew);
                                firebaseUser.updatePassword(passnew);
                            }
                        }
                        Toast.makeText(SettingsActivity.this,"Your password has been updated.",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                dialog.dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

    private void showDialogCycle(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View c_layout = getLayoutInflater().inflate(R.layout.clength_dialog, null);
        final EditText edtChangeCycle = c_layout.findViewById(R.id.edtChangeCycle);
        Button btnSave = c_layout.findViewById(R.id.btnSave2);
        Button btnCancel = c_layout.findViewById(R.id.btnCancel2);

        builder.setView(c_layout);
        AlertDialog dialog = builder.create();
        dialog.setCancelable(true);
        dialog.show();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference.child(loggedUserId).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        UserModel userModel = snapshot.getValue(UserModel.class);
                        if(userModel != null){
                            int clength = userModel.getcLength();
                            int clengthnew = Integer.valueOf(edtChangeCycle.getText().toString());
                            if(clength != clengthnew){
                                reference.child(loggedUserId).child("cLength").setValue(clengthnew);
                                txtCycleLength.setText("Cycle length: " + clengthnew + " days");
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

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

    private void showDialogPeriod(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View p_layout = getLayoutInflater().inflate(R.layout.plength_dialog, null);
        final EditText edtChangePeriod = p_layout.findViewById(R.id.edtChangePeriod);
        Button btnSave = p_layout.findViewById(R.id.btnSave);
        Button btnCancel = p_layout.findViewById(R.id.btnCancel);

        builder.setView(p_layout);
        AlertDialog dialog = builder.create();
        dialog.setCancelable(true);
        dialog.show();

        btnSave.setOnClickListener(new View.OnClickListener() {
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
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

    private void showDialogDelete(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View delete_layout = getLayoutInflater().inflate(R.layout.delete_dialog, null);
        Button btnYes = delete_layout.findViewById(R.id.btnYes);
        Button btnNo = delete_layout.findViewById(R.id.btnNo);

        builder.setView(delete_layout);
        AlertDialog dialog = builder.create();
        dialog.setCancelable(true);
        dialog.show();

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference.child(loggedUserId).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        UserModel userModel = snapshot.getValue(UserModel.class);
                        if(userModel != null){
                            snapshot.child(loggedUserId).getRef().removeValue();
                            Intent intent = new Intent(SettingsActivity.this,LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                dialog.dismiss();
            }
        });

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }


}