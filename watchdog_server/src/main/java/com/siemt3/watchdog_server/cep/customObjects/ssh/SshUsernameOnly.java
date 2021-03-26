package com.siemt3.watchdog_server.cep.customObjects.ssh;

public class SshUsernameOnly {
    String username;

    public SshUsernameOnly(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
