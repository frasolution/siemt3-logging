package com.siemt3.watchdog_server.model;

public class Alert {

    private String eventId;

    private String eventType;

    private String eventName;

    private long unix_time;
    private int priority;
    private String customData;

    public Alert() {
    }

    public Alert(String eventId, String eventType, String eventName,
                 long unix_time, int priority, String customData) {
        this.eventId = eventId;
        this.eventType = eventType;
        this.eventName = eventName;
        this.unix_time = unix_time;
        this.priority = priority;
        this.customData = customData;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public void setUnix_time(long unix_time) {
        this.unix_time = unix_time;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setCustomData(String customData) {
        this.customData = customData;
    }

    public String getEventId() {
        return eventId;
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
