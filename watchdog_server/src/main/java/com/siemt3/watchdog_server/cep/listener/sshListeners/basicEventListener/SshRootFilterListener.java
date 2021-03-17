package com.siemt3.watchdog_server.cep.listener.sshListeners.basicEventListener;

import com.espertech.esper.common.client.EventBean;
import com.espertech.esper.runtime.client.EPRuntime;
import com.espertech.esper.runtime.client.EPStatement;
import com.espertech.esper.runtime.client.UpdateListener;
import com.siemt3.watchdog_server.EventName;
import com.siemt3.watchdog_server.EventType;
import com.siemt3.watchdog_server.Severity;
import com.siemt3.watchdog_server.cep.customObjects.ssh.SshBasicRoot;
import com.siemt3.watchdog_server.cep.event.sshEvents.SSHIpFilterEvent;
import com.siemt3.watchdog_server.cep.listener.sshListeners.lib.SshCommonMethods;
import com.siemt3.watchdog_server.condb.DataBase;
import com.siemt3.watchdog_server.model.Alert;

import java.sql.SQLException;

public class SshRootFilterListener implements UpdateListener {
    @Override
    public void update(EventBean[] newEvents, EventBean[] oldEvents, EPStatement statement, EPRuntime runtime) {
        String log = (String) newEvents[0].get("log");
        long arrival_time = (long) newEvents[0].get("arrival_time");
        String ip;
        String[] a1 = log.split(" for root from ");
        String[] a2 = a1[1].split(" port ");
        ip = a2[0];
        //verified parse!

        System.out.println(ip + "root");

        SshBasicRoot sshBasicRoot = new SshBasicRoot(ip);
        String custom_data = SshCommonMethods.toJson(sshBasicRoot);
        Alert alert = new Alert()
                .setEventType(EventType.SSH)
                .setEventName(EventName.SSH_Algorithm)
                .setUnix_time(arrival_time)
                .setPriority(Severity.GREEN)
                .setCustomData(custom_data);
        try {
            DataBase.dbCommit(alert);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}