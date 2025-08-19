package com.example.possstaff.models;
public class User {
    public String username;
    public String password;
    public Role role;
    public String zone; // "A","B","C" hoặc "" với QLO/GSO

    public User(String u, String p, Role r, String zone){
        this.username=u; this.password=p; this.role=r; this.zone=zone;
    }
}
