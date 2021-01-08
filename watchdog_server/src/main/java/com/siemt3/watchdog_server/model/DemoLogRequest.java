package com.siemt3.watchdog_server.model;

import java.io.Serializable;

public class DemoLogRequest implements Serializable {

    private String sus;

    public String getSus() {
        return sus;
    }

    public void setSus(String sus) {
        this.sus = sus;
    }

    //need default constructor for JSON Parsing
    public DemoLogRequest() { }

    public DemoLogRequest(String log) {
        this.setSus(log);
    }
}
