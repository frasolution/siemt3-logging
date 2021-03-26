package com.siemt3.watchdog_server.cep.event.sshEvents.elevated;

import java.util.ArrayList;

public class SshDictionaryElevatedEvent {

    private long arrival_time;
    private String username;
    private ArrayList<String> ip;

    public SshDictionaryElevatedEvent(long arrival_time, String username, ArrayList<String> ip) {
        this.arrival_time = arrival_time;
        this.username = username;
        this.ip = ip;
    }

    public long getArrival_time() {
        return arrival_time;
    }

    public void setArrival_time(long arrival_time) {
        this.arrival_time = arrival_time;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ArrayList<String> getIp() {
        return ip;
    }

    public void setIp(ArrayList<String> ip) {
        this.ip = ip;
    }
}
