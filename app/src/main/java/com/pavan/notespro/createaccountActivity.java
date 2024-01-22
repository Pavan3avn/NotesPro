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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.sql.Struct;
import java.util.regex.Pattern;

public class createaccountActivity extends AppCompatActivity {

    EditText emailtext, passwordtext, confirmpasswordtext;
    Button submitbtn;
    ProgressBar progressBar;
    TextView logintextbtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createaccount);

        emailtext = findViewById(R.id.text_input_email);
        passwordtext = findViewById(R.id.text_input_password);
        confirmpasswordtext = findViewById(R.id.confirm_password);
        submitbtn = findViewById(R.id.submitbtn);
        progressBar = findViewById(R.id.progressbar);
        logintextbtn = findViewById(R.id.login_text_btn);

        submitbtn.setOnClickListener(v -> createaccount());
        logintextbtn.setOnClickListener(v ->
                startActivity(new Intent(createaccountActivity.this,loginActivity.class)));
    }

    void createaccount(){
        String email = emailtext.getText().toString();
        String password = passwordtext.getText().toString();
        String confirmpassword = confirmpasswordtext.getText().toString();

        boolean isvalidated = isvalidate(email,password,confirmpassword);
        if(!isvalidated){
            return;
        }

        createFireBaseAccount(email,password);

    }

    boolean isvalidate(String email,String password, String confirmpassword){
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailtext.setError("Email is invalid");
            return false;
        }
        if(password.isEmpty() || password.length()<6){
            passwordtext.setError("password length is not valid");
            return false;
        }
        if(!password.equals(confirmpassword)){
            confirmpasswordtext.setError("password is not equal");
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

    void createFireBaseAccount(String Email, String Password){
        changeInProgress(true);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(createaccountActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){//Account creation
                    changeInProgress(false);
                    Firebaseutils.showToast(createaccountActivity.this, "Successfully Account created,Check email to verify");
                    firebaseAuth.getCurrentUser().sendEmailVerification();// this method sends a vefication email to user
                    firebaseAuth.signOut();
                    finish();
                }else{//failure
                    Firebaseutils.showToast(createaccountActivity.this, task.getException().getLocalizedMessage());
                }
            }
        });

    }

}