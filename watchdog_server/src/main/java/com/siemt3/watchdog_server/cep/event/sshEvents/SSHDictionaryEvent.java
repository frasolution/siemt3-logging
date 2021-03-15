package com.siemt3.watchdog_server.cep.event.sshEvents;

import com.siemt3.watchdog_server.EventId;
import com.siemt3.watchdog_server.EventType;

public class SSHDictionaryEvent {
    private String eventId;
    private String eventType;
    private long arrival_time;
    private String username;
    private String ip;

    public SSHDictionaryEvent(long arrival_time,String username, String ip) {
        this.eventId = EventId.SSH_Dictionary;
        this.eventType  = EventType.SSH_Dictionary;
        this.arrival_time = arrival_time;
        this.username = username;
        this.ip = ip;
    }

    public SSHDictionaryEvent() {
        this.eventId = EventId.SSH_Dictionary;
        this.eventType  = EventType.SSH_Dictionary;
    }

    //getters
    public String getEventId() {
        return eventId;
    }

    public String getEventType() {
        return eventType;
    }

    public long getArrival_time() {
        return arrival_time;
    }

    public String getUsername() {
        return username;
    }

    public String getIp() {
        return ip;
    }

    //setters
    public void setArrival_time(long arrival_time) {
        this.arrival_time = arrival_time;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

}
