package com.siemt3.watchdog_server.cep.event.sshEvents;

import com.siemt3.watchdog_server.EventId;
import com.siemt3.watchdog_server.EventType;

public class SSHAlgorithmEvent {
    private long arrival_time;
    private String username;
    private String algo;
    private String fingerprint;
    private String ip;

    public SSHAlgorithmEvent(long arrival_time, String username, String algo, String fingerprint, String ip) {
        this.arrival_time = arrival_time;
        this.username = username;
        this.algo = algo;
        this.fingerprint = fingerprint;
        this.ip = ip;
    }

    public SSHAlgorithmEvent() {
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

    public String getAlgo() {
        return algo;
    }

    public void setAlgo(String algo) {
        this.algo = algo;
    }

    public String getFingerprint() {
        return fingerprint;
    }

    public void setFingerprint(String fingerprint) {
        this.fingerprint = fingerprint;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
