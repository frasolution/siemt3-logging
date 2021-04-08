package com.siemt3.watchdog_server.cep.listener.ssh.elevatedEvleventListener;

import com.espertech.esper.common.client.EventBean;
import com.espertech.esper.runtime.client.EPRuntime;
import com.espertech.esper.runtime.client.EPStatement;
import com.espertech.esper.runtime.client.UpdateListener;
import com.siemt3.watchdog_server.EventName;
import com.siemt3.watchdog_server.EventType;
import com.siemt3.watchdog_server.Severity;
import com.siemt3.watchdog_server.cep.event.sshEvents.SshRootEvent;
import com.siemt3.watchdog_server.cep.listener.ssh.lib.SshCommonMethods;
import com.siemt3.watchdog_server.condb.DataBase;
import com.siemt3.watchdog_server.model.Alert;

import java.sql.SQLException;
import java.util.ArrayList;

import static com.siemt3.watchdog_server.GlobalVariables.DEBUG_FLAG;

public class SshRootElevatedListener implements UpdateListener {


    @Override
    public void update(EventBean[] newEvents, EventBean[] oldEvents, EPStatement statement, EPRuntime runtime) {
        long arrival_time = DataBase.current_time();
        ArrayList<SshRootEvent> al = new ArrayList<SshRootEvent>();

        for (EventBean newEvent : newEvents) {
            long arrival_timee = (long) newEvent.get("arrival_time");
            String ip = (String) newEvent.get("ip");
            al.add(new SshRootEvent(arrival_timee, ip));
        }
        String custom_data = SshCommonMethods.toJson(al);

        if (DEBUG_FLAG) {
            System.out.println(EventName.SSH_RootE + " : " + custom_data);
        }

        Alert alert = new Alert()
                .setEventType(EventType.SSH)
                .setEventName(EventName.SSH_RootE)
                .setUnix_time(arrival_time)
                .setPriority(Severity.YELLOW)
                .setCustomData(custom_data);
        try {
            DataBase.dbCommit(alert);
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
    }
}
