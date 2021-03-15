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
    private long arrival_time;

    public SSHBaseLogEvent(String log) {
        this.log = log;
        this.arrival_time = (long)(System.currentTimeMillis() / 1000);
    }

    public String getLog() {
        return this.log;
    }
    public long getArrival_time(){return this.arrival_time;}

    public void setLog(String log) {
        this.log = log;
    }

}
