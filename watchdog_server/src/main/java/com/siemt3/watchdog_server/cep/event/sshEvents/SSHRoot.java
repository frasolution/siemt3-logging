package com.siemt3.watchdog_server.cep.event.sshEvents;

public class SSHRoot {
    private long arrival_time;
    private String ip;

    public SSHRoot(long arrival_time, String ip) {
        this.arrival_time = arrival_time;
        this.ip = ip;
    }

    public SSHRoot(){
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
