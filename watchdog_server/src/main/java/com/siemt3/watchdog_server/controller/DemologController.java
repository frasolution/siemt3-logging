package com.siemt3.watchdog_server.controller;

import com.siemt3.watchdog_server.model.DemoLogRequest;
import com.siemt3.watchdog_server.model.DemoLogResponse;
//import com.siemt3.watchdog_server.services.MyUserDetailsService;
//import com.siemt3.watchdog_server.util.JwtUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class DemologController {
//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    @Autowired
//    private JwtUtil jwtTokenUtil;
//
//    @Autowired
//    private MyUserDetailsService userDetailsService;

    @RequestMapping(value = "/api/v1/demolog", method =  RequestMethod.POST)
    public ResponseEntity<?> echoDemoString(@RequestBody DemoLogRequest demoLogRequest) throws Exception{
        String log;
        try{
            log = demoLogRequest.getSus();
        }catch (Exception e){
            throw new Exception("bad log", e);
        }
            return ResponseEntity.ok(new DemoLogResponse(log));
    }
}
