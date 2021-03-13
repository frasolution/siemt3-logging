package com.siemt3.watchdog_server.controller;


/*++
Project Name:
watchdog_server

Author:
Maximilian Medlin (Meshstyles)

Description:
http rest controller as sample code
you can use this as a reference when creating your endpoint

--*/

import com.espertech.esper.runtime.client.EPRuntime;
import com.siemt3.watchdog_server.cep.PEM;
import com.siemt3.watchdog_server.cep.event.DemoLogEvent;
import com.siemt3.watchdog_server.model.Alert;
import com.siemt3.watchdog_server.model.DemoLogRequest;
import org.springframework.web.bind.annotation.*;

@RestController
public class DemologController {

    /**
     *
     * @param demoLogRequest reqests that shall be send to the demoLog Endpoint
     * @throws Exception
     *
     * @returns void Attention! this should be void if you don't want to send more than HTTP 200 back to client
     */
    @RequestMapping(value = "/api/v1/demolog", method =  RequestMethod.POST)
    public void echoDemoString(@RequestBody DemoLogRequest demoLogRequest) throws Exception{
        // This is required to deserialize the Object
        // Can also result in a error on deserializing
        String log;
        try{
            log = demoLogRequest.getSus();
        }catch (Exception e){
            throw new Exception("bad log", e);
        }

        // send an event bean with log
        // before we get the runtime with help of PEM
        EPRuntime runtime = PEM.getInstance().runtime;
        runtime.getEventService().sendEventBean(
                new DemoLogEvent(
                        log
                ),
                "DemoLogEvent"
        );
    }

}
