package com.siemt3.watchdog_server.cep.customObjects.ssh;

public class SshBasicUser {
    String username;
    String ip;

    public SshBasicUser(String username, String ip) {
        this.username = username;
        this.ip = ip;
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
