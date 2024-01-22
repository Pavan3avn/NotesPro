package com.pavan.notespro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.Query;
import com.pavan.notespro.models.Notemodel;


public class MainActivity extends AppCompatActivity {

    ImageButton menubtn;
    RecyclerView recyclerView;
    FloatingActionButton addnotebtn;

    NoteAdapter noteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        menubtn = findViewById(R.id.menu_btn);
        recyclerView = findViewById(R.id.Recyclerview);
        addnotebtn = findViewById(R.id.floatbtn);
        addnotebtn.setOnClickListener(v -> startActivity(new Intent(MainActivity.this,NotesActivity.class)));
        menubtn.setOnClickListener(v -> showMenu());
        setUprecyclerview();

    }
    void showMenu(){

    }
    void setUprecyclerview(){
        Query query = Firebaseutils.getCollectionreference().orderBy("timestamp", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Notemodel> options = new FirestoreRecyclerOptions.Builder<Notemodel>()
                .setQuery(query,Notemodel.class).build();
        noteAdapter = new NoteAdapter(options,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(noteAdapter);



    }
    @Override
    protected void onStart() {
        super.onStart();
        noteAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        noteAdapter.stopListening();
    }

    @Override
    protected void onResume() {
        super.onResume();
        noteAdapter.notifyDataSetChanged();
    }

}