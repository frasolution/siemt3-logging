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
        System.out.println(log + " @1 " + arrival_time);

        String username, ip;

        String[] tmp_a1 = log.split("Failed password for ");
        String[] tmp_a2 = tmp_a1[1].split(" from ");
        username = tmp_a2[0];
        String[] tmp_a3 =  tmp_a2[1].split(" port ");
        ip = tmp_a3[0];

//        verified parsing
//        System.out.println(username + ip);

//        SshBasicPassword sshBasicPassword = new SshBasicPassword(username, ip);
//        String custom_data = SshCommonMethods.toJson(sshBasicPassword);
//        Alert alert = new Alert()
//                .setEventType(EventType.SSH)
//                .setEventName(EventName.SSH_Dictionary)
//                .setPriority(Severity.YELLOW)
//                .setUnix_time(DataBase.current_time())
//                .setCustomData(custom_data);
//        try {
//            DataBase.dbCommit(alert);
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }

        runtime.getEventService().sendEventBean(
                new SshDictionaryEvent(
                        arrival_time,
                        username,
                        ip
                ),
                "SSHDictionaryEvent"
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