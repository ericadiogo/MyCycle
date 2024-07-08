package com.ericadiogo.mycycle;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<MyViewHolder> {
    private Context context;
    private List<Notes> notesList;

    public NoteAdapter(Context context, List<Notes> notesList){
        this.context = context;
        this.notesList = notesList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.noteDate.setText(notesList.get(position).getNoteDay());
        holder.noteText.setText(notesList.get(position).getNoteText());
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    private String getDate() {
        DateFormat dateFormat = new SimpleDateFormat("EEE, MMM dd");
        Date date = new Date();
        return dateFormat.format(date);
    }
}

class MyViewHolder extends RecyclerView.ViewHolder{
    TextView noteDate, noteText;
    ImageView editNote, deleteNote;
    CardView cardNoteItem;
    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        noteDate = itemView.findViewById(R.id.noteDate);
        noteText = itemView.findViewById(R.id.noteText);
        editNote = itemView.findViewById(R.id.editNote);
        deleteNote = itemView.findViewById(R.id.deleteNote);
        cardNoteItem = itemView.findViewById(R.id.cardNoteItem);
    }
}
