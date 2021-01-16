package com.siemt3.watchdog_server.controller;

import com.espertech.esper.runtime.client.EPEventService;
import com.espertech.esper.runtime.client.EPRuntime;
import com.siemt3.watchdog_server.cep.event.DemoLogEvent;
import com.siemt3.watchdog_server.model.DemoLogRequest;
import com.siemt3.watchdog_server.model.DemoLogResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class DemologController {

    private static EPEventService eventServices;
    private static EPRuntime epRuntime;

    public static void setEventService(EPEventService eventService, EPRuntime epRuntime1){
        eventServices = eventService;
        epRuntime = epRuntime1;
    }

    @RequestMapping(value = "/api/v1/demolog", method =  RequestMethod.POST)
    public ResponseEntity<?> echoDemoString(@RequestBody DemoLogRequest demoLogRequest) throws Exception{
        // This is required to deserialize the Object
        String log;
        try{
            log = demoLogRequest.getSus();
        }catch (Exception e){
            throw new Exception("bad log", e);
        }
//        System.out.println(log);
        System.out.println(this.epRuntime);
//        this.eventServices.sendEventBean(new DemoLogEvent(log), "DemoLogEvent");
        this.epRuntime.getEventService().sendEventBean(
                new DemoLogEvent(
                        log
                ),
                "DemoLogEvent"
        );
        // sends the log ("sus") back to sender
        // omit this for prod code
        return ResponseEntity.ok(new DemoLogResponse(log));
    }

}
