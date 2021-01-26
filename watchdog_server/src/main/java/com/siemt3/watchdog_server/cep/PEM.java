package com.siemt3.watchdog_server.cep;

import com.espertech.esper.common.client.configuration.Configuration;
import com.siemt3.watchdog_server.cep.event.DemoLogEvent;
import com.siemt3.watchdog_server.cep.event.GudeEvent;

public class PEM {
    private static PEM instance;
    public Configuration config;
    public String runtimeURI;

    public PEM(){

        this.config = new Configuration();
        this.config.getCommon().addEventType(GudeEvent.class);
        this.config.getCommon().addEventType(DemoLogEvent.class);

        this.runtimeURI = "globalRuntime";

    }

    public static PEM getInstance(){
        if (PEM.instance == null){
            PEM.instance = new PEM();
        }
        return instance;
    }

}
