package com.siemt3.watchdog_server.model;

/*++
Project Name:
watchdog_server

Author:
Maximilian Medlin (Meshstyles)

Description:
model for a http request of type sshlog

--*/
import java.io.Serializable;

public class SSHLogRequest implements Serializable {

    private String log;

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    //need default constructor for JSON Parsing
    public SSHLogRequest() { }

    public SSHLogRequest(String log) {
        this.setLog(log);
    }
}
