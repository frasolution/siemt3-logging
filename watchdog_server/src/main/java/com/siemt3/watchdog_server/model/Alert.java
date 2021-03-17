package com.siemt3.watchdog_server.model;

public class Alert {
    private String eventType;
    private String eventName;
    private long unix_time;
    private int priority;
    private String customData;

    public Alert() {
    }

    public Alert(String eventType, String eventName,
                 long unix_time, int priority, String customData) {
        this.eventType = eventType;
        this.eventName = eventName;
        this.unix_time = unix_time;
        this.priority = priority;
        this.customData = customData;
    }

    public Alert setEventType(String eventType) {
        this.eventType = eventType;
        return this;
    }

    public Alert setEventName(String eventName) {
        this.eventName = eventName;
        return this;
    }

    public Alert setUnix_time(long unix_time) {
        this.unix_time = unix_time;
        return this;
    }

    public Alert setPriority(int priority) {
        this.priority = priority;
        return this;
    }

    public Alert setCustomData(String customData) {
        this.customData = customData;
        return this;
    }

    public String getEventType() {
        return eventType;
    }

    public String getEventName() {
        return eventName;
    }

    public long getUnix_time() {
        return unix_time;
    }

    public int getPriority() {
        return priority;
    }

    public String getCustomData() {
        return customData;
    }

}
