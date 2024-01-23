package com.pavan.notespro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.pavan.notespro.models.Notemodel;

import org.w3c.dom.Text;

public class NotesActivity extends AppCompatActivity {

    ImageButton backbtn , savebtn;
    EditText Titletext,descriptiontext;

    TextView pagetitle;

    String title,content,docid;

    TextView deletenote;


    boolean isEditMode = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        backbtn = findViewById(R.id.back_btn);
        savebtn = findViewById(R.id.save_btn);
        Titletext = findViewById(R.id.notes_title);
        descriptiontext = findViewById(R.id.notes_description);
        pagetitle = findViewById(R.id.page_title);
        deletenote = findViewById(R.id.delete_note);

        title =  getIntent().getStringExtra("title");
        content = getIntent().getStringExtra("content");
        docid = getIntent().getStringExtra("docId");

        if(docid != null && !docid.isEmpty() ) {
            isEditMode = true;
        }
        Titletext.setText(title);
        descriptiontext.setText(content);
        if(isEditMode){
            pagetitle.setText("Edit your Note");
            deletenote.setVisibility(View.VISIBLE);
        }

        backbtn.setOnClickListener(v -> startActivity(new Intent(NotesActivity.this,MainActivity.class)));
        savebtn.setOnClickListener(v -> saveNote());
        deletenote.setOnClickListener(v -> deletenotefromFirebase());

    }

    void saveNote(){
        String title = Titletext.getText().toString();
        String description = descriptiontext.getText().toString();
        if(title == null || title.isEmpty()){
            Titletext.setError("Title is required");
            return;
        }
        Notemodel note = new Notemodel();
        note.setTitle(title);
        note.setContent(description);
        note.setTimestamp(Timestamp.now());

        saveNoteToFirebase(note);


    }

    void saveNoteToFirebase(Notemodel note) {
        DocumentReference documentReference;
        if(isEditMode){
            documentReference = Firebaseutils.getCollectionreference().document(docid);
        }else{
            documentReference = Firebaseutils.getCollectionreference().document();
        }
        documentReference.set(note).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Firebaseutils.showToast(NotesActivity.this,"Note Added Successfuly");
                    finish();
                }else{
                    Firebaseutils.showToast(NotesActivity.this,"Failure while adding Notes");
                }
            }
        });
    }

    void deletenotefromFirebase(){
        DocumentReference documentReference;
            documentReference = Firebaseutils.getCollectionreference().document(docid);
        documentReference.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Firebaseutils.showToast(NotesActivity.this,"Note Added Successfuly");
                    finish();
                }else{
                    Firebaseutils.showToast(NotesActivity.this,"Failure while adding Notes");
                }
            }
        });
    }
}
