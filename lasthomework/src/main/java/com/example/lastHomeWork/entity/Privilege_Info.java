package com.example.lastHomeWork.entity;

public class Privilege_Info {
    private int privilege_id;
    private String privilege_name;
    private String privilege_request;
    private int status;

    public int getPrivilege_id() {
        return privilege_id;
    }

    public void setPrivilege_id(int privilege_id) {
        this.privilege_id = privilege_id;
    }

    public String getPrivilege_name() {
        return privilege_name;
    }

    public void setPrivilege_name(String privilege_name) {
        this.privilege_name = privilege_name;
    }

    public String getPrivilege_request() {
        return privilege_request;
    }

    public void setPrivilege_request(String privilege_request) {
        this.privilege_request = privilege_request;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
