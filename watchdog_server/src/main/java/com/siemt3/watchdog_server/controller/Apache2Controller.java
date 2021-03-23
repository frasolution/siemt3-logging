package com.siemt3.watchdog_server.controller;

import com.espertech.esper.runtime.client.EPRuntime;
import com.siemt3.watchdog_server.cep.PEM;
import com.siemt3.watchdog_server.cep.event.apache2Events.Apache2LogEvent;
import com.siemt3.watchdog_server.model.Apache2LogRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/*++
Project Name:
watchdog_server

Author:
Maximilian Medlin (Meshstyles)
Robert Uwe Litschel

--*/


@RestController
public class Apache2Controller {

    /**
     *
     * @param apache2LogRequest requests that shall be send to the demoLog Endpoint
     * @throws Exception
     *
     * @returns void Attention! this should be void if you don't want to send more than HTTP 200 back to client
     */
    @RequestMapping(value = "/api/v1/apache2", method =  RequestMethod.POST)
    public void echoDemoString(@RequestBody Apache2LogRequest apache2LogRequest) throws Exception{
        // This is required to deserialize the Object
        // Can also result in a error on deserializing
        String log;
        try{
            log = apache2LogRequest.getLog();
        }catch (Exception e){
            throw new Exception("bad log", e);
        }

        // send an event bean with log
        // before we get the runtime with help of PEM
        EPRuntime runtime = PEM.getInstance().runtime;
        runtime.getEventService().sendEventBean(
                new Apache2LogEvent(log),
                "Apache2LogEvent"
        );
    }

}
