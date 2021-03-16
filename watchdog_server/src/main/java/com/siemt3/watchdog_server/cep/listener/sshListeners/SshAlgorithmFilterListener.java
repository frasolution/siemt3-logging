package com.siemt3.watchdog_server.cep.listener.sshListeners;

import com.espertech.esper.common.client.EventBean;
import com.espertech.esper.runtime.client.EPRuntime;
import com.espertech.esper.runtime.client.EPStatement;
import com.espertech.esper.runtime.client.UpdateListener;
import com.siemt3.watchdog_server.EventId;
import com.siemt3.watchdog_server.EventType;
import com.siemt3.watchdog_server.Severity;
import com.siemt3.watchdog_server.cep.event.sshEvents.SSHAlgorithmEvent;
import com.siemt3.watchdog_server.model.Alert;

public class SshAlgorithmFilterListener implements UpdateListener {
    @Override
    public void update(EventBean[] newEvents, EventBean[] oldEvents, EPStatement statement, EPRuntime runtime) {
        String log = (String) newEvents[0].get("log");
        long arrival_time = (long) newEvents[0].get("arrival_time");
        System.out.println(log + " @3 " + arrival_time );
        String username, algo, fingerprint, ip ;
        String[] a1, a2, a3, a4, a5;

        //TODO fix when user is called from, ssh2:

        a1 = log.split(" publickey for ");
        a2 = a1[1].split(" from ");
        a3 = a2[1].split(" ssh2: ");
        a4 = a3[1].split(":");
        a5 = a2[1].split(" port ");

        username = a2[0];
        algo = a4[0];
        fingerprint = a4[1];
        ip = a5[0];

        //verified parse
        //System.out.println(username + algo + fingerprint + ip);

        runtime.getEventService().sendEventBean(
                new SSHAlgorithmEvent(
                        arrival_time,
                        username,
                        algo,
                        fingerprint,
                        ip
                ),
                "SSHAlgorithmEvent"
        );

//        new Alert(EventId.SSH_Algorithm, EventType.SSH_Algorithm, "justus", arrival_time, Severity.GREEN, )
    }
}
