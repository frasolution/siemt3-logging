package com.siemt3.watchdog_server.cep.event.sshEvents;

import com.siemt3.watchdog_server.EventId;
import com.siemt3.watchdog_server.EventType;

public class SSHIpEvent {
    private String eventId;
    private String eventType;
    private long arrival_time;
    private String ip;
    private String hostname;
    private String username;

    public SSHIpEvent(long arrival_time, String ip, String hostname, String username) {
        this.eventId = EventId.SSH_Ip;
        this.eventType = EventType.SSH_Ip;
        this.arrival_time = arrival_time;
        this.ip = ip;
        this.hostname = hostname;
        this.username = username;
    }

    public SSHIpEvent() {
        this.eventId = EventId.SSH_Ip;
        this.eventType = EventType.SSH_Ip;
    }

    public long getArrival_time() {
        return arrival_time;
    }

    public void setArrival_time(long arrival_time) {
        this.arrival_time = arrival_time;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
