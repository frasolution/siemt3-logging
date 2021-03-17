package com.siemt3.watchdog_server.cep.listener.sshListeners.basicEventListener;

import com.espertech.esper.common.client.EventBean;
import com.espertech.esper.runtime.client.EPRuntime;
import com.espertech.esper.runtime.client.EPStatement;
import com.espertech.esper.runtime.client.UpdateListener;
import com.siemt3.watchdog_server.EventName;
import com.siemt3.watchdog_server.EventType;
import com.siemt3.watchdog_server.Severity;
import com.siemt3.watchdog_server.cep.customObjects.ssh.SshBasicUser;
import com.siemt3.watchdog_server.cep.listener.sshListeners.lib.SshCommonMethods;
import com.siemt3.watchdog_server.condb.DataBase;
import com.siemt3.watchdog_server.model.Alert;

import java.sql.SQLException;

public class SshUserFilterListener implements UpdateListener {
    @Override
    public void update(EventBean[] newEvents, EventBean[] oldEvents, EPStatement statement, EPRuntime runtime) {
        String log = (String) newEvents[0].get("log");
        long arrival_time = (long) newEvents[0].get("arrival_time");
        System.out.println(log + " @4 " + arrival_time);
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
        Alert alert = new Alert()
                .setEventType(EventType.SSH)
                .setEventName(EventName.SSH_Dictionary)
                .setPriority(Severity.YELLOW)
                .setUnix_time(DataBase.current_time())
                .setCustomData(custom_data);
        try {
            DataBase.dbCommit(alert);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

//        System.out.println(username + ip);

    }
}
