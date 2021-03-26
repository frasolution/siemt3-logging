package com.siemt3.watchdog_server.cep.event.sshEvents.elevated;

public class SshDictionaryElevatedEvent {

    private long arrival_time;
    private String username;


    public SshDictionaryElevatedEvent(long arrival_time, String username) {
        this.arrival_time = arrival_time;
        this.username = username;
    }

    //getters

    public long getArrival_time() {
        return arrival_time;
    }

    public String getUsername() {
        return username;
    }

    //setters
    public void setArrival_time(long arrival_time) {
        this.arrival_time = arrival_time;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
