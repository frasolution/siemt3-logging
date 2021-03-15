package com.siemt3.watchdog_server.cep.event.sshEvents;
/*++
Project Name:
watchdog_server

Author:
Maximilian Medlin (Meshstyles)

Description:
event Class for log event of type SSHLog
hint this does not need to implement serializable

--*/

//event object for raw SSHLog event
public class SSHBaseLogEvent {

    private String log;

    public SSHBaseLogEvent(String log) {
        this.log = log;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

}
