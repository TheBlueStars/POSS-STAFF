package com.example.possstaff.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.possstaff.R;
import com.example.possstaff.models.Role;

public class ZoneActivity extends AppCompatActivity {
    String username, zone; Role role;

    @Override protected void onCreate(Bundle b){
        super.onCreate(b);
        setContentView(R.layout.activity_zone);

        username=getIntent().getStringExtra("username");
        zone=getIntent().getStringExtra("zone");
        role= Role.valueOf(getIntent().getStringExtra("role"));

        TextView tv=findViewById(R.id.tvWelcome);
        tv.setText("Xin chào " + username + " • Vai trò: " + role.name());

        Button a=findViewById(R.id.btnZoneA), bB=findViewById(R.id.btnZoneB), c=findViewById(R.id.btnZoneC);

        a.setOnClickListener(v->openZone("A",13));
        bB.setOnClickListener(v->openZone("B",15));
        c.setOnClickListener(v->openZone("C",16));
    }

    private void openZone(String z, int tables){
        // nhân viên bị giới hạn khu
        if(role==Role.EMPLOYEE || role==Role.TEAM_LEAD){
            if(!z.equals(zone)){
                Toast.makeText(this,"Bạn chỉ được vào khu " + zone, Toast.LENGTH_SHORT).show(); return;
            }
        }
        Intent i = new Intent(this, TableActivity.class);
        i.putExtra("role", role.name());
        i.putExtra("zone", z);
        i.putExtra("tables", tables);
        startActivity(i);
    }
}
