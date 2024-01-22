package com.pavan.notespro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class loginActivity extends AppCompatActivity {

    EditText emailtext, passwordtext;
    Button submitbtn;
    ProgressBar progressBar;
    TextView signuptextbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailtext = findViewById(R.id.text_input_email);
        passwordtext = findViewById(R.id.text_input_password);
        submitbtn = findViewById(R.id.submitbtn);
        progressBar = findViewById(R.id.progressbar);
        signuptextbtn = findViewById(R.id.signup_text_btn);

        submitbtn.setOnClickListener(v -> loginUser());
        signuptextbtn.setOnClickListener(v ->
                startActivity(new Intent(loginActivity.this,createaccountActivity.class)));
    }

    void loginUser(){
        String email = emailtext.getText().toString();
        String password = passwordtext.getText().toString();


        boolean isvalidated = isvalidate(email,password);
        if(!isvalidated){
            return;
        }

        logintoFireBaseAccount(email,password);

    }



    boolean isvalidate(String email,String password){
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailtext.setError("Email is invalid");
            return false;
        }
        if(password.isEmpty() || password.length()<6){
            passwordtext.setError("password length is not valid");
            return false;
        }
        return true;
    }

    void changeInProgress(boolean isprogress){
        if(isprogress){
            submitbtn.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        }else{
            progressBar.setVisibility(View.GONE);
            submitbtn.setVisibility(View.VISIBLE);
        }
    }

   void logintoFireBaseAccount(String email, String password) {

       FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
       changeInProgress(true);
       firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
           @Override
           public void onComplete(@NonNull Task<AuthResult> task) {
               changeInProgress(false);
                if(task.isSuccessful()){
                    if(firebaseAuth.getCurrentUser().isEmailVerified()){
                        startActivity(new Intent(loginActivity.this,MainActivity.class));
                        finish();
                    }else{
                        Firebaseutils.showToast(loginActivity.this,"Email is not verified , Please verify email");
                    }
                }else{
                        Firebaseutils.showToast(loginActivity.this,task.getException().getLocalizedMessage());
                }
           }
       });


    }
}