package com.siemt3.watchdog_server.cep.listener.ssh.basicEventListener;

import com.espertech.esper.common.client.EventBean;
import com.espertech.esper.runtime.client.EPRuntime;
import com.espertech.esper.runtime.client.EPStatement;
import com.espertech.esper.runtime.client.UpdateListener;
import com.siemt3.watchdog_server.EventName;
import com.siemt3.watchdog_server.EventType;
import com.siemt3.watchdog_server.Severity;
import com.siemt3.watchdog_server.cep.customObjects.ssh.SshBasicUser;
import com.siemt3.watchdog_server.cep.event.sshEvents.SshIpFilterEvent;
import com.siemt3.watchdog_server.cep.event.sshEvents.SshUserEvent;
import com.siemt3.watchdog_server.cep.listener.ssh.lib.SshCommonMethods;
import com.siemt3.watchdog_server.condb.DataBase;
import com.siemt3.watchdog_server.model.Alert;

import java.sql.SQLException;

import static com.siemt3.watchdog_server.GlobalVariables.DEBUG_FLAG;

public class SshUserBasicListener implements UpdateListener {
    @Override
    public void update(EventBean[] newEvents, EventBean[] oldEvents, EPStatement statement, EPRuntime runtime) {
        String log = (String) newEvents[0].get("log");
        long arrival_time = (long) newEvents[0].get("arrival_time");
//        System.out.println(log + " @4 " + arrival_time);
        String username, ip;
        String[] a1, a2, a3, a4;
        //TODO fix statements for user with spaces
        a1 = log.split(" invalid user ");
        a2 = a1[1].split(" " );
        username = a2[0];
        a3 = a1[1].split(username+" ");
        a4 = a3[1].split(" port ");
        ip = a4[0];

        SshBasicUser sshBasicUser = new SshBasicUser(username, ip);
        String custom_data = SshCommonMethods.toJson(sshBasicUser);

        if (DEBUG_FLAG) {
            System.out.println(EventName.SSH_User + " : " + custom_data);
        }

        Alert alert = new Alert()
                .setEventType(EventType.SSH)
                .setEventName(EventName.SSH_User)
                .setPriority(Severity.GREEN)
                .setUnix_time(DataBase.current_time())
                .setCustomData(custom_data);
        try {
            DataBase.dbCommit(alert);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

//        System.out.println(username + ip);

        runtime.getEventService().sendEventBean(
                new SshUserEvent(arrival_time, username, ip),
                "SshUserEvent"
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
