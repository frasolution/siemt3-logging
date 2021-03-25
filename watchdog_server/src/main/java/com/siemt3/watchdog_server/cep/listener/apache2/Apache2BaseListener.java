package com.siemt3.watchdog_server.cep.listener.apache2;

import com.espertech.esper.common.client.EventBean;
import com.espertech.esper.common.client.PropertyAccessException;
import com.espertech.esper.compiler.client.EPCompileException;
import com.espertech.esper.runtime.client.EPRuntime;
import com.espertech.esper.runtime.client.EPStatement;
import com.espertech.esper.runtime.client.UpdateListener;

public class Apache2BaseListener implements UpdateListener {

    @Override
    public void update(EventBean[] newEvents, EventBean[] oldEvents, EPStatement epStatement, EPRuntime epRuntime) {
        EventBean event = newEvents[0];
        //System.out.println("apache2_listener: " + event.toString());


//        // basic log400 event
//        try {
//            if (event.get("event") == "log400") {
//                System.out.println("listener: " + event.get("event") + ": " + event.get("log"));
//            }
//        } catch (PropertyAccessException exception) {
//            System.out.println("log - exception");
//        }

        // basic log404 event
        try {
            if (event.get("event") == "log404") {
                System.out.println("listener: " + event.get("event") + ": " + event.get("log"));
            }
        } catch (PropertyAccessException exception) {
            System.out.println("listener - exception");
        }


//        // basic 400 warning
//        try {
//            if (event.get("event") == "warn400") {
//                System.out.println("listener: " + event.get("event") + ": " + event.get("log"));
//            }
//        } catch (PropertyAccessException exception) {
//            System.out.println("listener - exception");
//        }
//
//        // basic 400 warning
//        try {
//            if (event.get("event") == "warn400-time") {
//                System.out.println("listener: " + event.get("event") + ": " + event.get("log"));
//            }
//        } catch (PropertyAccessException exception) {
//            System.out.println("listener - exception");
//        }


//        // basic 404 request
//        try {
//            if (event.get("event") == "request404") {
//                System.out.println("listener: " + event.get("event") + ": " + event.get("request"));
//            }
//        } catch (PropertyAccessException exception) {
//            System.out.println("listener - exception");
//        } catch (NullPointerException exception) {
//            System.out.println("listener: NullPointerException");
//        }
    }
}