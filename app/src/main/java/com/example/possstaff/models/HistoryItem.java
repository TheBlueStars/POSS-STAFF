package com.example.possstaff.models;

public class HistoryItem {
    public int id; public String zone; public int tableNo; public String detail; public int total; public String createdAt;
    public HistoryItem(int id,String z,int t,String d,int total,String time){
        this.id=id; this.zone=z; this.tableNo=t; this.detail=d; this.total=total; this.createdAt=time;
    }
}
