package com.keke.hejia.bean;

public class RecycBeanDemo {
    public RecycBeanDemo(String name, int iconid, String role) {
        this.name = name;
        this.iconid = iconid;
        this.role = role;
    }

    private String name;

    public String getName() {
        return name;
    }

    public int getIconid() {
        return iconid;
    }

    public String getRole() {
        return role;
    }

    private int iconid;
    private String role;
}
