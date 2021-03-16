package com.siemt3.watchdog_server.cep.customObjects.ssh;

public class SshBasicRoot {
    String ip;

    public SshBasicRoot(String ip) {
        this.ip = ip;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
