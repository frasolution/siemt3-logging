package com.siemt3.watchdog_server.cep.event.demoEvents;
/*++
Project Name:
watchdog_server

Author:
Maximilian Medlin (Meshstyles)

Description:
demo event Class for log event of type DemoLog
hint this does not need to implement serializable

--*/

//event object for raw demolog event
public class DemoLogEvent {

    private String sus;

    public String getSus() {
        return sus;
    }

    public void setSus(String sus) {
        this.sus = sus;
    }

    public DemoLogEvent(String log) {
        this.setSus(log);
    }

}
