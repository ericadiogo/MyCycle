package com.ericadiogo.mycycle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class NotesActivity extends AppCompatActivity {
    private LinearLayout notesBack;
    private ImageView addNote;
    private DatabaseReference reference;
    private String loggedUserId;
    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;
    private RecyclerView recViewNotes;
    //private List<Notes> notesList;
    //private NoteAdapter adapter;
    //private Notes note;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_notes);

        notesBack = findViewById(R.id.notesBack);
        addNote = findViewById(R.id.addNote);
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        loggedUserId = mAuth.getUid();
        reference = FirebaseDatabase.getInstance().getReference("users");
        recViewNotes = findViewById(R.id.recViewNotes);

        notesBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NotesActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogAddNote();
            }
        });
    }

    private void showDialogAddNote(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View addnote_layout = getLayoutInflater().inflate(R.layout.newnote_dialog, null);
        final EditText edtChangeFname = addnote_layout.findViewById(R.id.edtNewNote);
        Button btnSave = addnote_layout.findViewById(R.id.btnSave7);
        Button btnCancel = addnote_layout.findViewById(R.id.btnCancel7);

        builder.setView(addnote_layout);
        AlertDialog dialog = builder.create();
        dialog.setCancelable(true);
        dialog.show();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }
}