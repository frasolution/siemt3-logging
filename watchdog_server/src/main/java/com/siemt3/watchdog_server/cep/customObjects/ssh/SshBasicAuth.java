package com.siemt3.watchdog_server.cep.customObjects.ssh;

public class SshBasicAuth {
    String username;
    String algorithm;
    String fingerprint;
    String ip;

    public SshBasicAuth(String username, String algorithm, String fingerprint, String ip) {
        this.username = username;
        this.algorithm = algorithm;
        this.fingerprint = fingerprint;
        this.ip = ip;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
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
