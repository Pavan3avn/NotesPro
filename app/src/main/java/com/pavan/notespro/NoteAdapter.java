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
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull Noteviewholder holder, int position, @NonNull Notemodel model) {
        holder.Title.setText(model.title);
        holder.content.setText(model.content);
        holder.timestamp.setText(Firebaseutils.Timestamptostring(model.getTimestamp()));

        holder.itemView.setOnClickListener( v ->{
            Intent intent = new Intent(context,NotesActivity.class);
            intent.putExtra("title",model.title);
            intent.putExtra("content",model.getContent());
            String docId = this.getSnapshots().getSnapshot(position).getId();
            intent.putExtra("docId",docId);
            context.startActivity(intent);
        });


    }

    @NonNull
    @Override
    public Noteviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Noteviewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.noteitem,parent,false));
    }

    public class Noteviewholder extends RecyclerView.ViewHolder{

        TextView Title,content,timestamp;
        public Noteviewholder(@NonNull View itemView) {
            super(itemView);
            Title =  (TextView) itemView.findViewById(R.id.note_title);
            content = (TextView) itemView.findViewById(R.id.note_content);
            timestamp = itemView.findViewById(R.id.time_stamp);
        }

    };
}
