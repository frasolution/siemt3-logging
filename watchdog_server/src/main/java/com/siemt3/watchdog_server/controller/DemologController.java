package com.siemt3.watchdog_server.controller;

import com.siemt3.watchdog_server.model.DemoLogRequest;
import com.siemt3.watchdog_server.model.DemoLogResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class DemologController {

    @RequestMapping(value = "/api/v1/demolog", method =  RequestMethod.POST)
    public ResponseEntity<?> echoDemoString(@RequestBody DemoLogRequest demoLogRequest) throws Exception{
        // This is required to deserialize the Object
        String log;
        try{
            log = demoLogRequest.getSus();
        }catch (Exception e){
            throw new Exception("bad log", e);
        }
        // sends the log ("sus") back to sender
        // omit this for prod code
        return ResponseEntity.ok(new DemoLogResponse(log));
    }

}
