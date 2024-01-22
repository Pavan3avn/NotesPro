package com.pavan.notespro;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.pavan.notespro.models.Notemodel;

import org.w3c.dom.Text;

public class NoteAdapter extends FirestoreRecyclerAdapter<Notemodel,NoteAdapter.Noteviewholder> {

    Context context;


    public NoteAdapter(@NonNull FirestoreRecyclerOptions<Notemodel> options,Context context) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull Noteviewholder holder, int position, @NonNull Notemodel model) {
        holder.Title.setText(model.getTitle());
        holder.content.setText(model.getContent());
        holder.content.setText(Firebaseutils.Timestamptostring(model.getTimestamp()));

        holder.itemView.setOnClickListener((v)->{
            Intent intent = new Intent(context,NotesActivity.class);
            intent.putExtra("title",model.getTitle());
            intent.putExtra("content",model.getContent());
            String docId = this.getSnapshots().getSnapshot(position).getId();
            intent.putExtra("docId",docId);
            context.startActivity(intent);
        });


    }

    @NonNull
    @Override
    public Noteviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Noteviewholder(LayoutInflater.from(context).inflate(R.layout.noteitem,parent,false));
    }

    public class Noteviewholder extends RecyclerView.ViewHolder{

        TextView Title,content,timestamp;
        public Noteviewholder(@NonNull View v) {
            super(v);
            Title =  v.findViewById(R.id.note_title);
            content = v.findViewById(R.id.notes_description);
            timestamp = v.findViewById(R.id.time_stamp);
        }

    };
}
