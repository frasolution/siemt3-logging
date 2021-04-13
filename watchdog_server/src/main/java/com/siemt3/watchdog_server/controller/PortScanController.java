/**
 * @author Nicolas Roth
 */

package com.siemt3.watchdog_server.controller;

import com.espertech.esper.runtime.client.EPRuntime;
import com.siemt3.watchdog_server.cep.PEM;
import com.siemt3.watchdog_server.cep.event.portScanEvents.PortRawEvent;
import com.siemt3.watchdog_server.model.PortScanLogRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PortScanController {

    /**
     *
     * @param portScanLogRequest request that shall be send to the demoLog Endpoint
     * @throws Exception
     *
     * @returns void Attention! this should be void if you don't want to send more than HTTP 200 back to client
     */
    @RequestMapping(value = "/api/v1/port", method =  RequestMethod.POST)
    public void echoDemoString(@RequestBody PortScanLogRequest portScanLogRequest) throws Exception{
        // This is required to deserialize the Object
        // Can also result in a error on deserializing
    	String type;
    	String srcIp;
    	Integer srcPort;
    	String dstIp;
    	Integer dstPort;
        System.out.println("Port Scan call");
        try{
        	type = portScanLogRequest.getType();
        	srcIp = portScanLogRequest.getSrcIp();
        	srcPort = portScanLogRequest.getSrcPort();
        	dstIp = portScanLogRequest.getDstIp();
        	dstPort = portScanLogRequest.getDstPort();
        }catch (Exception e){
            throw new Exception("bad log", e);
        }
        // send an event bean with log
        // before we get the runtime with help of PEM
        EPRuntime runtime = PEM.getInstance().runtime;
        //TODO add further logic
        runtime.getEventService().sendEventBean(
                new PortRawEvent( type, srcIp, srcPort, dstIp, dstPort ),
                "PortScanRawEvent"
        );
    }

}
