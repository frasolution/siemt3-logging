package com.siemt3.watchdog_server.cep.customObjects.ssh;

import java.util.ArrayList;

public class SshBasicPassword {
    String username;
    ArrayList<String> ip;

    public SshBasicPassword(String username, ArrayList<String> ip) {
        this.username = username;
        this.ip = ip;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ArrayList<String> getIp() {
        return ip;
    }

    public void setIp(ArrayList<String> ip) {
        this.ip = ip;
    }
}
