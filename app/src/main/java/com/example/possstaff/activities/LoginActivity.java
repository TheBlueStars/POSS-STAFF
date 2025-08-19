package com.example.possstaff.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.possstaff.R;
import com.example.possstaff.models.Role;
import com.example.possstaff.models.User;

public class LoginActivity extends AppCompatActivity {

    private EditText edtU, edtP;
    @Override protected void onCreate(Bundle b){
        super.onCreate(b);
        setContentView(R.layout.activity_login);
        edtU=findViewById(R.id.edtUsername);
        edtP=findViewById(R.id.edtPassword);
        Button btn = findViewById(R.id.btnLogin);

        btn.setOnClickListener(v->{
            String u = edtU.getText().toString().trim().toUpperCase();
            String p = edtP.getText().toString().trim();
            User user = auth(u, p);
            if(user==null){ Toast.makeText(this,"Sai tài khoản/mật khẩu",Toast.LENGTH_SHORT).show(); return; }
            Intent i = new Intent(this, ZoneActivity.class);
            i.putExtra("username", user.username);
            i.putExtra("role", user.role.name());
            i.putExtra("zone", user.zone); // "" nếu là QLO/GSO
            startActivity(i);
        });
    }

    private User auth(String u,String p){
        // Quản lý
        if(u.equals("QLO") && p.equals("QL123")) return new User(u,p, Role.MANAGER,"");
        // Giám sát
        if(u.equals("GSO") && p.equals("GS123")) return new User(u,p, Role.SUPERVISOR,"");
        // Nhân viên theo khu
        if(u.equals("NVA") && p.equals("111")) return new User(u,p, Role.EMPLOYEE,"A");
        if(u.equals("NVB") && p.equals("222")) return new User(u,p, Role.EMPLOYEE,"B");
        if(u.equals("NVC") && p.equals("333")) return new User(u,p, Role.EMPLOYEE,"C");
        // Tổ trưởng (ví dụ TT? dùng chung GS123)
        if(u.equals("TTA") && p.equals("TT123")) return new User(u,p, Role.TEAM_LEAD,"A");
        if(u.equals("TTB") && p.equals("TT123")) return new User(u,p, Role.TEAM_LEAD,"B");
        if(u.equals("TTC") && p.equals("TT123")) return new User(u,p, Role.TEAM_LEAD,"C");
        return null;
    }
}
