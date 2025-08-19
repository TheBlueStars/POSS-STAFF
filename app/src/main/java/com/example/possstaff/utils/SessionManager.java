package com.example.possstaff.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private static final String PREF_NAME = "POSS_SESSION";
    private static final String KEY_USER = "user";
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    public SessionManager(Context ctx){
        prefs = ctx.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    public void login(String username){ editor.putString(KEY_USER, username); editor.apply(); }
    public void logout(){ editor.clear().apply(); }
    public String getUser(){ return prefs.getString(KEY_USER, null); }
    public boolean isLoggedIn(){ return getUser()!=null; }
}
