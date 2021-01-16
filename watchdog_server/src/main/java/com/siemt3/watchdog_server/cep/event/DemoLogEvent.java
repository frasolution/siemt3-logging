package com.siemt3.watchdog_server.cep.event;

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
