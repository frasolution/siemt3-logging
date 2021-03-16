package com.siemt3.watchdog_server.cep.listener.sshListeners;

import com.espertech.esper.common.client.EventBean;
import com.espertech.esper.runtime.client.EPRuntime;
import com.espertech.esper.runtime.client.EPStatement;
import com.espertech.esper.runtime.client.UpdateListener;

public class SshRootFilterListener implements UpdateListener {
    @Override
    public void update(EventBean[] newEvents, EventBean[] oldEvents, EPStatement statement, EPRuntime runtime) {
        String log = (String) newEvents[0].get("log");
        long arrival_time = (long) newEvents[0].get("arrival_time");
        System.out.println(log + " @2 " + arrival_time);
        String ip;
        String[] a1 = log.split(" for root from ");
        String[] a2 = a1[1].split(" port ");
        ip = a2[0];
        //verified parse!
    }
}
