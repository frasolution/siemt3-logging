package com.siemt3.watchdog_server.model;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.persistence.PreUpdate;

@Entity
@Table(name = "alerts")
public class Alert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String eventId;

    @NotNull
    private String eventType;

    @NotNull
    private String eventName;

    private LocalDateTime date;
    private String priority;
    private String customData;

    public Alert() {
    }

    public void setId(Integer id) {
        this.id = id;
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

    @PrePersist
    public void setCreationDateTime() {
        this.date = LocalDateTime.now();
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public void setCustomData(String customData) {
        this.customData = customData;
    }

    public Integer getId() {
        return id;
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


    public LocalDateTime getDate() {
        return date;
    }

    public String getPriority() {
        return priority;
    }

    public String getCustomData() {
        return customData;
    }

}
