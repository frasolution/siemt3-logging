package com.siemt3.watchdog_server.model;

public class Threshold {
    public String name;
    public int number;
    public String type;

    public Threshold(String name, String type, int number) {
        this.name = name;
        this.type = type;
        this.number = number;
    }
}
