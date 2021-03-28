package com.siemt3.watchdog_server.cep.customObjects.ssh;

public class SshBasicAuth {
    String username;
    String algo_fingerprint;
    String ip;

    public SshBasicAuth(String username, String algo_fingerprint, String ip) {
        this.username = username;
        this.algo_fingerprint = algo_fingerprint;
        this.ip = ip;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAlgo_fingerprint() {
        return algo_fingerprint;
    }

    public void setAlgo_fingerprint(String algo_fingerprint) {
        this.algo_fingerprint = algo_fingerprint;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
