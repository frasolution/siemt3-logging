package com.siemt3.watchdog_server.model;

public class Threshold {
    public String name;
    public int count;
    public int time;

    public Threshold(String name, int count, int time) {
        this.name = name;
        this.count = count;
        this.time = time;
    }
}
