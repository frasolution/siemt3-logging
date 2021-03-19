package com.siemt3.watchdog_server.cep.customObjects.ssh;

public class SshBasicIp {
    String hostname;
    String username;
    String ip;

    public SshBasicIp(String hostname, String username, String ip) {
        this.hostname = hostname;
        this.username = username;
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

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
