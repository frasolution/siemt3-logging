package com.siemt3.watchdog_server;

public class EventName {
    // RULE KAT+EVENT_NUMBER
    // KAT SQL INJECT = 4 | EVENT_NUMBER 1;
    // => 41

    // SSH = KAT 3
    public static final String SSH_Dictionary = "SSH_Failed_Password";
    public static final String SSH_Root = "SSH_Root";
    public static final String SSH_Algorithm = "SSH_Algorithm";
    public static final String SSH_User = "SSH_User";
    public static final String SSH_Ip = "SSH_Ip";

    public static final String SSH_DictionaryE = "SSH_Failed_Password";
    public static final String SSH_RootE = "SSH_Root";
    public static final String SSH_UserE = "SSH_User";
    public static final String SSH_IpE = "SSH_Ip";

}