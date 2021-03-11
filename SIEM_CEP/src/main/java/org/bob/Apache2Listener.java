package org.bob;

import com.espertech.esper.common.client.EventBean;
import com.espertech.esper.common.client.PropertyAccessException;
import com.espertech.esper.runtime.client.EPRuntime;
import com.espertech.esper.runtime.client.EPStatement;
import com.espertech.esper.runtime.client.UpdateListener;

public class Apache2Listener implements UpdateListener {

    @Override
    public void update(EventBean[] newEvents, EventBean[] oldEvents, EPStatement epStatement, EPRuntime epRuntime) {
        EventBean event = newEvents[0];
        //System.out.println("listen: " + event.toString());


        // basic log404 event
        try {
            if (event.get("what") == "log") {
                System.out.println("listener: " + event.get("what") + ": " + event.get("log"));
            }
        } catch (PropertyAccessException exception) {
            System.out.println("log - exception");
        }


        //basic 404 alert
        try {
            if (event.get("what") == "basic") {
                System.out.println("listener: " + event.get("what") + ": " + event.get("log"));
            }
        } catch (PropertyAccessException exception) {
            System.out.println("basic - exception");
        }


        //time window alert 404
        try {
            if (event.get("what") == "alert") {
                System.out.println("listener: " + event.get("what") + ": " + event.get("log"));
            }
        } catch (PropertyAccessException exception) {
            System.out.println("alert - exception");
        }
    }
}
