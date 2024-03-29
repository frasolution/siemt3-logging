package com.siemt3.watchdog_server.cep.listener.ssh.elevatedEvleventListener;

import com.espertech.esper.common.client.EventBean;
import com.espertech.esper.runtime.client.EPRuntime;
import com.espertech.esper.runtime.client.EPStatement;
import com.espertech.esper.runtime.client.UpdateListener;
import com.siemt3.watchdog_server.EventName;
import com.siemt3.watchdog_server.EventType;
import com.siemt3.watchdog_server.Severity;
import com.siemt3.watchdog_server.cep.event.sshEvents.SshIpEvent;
import com.siemt3.watchdog_server.cep.listener.ssh.lib.SshCommonMethods;
import com.siemt3.watchdog_server.condb.DataBase;
import com.siemt3.watchdog_server.model.Alert;

import java.sql.SQLException;
import java.util.ArrayList;

import static com.siemt3.watchdog_server.GlobalVariables.DEBUG_FLAG;

public class SshIpElevatedListener implements UpdateListener {
    @Override
    public void update(EventBean[] newEvents, EventBean[] oldEvents, EPStatement statement, EPRuntime runtime) {
        long arrival_time = DataBase.current_time();

        ArrayList<SshIpEvent> al = new ArrayList<SshIpEvent>();

        for (EventBean newEvent :
                newEvents) {
            long arrival_timee = (long) newEvent.get("arrival_time");
            String ip = (String) newEvent.get("ip");
            String hostname = (String) newEvent.get("hostname");
            String username = (String) newEvent.get("username");
            al.add(new SshIpEvent(arrival_timee, ip, hostname, username));
        }

        String custom_data = SshCommonMethods.toJson(al);

        if (DEBUG_FLAG) {
            System.out.println(EventName.SSH_IpE + " : " + custom_data);
        }

        Alert alert = new Alert()
                .setEventType(EventType.SSH)
                .setEventName(EventName.SSH_IpE)
                .setUnix_time(arrival_time)
                .setPriority(Severity.RED)
                .setCustomData(custom_data);
        try {
            DataBase.dbCommit(alert);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
