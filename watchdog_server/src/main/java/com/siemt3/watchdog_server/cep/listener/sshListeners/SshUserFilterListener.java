package com.siemt3.watchdog_server.cep.listener.sshListeners;

import com.espertech.esper.common.client.EventBean;
import com.espertech.esper.runtime.client.EPRuntime;
import com.espertech.esper.runtime.client.EPStatement;
import com.espertech.esper.runtime.client.UpdateListener;

public class SshUserFilterListener implements UpdateListener {
    @Override
    public void update(EventBean[] newEvents, EventBean[] oldEvents, EPStatement statement, EPRuntime runtime) {
        String log = (String) newEvents[0].get("log");
        long arrival_time = (long) newEvents[0].get("arrival_time");
        System.out.println(log + " @4 " + arrival_time);
        String username, ip;
        String[] a1, a2, a3, a4;
        //TODO fix statements for user with spaces
//        String IPv4Limiter = "^[1-9]\\d{0,2}\\.[1-9]\\d{0,2}\\.[1-9]\\d{0,2}\\.[1-9]\\d{0,2}";
        a1 = log.split(" invalid user ");
        a2 = a1[1].split(" " );
        username = a2[0];
        a3 = a1[1].split(username+" ");
        a4 = a3[1].split(" port ");
        ip = a4[0];
        // verified parsing
//        System.out.println(username + ip);

        String lol = "Dec 9 22:01:56 dlserve sshd[1138]: Disconnecting invalid user hans 192.168.178.58 port 49638: Too many authentication failures [preauth]";
    }
}
