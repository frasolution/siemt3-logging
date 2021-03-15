package com.siemt3.watchdog_server.cep.listener.sshListeners;

import com.espertech.esper.common.client.EventBean;
import com.espertech.esper.runtime.client.EPRuntime;
import com.espertech.esper.runtime.client.EPStatement;
import com.espertech.esper.runtime.client.UpdateListener;
import com.siemt3.watchdog_server.cep.PEM;
import com.siemt3.watchdog_server.cep.event.sshEvents.SSHDictionaryEvent;

public class SshDictionaryFilterListener implements UpdateListener {
    @Override
    public void update(EventBean[] newEvents, EventBean[] oldEvents, EPStatement statement, EPRuntime runtime) {
        String log = (String) newEvents[0].get("log");
        long arrival_time = (long) newEvents[0].get("arrival_time");
        System.out.println(log + " @1 " + arrival_time);

        String username;
        String ip;

        String[] tmp_a1 = log.split("Failed password for ");
        String[] tmp_a2 = tmp_a1[1].split(" from ");
        username = tmp_a2[0];
        String[] tmp_a3 =  tmp_a2[1].split(" port ");
        ip = tmp_a3[0];
        System.out.println(username + ip);
//        EPRuntime epRuntime = PEM.getInstance().runtime;
        runtime.getEventService().sendEventBean(
                new SSHDictionaryEvent(
                        arrival_time,
                        username,
                        ip
                ),
                "SSHDictionaryEvent"
        );
    }
}
