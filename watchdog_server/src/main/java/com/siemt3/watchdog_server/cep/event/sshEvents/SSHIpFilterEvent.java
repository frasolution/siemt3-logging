package com.siemt3.watchdog_server.cep.event.sshEvents;

import com.siemt3.watchdog_server.EventId;
import com.siemt3.watchdog_server.EventType;

public class SSHIpFilterEvent {
    private long arrival_time;
    private String ip;
    private String log;
    private String username;

    public SSHIpFilterEvent(long arrival_time, String ip, String log, String username) {
        this.arrival_time = arrival_time;
        this.ip = ip;
        this.log = log;
        this.username = username;
    }

    public SSHIpFilterEvent() {
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

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
