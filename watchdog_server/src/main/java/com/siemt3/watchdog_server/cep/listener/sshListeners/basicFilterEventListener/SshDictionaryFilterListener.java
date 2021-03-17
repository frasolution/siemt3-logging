package com.siemt3.watchdog_server.cep.listener.sshListeners.basicFilterEventListener;

import com.espertech.esper.common.client.EventBean;
import com.espertech.esper.runtime.client.EPRuntime;
import com.espertech.esper.runtime.client.EPStatement;
import com.espertech.esper.runtime.client.UpdateListener;
import com.siemt3.watchdog_server.EventName;
import com.siemt3.watchdog_server.EventType;
import com.siemt3.watchdog_server.Severity;
import com.siemt3.watchdog_server.cep.PEM;
import com.siemt3.watchdog_server.cep.customObjects.ssh.SshBasicPassword;
import com.siemt3.watchdog_server.cep.event.sshEvents.SSHDictionaryEvent;
import com.siemt3.watchdog_server.cep.listener.sshListeners.lib.SshCommonMethods;
import com.siemt3.watchdog_server.condb.DataBase;
import com.siemt3.watchdog_server.model.Alert;

import java.sql.SQLException;

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
                new SSHDictionaryEvent(
                        arrival_time,
                        username,
                        ip
                ),
                "SSHDictionaryEvent"
        );
    }
}
