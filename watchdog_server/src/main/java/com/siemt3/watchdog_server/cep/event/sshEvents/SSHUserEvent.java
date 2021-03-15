package com.siemt3.watchdog_server.cep.event.sshEvents;

import com.siemt3.watchdog_server.EventId;
import com.siemt3.watchdog_server.EventType;

public class SSHUserEvent {
    private String eventId;
    private String eventType;
    private long arrival_time;
    private String username;
    private String ip;

    public SSHUserEvent(long arrival_time, String username, String ip) {
        this.eventId = EventId.SSH_User;
        this.eventType = EventType.SSH_User;
        this.arrival_time = arrival_time;
        this.username = username;
        this.ip = ip;
    }

    public SSHUserEvent() {
        this.eventId = EventId.SSH_User;
        this.eventType = EventType.SSH_User;
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

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
