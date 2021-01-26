package com.siemt3.watchdog_server.cep.event;

// event object for elevated demolog event "containing gude substring"
public class GudeEvent {

    public String gude;

    public GudeEvent(String gude) {
        this.gude = gude;
    }

    public String getGude() {
        return gude;
    }

    public void setGude(String gude) {
        this.gude = gude;
    }
}
