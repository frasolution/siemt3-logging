package com.siemt3.watchdog_server.cep.listener.ssh.basicFilterEventListener;

import com.espertech.esper.common.client.EventBean;
import com.espertech.esper.runtime.client.EPRuntime;
import com.espertech.esper.runtime.client.EPStatement;
import com.espertech.esper.runtime.client.UpdateListener;
import com.siemt3.watchdog_server.cep.event.sshEvents.SshDictionaryEvent;
import com.siemt3.watchdog_server.cep.event.sshEvents.SshIpFilterEvent;

public class SshDictionaryFilterListener implements UpdateListener {
    @Override
    public void update(EventBean[] newEvents, EventBean[] oldEvents, EPStatement statement, EPRuntime runtime) {
        String log = (String) newEvents[0].get("log");
        long arrival_time = (long) newEvents[0].get("arrival_time");

        String username, ip;

        String[] tmp_a1 = log.split("Failed password for ");
        String[] tmp_a2 = tmp_a1[1].split(" from ");
        username = tmp_a2[0];
        String[] tmp_a3 =  tmp_a2[1].split(" port ");
        ip = tmp_a3[0];

        runtime.getEventService().sendEventBean(
                new SshDictionaryEvent(
                        arrival_time,
                        username,
                        ip
                ),
                "SshDictionaryEvent"
        );

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
