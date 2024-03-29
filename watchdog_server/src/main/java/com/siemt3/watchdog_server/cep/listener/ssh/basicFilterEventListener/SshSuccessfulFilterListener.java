package com.siemt3.watchdog_server.cep.listener.ssh.basicFilterEventListener;

import com.espertech.esper.common.client.EventBean;
import com.espertech.esper.runtime.client.EPRuntime;
import com.espertech.esper.runtime.client.EPStatement;
import com.espertech.esper.runtime.client.UpdateListener;
import com.siemt3.watchdog_server.cep.event.sshEvents.SshIpFilterEvent;

public class SshSuccessfulFilterListener implements UpdateListener {
    @Override
    public void update(EventBean[] newEvents, EventBean[] oldEvents, EPStatement statement, EPRuntime runtime) {
        String log = (String) newEvents[0].get("log");
        long arrival_time = (long) newEvents[0].get("arrival_time");

        String username, ip;
        String[] a1, a2, a3;
        if(log.contains("publickey")){
            a1 = log.split(" Accepted publickey for ");
        }else{
            a1 = log.split(" Accepted password for ");
        }
        a2 = a1[1].split(" from ");
        username = a2[0];
        a3 =  a2[1].split(" port ");
        ip = a3[0];

        runtime.getEventService().sendEventBean(
                new SshIpFilterEvent(
                        arrival_time,
                        ip,
                        log,
                        username
                ),
                "SshIpFilterEvent"
        );
    }
}
