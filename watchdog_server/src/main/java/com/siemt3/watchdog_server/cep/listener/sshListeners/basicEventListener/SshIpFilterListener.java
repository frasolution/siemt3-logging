


package com.siemt3.watchdog_server.cep.listener.sshListeners.basicEventListener;

import com.espertech.esper.common.client.EventBean;
import com.espertech.esper.runtime.client.EPRuntime;
import com.espertech.esper.runtime.client.EPStatement;
import com.espertech.esper.runtime.client.UpdateListener;
import com.siemt3.watchdog_server.cep.event.sshEvents.SSHIpEvent;
import com.siemt3.watchdog_server.cep.listener.sshListeners.lib.SshCommonMethods;

public class SshIpFilterListener implements UpdateListener {
    @Override
    public void update(EventBean[] newEvents, EventBean[] oldEvents, EPStatement statement, EPRuntime runtime) {
        String log = (String) newEvents[0].get("log");
        long arrival_time = (long) newEvents[0].get("arrival_time");
        String ip = (String) newEvents[0].get("ip");
        String username = (String) newEvents[0].get("username");

        String hostname = SshCommonMethods.getHostname(log);

        System.out.println(ip + username + hostname);
        //successful parsing
        runtime.getEventService().sendEventBean(
                new SSHIpEvent(
                        arrival_time,
                        username,
                        hostname,
                        ip
                ),
                "SSHIpEvent"
        );

    }
}
