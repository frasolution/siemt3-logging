package com.siemt3.watchdog_server.cep;
/*++
Project Name:
watchdog_server

Author:
Maximilian Medlin (Meshstyles)

Description:
Permanent Engine Manager
Manager Singleton that "manages" our variables which are need to be accessed throughout the entire project

--*/
import com.espertech.esper.common.client.configuration.Configuration;
import com.espertech.esper.runtime.client.EPRuntime;
import com.espertech.esper.runtime.client.EPRuntimeProvider;
import com.siemt3.watchdog_server.cep.event.DemoLogEvent;
import com.siemt3.watchdog_server.cep.event.GudeEvent;
import com.siemt3.watchdog_server.cep.event.SSHLogEvent;

public class PEM {

    //uses singleton pattern for access of variables below
    private static PEM instance;

    // having config runtime and runtimeuri available for when needed
    public Configuration config;
    public String runtimeURI;
    public EPRuntime runtime;

    public PEM(){

        this.config = new Configuration();
        this.config.getCommon().addEventType(GudeEvent.class);
        this.config.getCommon().addEventType(DemoLogEvent.class);
        this.config.getCommon().addEventType(SSHLogEvent.class);

        this.runtimeURI = "globalRuntime";

        this.runtime = EPRuntimeProvider.getRuntime(this.runtimeURI, this.config);
    }

    public static PEM getInstance(){
        if (PEM.instance == null){
            PEM.instance = new PEM();
        }
        return instance;
    }

}
