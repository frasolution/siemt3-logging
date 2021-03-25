package com.siemt3.watchdog_server.model;

import java.io.Serializable;

/*++
Project Name:
watchdog_server

Author:
Maximilian Medlin (Meshstyles)
Robert Uwe Litschel

 --*/

public class Apache2LogRequest implements Serializable{

    private String log;

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    //need default constructor for JSON Parsing
    public Apache2LogRequest() { }

    public Apache2LogRequest(String log) {
        this.setLog(log);
    }

}







