package com.example.possstaff.activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.possstaff.utils.SessionManager;

public class SplashActivity extends AppCompatActivity {
    @Override protected void onCreate(Bundle b){
        super.onCreate(b);
        SessionManager sm=new SessionManager(this);
        if(sm.isLoggedIn()){
            startActivity(new Intent(this, ZoneActivity.class));
        } else {
            startActivity(new Intent(this, LoginActivity.class));
        }
        finish();
    }
}
