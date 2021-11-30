package com.example.lastHomeWork.entity;

public class Manager {
    private int manager_id;
    private String manager_name;
    private String manager_password;
    private String manager_privilege;

    public int getManager_id() {
        return manager_id;
    }

    public void setManager_id(int manager_id) {
        this.manager_id = manager_id;
    }

    public String getManager_name() {
        return manager_name;
    }

    public void setManager_name(String manager_name) {
        this.manager_name = manager_name;
    }

    public String getManager_password() {
        return manager_password;
    }

    public void setManager_password(String manager_password) {
        this.manager_password = manager_password;
    }

    public String getManager_privilege() {
        return manager_privilege;
    }

    public void setManager_privilege(String manager_privilege) {
        this.manager_privilege = manager_privilege;
    }
}
