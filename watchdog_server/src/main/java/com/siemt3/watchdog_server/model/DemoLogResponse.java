package com.siemt3.watchdog_server.model;

import java.io.Serializable;

public class DemoLogResponse implements Serializable {

    private final String demoLog;

    public DemoLogResponse(String demoLog) {
        this.demoLog = demoLog;
    }

    public String getDemoLog() {
        return demoLog;
    }
}
