package com.siemt3.watchdog_server.cep.listener.ssh.basicEventListener;

import com.espertech.esper.common.client.EventBean;
import com.espertech.esper.runtime.client.EPRuntime;
import com.espertech.esper.runtime.client.EPStatement;
import com.espertech.esper.runtime.client.UpdateListener;

public class SshDictionaryBasicListener implements UpdateListener {
    @Override
    public void update(EventBean[] newEvents, EventBean[] oldEvents, EPStatement statement, EPRuntime runtime) {
        long arrival_time   =   (long)      newEvents[0].get("arrival_time");
        String username     =   (String)    newEvents[0].get("username");
        String ip           =   (String)    newEvents[0].get("ip");
        for (EventBean newEvent: newEvents) {
            System.out.println(newEvent.getUnderlying().toString());
        }
        System.out.println(newEvents.length);
    }
}
