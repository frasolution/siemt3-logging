package com.siemt3.watchdog_server.cep.listener.ssh.lib;

import com.google.gson.Gson;

public class SshCommonMethods {
    
    public static String getHostname(String log){
        String hostname;
        String[] a6;
        a6 = log.split(" ");
        hostname = a6[3];
        return hostname;
    }

    public static String toJson(Object o){
        Gson gson = new Gson();
        return gson.toJson(o);
    }

}
