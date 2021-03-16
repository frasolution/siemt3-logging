package com.siemt3.watchdog_server.cep.listener.sshListeners.lib;

public class SshCommonMethods {
    public static String getHostname(String log){
        String hostname;
        String[] a5, a6;
        a5 = log.split(" sshd");
        a6 = log.split(" ");
        hostname = a6[3];
        return hostname;
    }

}
