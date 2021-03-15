package com.siemt3.watchdog_server.cep.event.sshEvents;

import com.siemt3.watchdog_server.EventId;
import com.siemt3.watchdog_server.EventType;

public class SSHRoot {
    private String eventId;
    private String eventType;
    private long arrival_time;
    private String ip;

    public SSHRoot(long arrival_time, String ip) {
        this.eventId = EventId.SSH_Root;
        this.eventType  = EventType.SSH_Root;
        this.arrival_time = arrival_time;
        this.ip = ip;
    }

    public SSHRoot(){
        this.eventId = EventId.SSH_Root;
        this.eventType  = EventType.SSH_Root;
    }

    public long getArrival_time() {
        return arrival_time;
    }

    public String getIp() {
        return ip;
    }

    public void setArrival_time(long arrival_time) {
        this.arrival_time = arrival_time;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }



}
