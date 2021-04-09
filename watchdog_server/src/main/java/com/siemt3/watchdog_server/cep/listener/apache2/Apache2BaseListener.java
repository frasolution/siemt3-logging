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

        // basic event
        try {
            if (event.get("eventName") != null) {
                System.out.println("listener: " + event.get("eventName") + ": " + event.get("log"));
            }
        } catch (PropertyAccessException exception) {
            System.out.println("listener - exception");
        }
    }
}