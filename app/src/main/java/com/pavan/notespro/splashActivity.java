package com.pavan.notespro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class splashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user == null){
                    startActivity(new Intent(splashActivity.this,loginActivity.class));
                }else{
                    startActivity(new Intent(splashActivity.this,MainActivity.class));
                }
                finish();
            }
        },2000);
    }
}