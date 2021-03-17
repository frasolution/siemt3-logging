package com.siemt3.watchdog_server.cep.listener.sshListeners.basicFilterEventListener;

import com.espertech.esper.common.client.EventBean;
import com.espertech.esper.runtime.client.EPRuntime;
import com.espertech.esper.runtime.client.EPStatement;
import com.espertech.esper.runtime.client.UpdateListener;
import com.siemt3.watchdog_server.cep.customObjects.ssh.SshBasicPassword;
import com.siemt3.watchdog_server.cep.event.sshEvents.SSHIpFilterEvent;
import com.siemt3.watchdog_server.cep.listener.sshListeners.lib.SshCommonMethods;
import com.siemt3.watchdog_server.model.Alert;

public class SshSuccessfulFilterListener implements UpdateListener {
    @Override
    public void update(EventBean[] newEvents, EventBean[] oldEvents, EPStatement statement, EPRuntime runtime) {
        String log = (String) newEvents[0].get("log");
        long arrival_time = (long) newEvents[0].get("arrival_time");
        System.out.println(log + " @1 " + arrival_time);

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

        System.out.println(ip + "succ");

        runtime.getEventService().sendEventBean(
                new SSHIpFilterEvent(
                        arrival_time,
                        ip,
                        log,
                        username
                ),
                "SSHIpFilterEvent"
        );
    }
}
