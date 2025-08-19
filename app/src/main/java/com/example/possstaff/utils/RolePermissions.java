package com.example.possstaff.utils;

import com.example.possstaff.models.Role;

public class RolePermissions {
    public static boolean canOrder(Role r){
        return r==Role.EMPLOYEE || r==Role.SUPERVISOR || r==Role.TEAM_LEAD || r==Role.MANAGER;
    }
    public static boolean canCancelItem(Role r){
        return r==Role.SUPERVISOR || r==Role.TEAM_LEAD || r==Role.MANAGER;
    }
    public static boolean canTransferOrCancelTable(Role r){
        return r==Role.SUPERVISOR || r==Role.TEAM_LEAD || r==Role.MANAGER;
    }
    public static boolean isManager(Role r){
        return r==Role.MANAGER;
    }
}
