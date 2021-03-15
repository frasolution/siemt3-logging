package com.siemt3.watchdog_server;

public class EventType {
    // RULE KAT+EVENT_NUMBER
    // KAT SQL INJECT = 4 | EVENT_NUMBER 1;
    // => 41

    // SSH = KAT 3
    public static final String SSH_Dictionary = "SSH_Dictionary";
    public static final String SSH_Root = "SSH_Root";
    public static final String SSH_Algorithm = "SSH_Algorithm";
    public static final String SSH_User = "SSH_User";
    public static final String SSH_Ip = "SSH_Ip";
}
